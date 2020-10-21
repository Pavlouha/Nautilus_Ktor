package com.pavlouha

import com.pavlouha.dao.*
import com.pavlouha.models.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.sql.Date
import java.text.SimpleDateFormat


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Authentication) {
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO, WORLD!", contentType = ContentType.Text.Plain)
        }

        /* STATE */
        get("/gunstate") {
            call.respond(StateDao.getGunInOrderState())
        }

        get("/orderstate") {
            call.respond(StateDao.getOrderState())
        }

        /* AUTH TABLE */
        get("/auths") {
           call.respond(AuthDao.get())
        }

        delete("/auths") {
            call.respond(AuthDao.delete())
        }

        /* GUN */
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

        /* GUNINORDER */
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
            call.respond(GunInOrderDao.update(orderId!!, stateId!!))
        }

        /* USER */

        get("/user") {
            call.respond(UserDao.get())
        }

        post("/user") {
            val parameters = call.receiveParameters()

            val id = parameters["id"]!!.toInt()
            val login = parameters["login"]
            val password = parameters["password"]
            val roleId = parameters["roleId"]!!.toInt()
            val roleName = parameters["roleName"]
            val username = parameters["username"]
            val cell = parameters["cell"]

            call.respond(UserDao.insert(User(id, login!!, username!!, password!!, Role(roleId, roleName!!), cell!!)))
        }

        delete("/user") {
            val parameters = call.receiveParameters()

            val id = parameters["id"]!!.toInt()
            call.respond(UserDao.delete(id))
        }

        /* ORDER */
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

            call.respond(OrderDao.insert(Order(
                    id, Customer(customerId, "", "", ""),
                    commentary!!, userId, "", formatter.parse(orderDate) as Date, OrderState(0, "")
            )))
        }

        patch("/order") {
            val parameters = call.receiveParameters()

            val id = parameters["id"]!!.toInt()
            val stateId = parameters["stateId"]?.toInt()
            call.respond(OrderDao.updateState(id, stateId!!))
        }

    }
}