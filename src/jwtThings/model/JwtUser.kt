package com.pavlouha.jwtThings.model

import io.ktor.auth.*

class JwtUser (val name: String, val password: String): Principal