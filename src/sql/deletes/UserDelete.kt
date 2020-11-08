package com.pavlouha.sql.deletes

import com.pavlouha.sql.Connection
import java.sql.Statement

object UserDelete {

    @JvmStatic
    fun delete(id: Int): Boolean {
        var st: Statement?
        var response = false
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            st.execute("DELETE FROM mpiDB.USERS WHERE mpiDB.USERS.ID = $id")
            response = true
        } catch (e: Exception) {
            println(e.printStackTrace())
            println("UserDelete Error")
        }
        return response
    }
}