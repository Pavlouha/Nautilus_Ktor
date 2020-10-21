package com.pavlouha.jwtThings

import io.ktor.auth.*

class JwtUser (val name: String, val password: String): Principal