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
            st.execute("DELETE FROM mpiDB.GUNS WHERE mpiDB.GUNS.ID = $id")
            response = true
        } catch (e: Exception) {
            println(id)
            println(e.printStackTrace())
            println("GunDelete Error")
        }
        return response
    }
}

