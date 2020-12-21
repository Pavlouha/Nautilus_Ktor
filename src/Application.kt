package com.pavlouha

import com.pavlouha.dao.*
import com.pavlouha.jwtThings.Decoder
import com.pavlouha.jwtThings.JwtConfig
import com.pavlouha.jwtThings.model.JwtUser
import com.urbanairship.api.client.UrbanAirshipClient
import com.urbanairship.api.push.PushRequest
import com.urbanairship.api.push.model.DeviceType
import com.urbanairship.api.push.model.DeviceTypeData
import com.urbanairship.api.push.model.PushPayload
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

    install(CORS) {

        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        allowNonSimpleContentTypes = true
        anyHost()
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
        .setSecret("NnkF8PWCTqqpzQElVFn8pA")
        .build()

    routing {
        get("/") {
            call.respondText("HELLO, WORLD!", contentType = ContentType.Text.Plain)
        }

        /** TOKEN GEN */
        /** ALL */
        post("/generatetoken"){
            val user = call.receive<JwtUser>()
            val result = UserDao.authenticate(user.name, user.password)
            if (result != null) {
                println("Logged in as ${user.name}, pwd = ${user.password}")
                val token = JwtConfig.generateToken(user, result.role.roleId)
                call.respond(
                    mapOf(
                        "token" to token, "id" to result.userId, "login" to result.login,
                        "password" to result.password, "username" to result.username, "cell" to result.cell,
                        "roleId" to result.role.roleId, "title" to result.role.title
                    )
                )
            } else {
                print("Wrong login with ${user.name} and ${user.password}")
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
            /** CAPTAIN */
            get("/auths") {

                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 0) {
                    call.respond(AuthDao.get())
                }

            }

            /** CAPTAIN */
            delete("/auths") {
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 0) {
                    call.respond(AuthDao.delete())
                }

            }

            /** GUN */
            get("/gun") {
                call.respond(GunDao.get())
            }

            /** GUNSMITH */
            post("/gun") {
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 3) {
                    val parameters = call.receiveParameters()

                    val vendorCode = parameters["vendorCode"]
                    val price = parameters["price"]?.toInt()

                    call.respond(GunDao.insert(vendorCode!!, price!!))
                }

            }

            /** GUNSMITH */
            delete("/gun") {
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 3) {
                    val parameters = call.receiveParameters()

                    val id = parameters["id"]!!.toInt()

                    call.respond(GunDao.delete(id))
                }
            }

            /** GUNINORDER */
            post("/guninorder") {

                val parameters = call.receiveParameters()
                        // гет запрос в обличье пост, получаем ордер по айди
                val id = parameters["id"]
                if (id != null) {
                    call.respond(GunInOrderDao.get(id.toInt()))
                }
            }

            /** GUNSMITH */
            get("/guninorder") {
                //Получаем все ганинордеры, без условий
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 3) {
                    call.respond(GunInOrderDao.getAllGIO())
                }

        }

            /** CONSPIRATOR */
            post("/newguninorder") {
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 1) {
                    val parameters = call.receiveParameters()
                    //  настоящий пост запрос
                    val gunId = parameters["gunId"]!!.toInt()
                    val quantity = parameters["quantity"]!!.toInt()
                    val sum = parameters["sum"]!!.toInt()
                    val orderId = parameters["orderId"]!!.toInt()
                    call.respond(GunInOrderDao.insert(gunId, quantity, sum, orderId))
                }
            }

            /** STOREKEEPER */
            /** GUNSMITH */
            patch("/guninorder") {
                val token = call.request.authorization().toString()
                if ((Decoder.decode(token) == 2) || (Decoder.decode(token) == 3)) {
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
            }

            /** USER */

            get("/user") {
                call.respond(UserDao.get())
            }

            /** CAPTAIN */
            post("/user") {
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 0) {
                    val parameters = call.receiveParameters()

                    println(parameters)

                    val login = parameters["login"]
                    val password = parameters["password"]
                    val roleId = parameters["roleId"]!!.toInt()
                    val username = parameters["username"]
                    val cell = parameters["cell"]

                    call.respond(UserDao.insert(login!!, password!!, roleId, username!!, cell!!))
                }


            }

            /** CAPTAIN */
            delete("/user") {
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 0) {
                    val parameters = call.receiveParameters()

                    val id = parameters["id"]!!.toInt()
                    call.respond(UserDao.delete(id))
                }
            }

            /** ORDER */
            get("/order") {
                call.respond(OrderDao.get())
            }

            get("ordernotcancelled") {
                call.respond(OrderDao.getNotCancelled())
            }

            /** CONSPIRATOR */
            post("/order") {
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 1) {
                    val parameters = call.receiveParameters()

                    val customerId = parameters["customerId"]!!.toInt()
                    val commentary = parameters["commentary"]!!
                    val userId = parameters["userId"]!!.toInt()

                    val result = OrderDao.insert(customerId, commentary, userId)

                    if (result > 0) {
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

            }

            patch("/order") {
                val parameters = call.receiveParameters()

                val id = parameters["id"]!!.toInt()
                val stateId = parameters["stateId"]?.toInt()

                val result = OrderDao.updateState(id, stateId!!)

                if (result) {
                    val payload = PushPayload.newBuilder()
                            .setAudience(Selectors.androidChannel("customChannel"))
                            .setNotification(Notifications.alert("Order State #$id is updated now! Check this!"))
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

            /** CAPTAIN */
            patch("/revieworder") {
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 0) {
                    val parameters = call.receiveParameters()

                    val id = parameters["id"]!!.toInt()
                    val stateId = parameters["orderReviewStateId"]?.toInt()

                    val result = OrderDao.updateReviewState(id, stateId!!)

                    if (result) {
                        val payload = PushPayload.newBuilder()
                            .setAudience(Selectors.androidChannel("customChannel"))
                            .setNotification(Notifications.alert("Order #$id reviewed!"))
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
            }

            /** ROLE */
            get("/role") {
                call.respond(RoleDao.get())
            }

            /** CUSTOMERS */
            get("/customer") {
                call.respond(CustomerDao.get())
            }

            /** CONSPIRATOR */
            post("/customer") {

                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 1) {
                    val parameters = call.receiveParameters()

                    val clientName = parameters["client"]
                    val coords = parameters["coords"]
                    val connection = parameters["connection"]

                    call.respond(CustomerDao.insert(clientName!!, coords!!, connection!!))
                }
            }

            /** CONSPIRATOR */
            delete("/customer") {
                val token = call.request.authorization().toString()
                if (Decoder.decode(token) == 1) {
                    val parameters = call.receiveParameters()

                    val id = parameters["id"]!!.toInt()

                    call.respond(CustomerDao.delete(id))
                }

            }
        }
    }
}