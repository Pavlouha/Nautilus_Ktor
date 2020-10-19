package com.pavlouha

import com.pavlouha.dao.AuthDao
import com.pavlouha.dao.GunDao
import com.pavlouha.dao.StateDao
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.features.*
import java.io.*
import java.util.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.network.util.*
import kotlin.coroutines.*
import kotlinx.coroutines.*
import io.ktor.utils.io.*

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

        /* Auth Table */
        get("/auths") {
           call.respond(AuthDao.get())
        }

        delete("/auths") {
            call.respond(AuthDao.delete())
        }

        /* Gun */
        get("/gun") {
            call.respond(GunDao.get())
        }

        post ("/gun") {
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


    }
}