package com.pavlouha

import com.pavlouha.jwtThings.model.JwtUser
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication

val ApplicationCall.user get() = authentication.principal<JwtUser>()