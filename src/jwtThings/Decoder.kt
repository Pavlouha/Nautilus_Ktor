package com.pavlouha.jwtThings

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT

object Decoder {

    fun decode(token : String): Int {
        val resultToken = token.removeRange(0, 6)
        val decodedJWT : DecodedJWT = JWT.decode(resultToken)
        return decodedJWT.getClaim("role").asInt()
    }

}