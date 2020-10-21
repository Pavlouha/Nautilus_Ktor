package com.pavlouha.sql.deletes

import com.pavlouha.sql.Connection
import java.sql.Statement

object GunDelete {

    fun delete(id: Int): Boolean {
        var st: Statement?
        var response = false
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            st.executeQuery("DELETE FROM mpiDB.GUNS WHERE 'ID' = $id")
            response = true
        } catch (e: Exception) {
            println(e.printStackTrace())
            println("GunDelete Error")
        }
        return response
    }
}

