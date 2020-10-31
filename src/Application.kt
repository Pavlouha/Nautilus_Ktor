package com.pavlouha

import com.pavlouha.dao.*
import com.pavlouha.jwtThings.JwtConfig
import com.pavlouha.jwtThings.model.JwtUser
import com.pavlouha.models.Customer
import com.pavlouha.models.Order
import com.pavlouha.models.OrderReviewState
import com.pavlouha.models.OrderState
import com.urbanairship.api.client.Response
import com.urbanairship.api.client.UrbanAirshipClient
import com.urbanairship.api.push.PushRequest
import com.urbanairship.api.push.model.DeviceType
import com.urbanairship.api.push.model.DeviceTypeData
import com.urbanairship.api.push.model.PushPayload
import com.urbanairship.api.push.model.PushResponse
import com.urbanairship.api.push.model.audience.Selectors
import com.urbanairship.api.push.model.notification.Notifications
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.IOException
import java.sql.Date
import java.text.SimpleDateFormat


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            realm = "com.pavlouha"
            validate {
                val name = it.payload.getClaim("name").asString()
                val password = it.payload.getClaim("password").asString()
                if(name != null && password != null){
                    JwtUser(name, password)
                }else{
                    null
                }
            }
        }
    }

    /* install(HttpsRedirect) {
        // The port to redirect to. By default 443, the default HTTPS port.
        sslPort = 443
        // 301 Moved Permanently, or 302 Found redirect.
        permanentRedirect = true
    } */

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    val pushClient = UrbanAirshipClient.newBuilder()
        .setKey("L5xur5MgQ4WcdTno7XALxA")
        .setSecret("rXhQ5XVjR32DPzpcujUQTg")
        .build()

    routing {
        get("/") {
            call.respondText("HELLO, WORLD!", contentType = ContentType.Text.Plain)
        }

        /** TOKEN GEN */
        post("/generatetoken"){
            val user = call.receive<JwtUser>()
            val result = UserDao.authenticate(user.name, user.password)
            if (result != null) {
                println("${user.name}, pwd = ${user.password}")
                val token = JwtConfig.generateToken(user)

                call.respond(
                    mapOf(
                        "token" to token, "id" to result.userId, "login" to result.login,
                        "password" to result.password, "username" to result.username, "cell" to result.cell,
                        "roleId" to result.role.roleId, "title" to result.role.title
                    )
                )
            }
        }

        authenticate {
            /** STATE */
            get("/gunstate") {
                call.respond(StateDao.getGunInOrderState())
            }

            get("/orderstate") {
                call.respond(StateDao.getOrderState())
            }

            /** AUTH TABLE */
            get("/auths") {
                call.respond(AuthDao.get())
            }

            delete("/auths") {
                call.respond(AuthDao.delete())
            }

            /** GUN */
            get("/gun") {
                call.respond(GunDao.get())
            }

            post("/gun") {
                val parameters = call.receiveParameters()

                val vendorCode = parameters["vendorCode"]
                val price = parameters["price"]?.toInt()

                vendorCode?.let { it1 ->
                    if (price != null) {
                        GunDao.insert(it1, price)
                    }
                }?.let { it2 -> call.respond(it2) }
            }

            delete("/gun") {
                val parameters = call.receiveParameters()

                val id = parameters["id"]?.toInt()
                id?.let { it1 -> GunDao.delete(it1) }?.let { it2 -> call.respond(it2) }
            }

            /** GUNINORDER */
            get("/guninorder") {
                val parameters = call.receiveParameters()

                val id = parameters["id"]
                if (id != null) {
                    call.respond(GunInOrderDao.get(id.toInt()))
                }
                // id?.let { it1 -> GunInOrderDao.get(it1) }?.let { it2 -> call.respond(it2) }
            }

            patch("/guninorder") {
                val parameters = call.receiveParameters()

                val orderId = parameters["orderId"]?.toInt()
                val stateId = parameters["stateId"]?.toInt()

                val result = GunInOrderDao.update(orderId!!, stateId!!)

                if (result) {
                    val payload = PushPayload.newBuilder()
                        .setAudience(Selectors.androidChannel("customChannel"))
                        .setNotification(Notifications.alert("Hey! Order #$orderId is changed. Please, review!"))
                        .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
                        .build()

                    val request = PushRequest.newRequest(payload)

                    try {
                       val response = pushClient.execute(request)
                    } catch (e: IOException) {
                        println(e.stackTrace)
                    }
                }

                call.respond(result)
            }

            /** USER */

            get("/user") {
                call.respond(UserDao.get())
            }

            post("/user") {
                val parameters = call.receiveParameters()

                println(parameters)

                val login = parameters["login"]
                val password = parameters["password"]
                val roleId = parameters["roleId"]!!.toInt()
                val username = parameters["username"]
                val cell = parameters["cell"]

                call.respond(UserDao.insert(login!!, username!!, roleId, password!!, cell!!))
            }

            delete("/user") {
                val parameters = call.receiveParameters()

                val id = parameters["id"]!!.toInt()
                call.respond(UserDao.delete(id))
            }

            /** ORDER */
            get("/order") {
                call.respond(OrderDao.get())
            }

            post("/order") {
                val parameters = call.receiveParameters()

                val id = parameters["id"]!!.toInt()
                val customerId = parameters["customerId"]!!.toInt()
                val commentary = parameters["commentary"]
                val userId = parameters["userId"]!!.toInt()
                val orderDate = parameters["orderDate"]

                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                val result = OrderDao.insert(
                        Order(id, Customer(customerId, "", "", ""),
                                commentary!!, userId, "", orderDate!!, OrderState(0, ""),
                                OrderReviewState(0, "")))

                if (result) {
                    val payload = PushPayload.newBuilder()
                            .setAudience(Selectors.androidChannel("customChannel"))
                            .setNotification(Notifications.alert("New order is appeared"))
                            .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
                            .build()

                    val request = PushRequest.newRequest(payload)

                    try {
                        val response = pushClient.execute(request)
                    } catch (e: IOException) {
                        println(e.stackTrace)
                    }
                }

                call.respond(result)
            }

            patch("/order") {
                val parameters = call.receiveParameters()

                //TODO сделать уведомление

                val id = parameters["id"]!!.toInt()
                val stateId = parameters["stateId"]?.toInt()

                val result = OrderDao.updateState(id, stateId!!)

                if (result) {
                    val payload = PushPayload.newBuilder()
                            .setAudience(Selectors.androidChannel("customChannel"))
                            .setNotification(Notifications.alert("Order #$id is updated now! Check this!"))
                            .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
                            .build()

                    val request = PushRequest.newRequest(payload)

                    try {
                        val response = pushClient.execute(request)
                    } catch (e: IOException) {
                        println(e.stackTrace)
                    }
                }

                call.respond(result)
            }

            /** ROLE */
            get("/role") {
                call.respond(RoleDao.get())
            }
        }
    }
}