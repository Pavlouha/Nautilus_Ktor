package com.pavlouha.jwtThings

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.pavlouha.jwtThings.model.JwtUser
import java.util.*

object JwtConfig {

    private const val secret = "lxl3XSIS7p" // use your own secret
    private const val issuer = "com.pavlouha"  // use your own issuer
    private const val validityInMs = 36_000_00 * 24 // 1 day
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    /** Produce a token for this combination of name and password */

    fun generateToken(user: JwtUser): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("name", user.name)
        .withClaim("password", user.password)
        .withExpiresAt(getExpiration())  // optional
        .sign(algorithm)


    /** Calculate the expiration Date based on current time + the given validity */

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}