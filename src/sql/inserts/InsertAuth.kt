package com.pavlouha.sql.inserts

import com.pavlouha.sql.Connection
import java.sql.Statement

object InsertAuth {

    fun insert(id: Int, userId: Int): Boolean {
        var response = false
        val st: Statement?
        try {
            val conn = Connection.connection
            val newCustomerStatement = conn.prepareStatement("INSERT INTO mpiDB.AUTHS VALUES ( ?, CURRENT_TIMESTAMP, ? )")
            newCustomerStatement.setInt(1, id)
            newCustomerStatement.setInt(2, userId)
            newCustomerStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("AuthInsertError")
        }
        return response
    }
}