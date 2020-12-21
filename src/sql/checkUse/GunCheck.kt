package com.pavlouha.sql.checkUse

import com.pavlouha.sql.Connection
import java.sql.Statement

object GunCheck {

    fun check(id: Int): Boolean {
        val st: Statement?
        var response = false
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val value = st.executeQuery("SELECT * FROM mpiDB.GUNS_IN_ORDER WHERE EXISTS (SELECT * FROM mpiDB.GUNS WHERE GUNS_IN_ORDER.GUN_ID = $id);")
            if ( value.next()) {
                response = true
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            println("CustomerCheck Error")
        }
        return response
    }
}