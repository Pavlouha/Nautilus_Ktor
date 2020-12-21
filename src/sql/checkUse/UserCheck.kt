package com.pavlouha.sql.checkUse

import com.pavlouha.sql.Connection
import java.sql.Statement

object UserCheck {

    fun check(id: Int): Boolean {
        val st: Statement?
        var response = false
        try {
            val conn = Connection.connection
            st = conn.createStatement()
           val value = st.executeQuery("SELECT * FROM mpiDB.ORDERS WHERE EXISTS (SELECT * FROM mpiDB.USERS WHERE ORDERS.USER_ID = $id);")
            if ( value.next()) {
                response = true
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            println("UserCheck Error")
        }
        return response
    }

}