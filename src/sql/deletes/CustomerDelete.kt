package com.pavlouha.sql.deletes

import com.pavlouha.sql.Connection
import java.sql.Statement

object CustomerDelete {

    fun delete(id: Int): Boolean {
        val st: Statement?
        var response = false
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            st.execute("DELETE FROM mpiDB.CUSTOMERS WHERE mpiDB.CUSTOMERS.ID = $id")
            response = true
        } catch (e: Exception) {
            println(e.printStackTrace())
            println("Delete Customer Error")
        }
        return response
    }
}