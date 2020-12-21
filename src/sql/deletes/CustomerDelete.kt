package com.pavlouha.sql.deletes

import com.pavlouha.sql.Connection
import java.sql.Statement

object CustomerDelete {

    fun delete(id: Int): String {
        val st: Statement?
        var response = ""
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            st.execute("DELETE FROM mpiDB.CUSTOMERS WHERE mpiDB.CUSTOMERS.ID = $id")
            response = "true"
        } catch (e: Exception) {
            println(e.printStackTrace())
            println("DeleteCustomer Error")
        }
        return response
    }
}