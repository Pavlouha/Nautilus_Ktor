package com.pavlouha.sql.deletes

import com.pavlouha.sql.Connection
import java.sql.Statement

object AuthTrunicate {

    @JvmStatic
    fun clean(): Boolean {
        var st: Statement? = null
        var response = false
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            st.execute("TRUNCATE TABLE mpiDB.AUTHS")
            response = true
        } catch (e: Exception) {
            println(e.printStackTrace())
            println("Trunicate AUTHS error")
        }
        return response
    }
}