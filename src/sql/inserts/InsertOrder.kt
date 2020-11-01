package com.pavlouha.sql.inserts

import com.pavlouha.models.Order
import com.pavlouha.sql.Connection

object InsertOrder {

    fun insert(orderId: Int, customerId: Int, commentary: String, userId: Int): Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val newCustomerStatement = conn.prepareStatement("INSERT INTO mpiDB.ORDERS VALUES ( ?, ?, ?,?, CURRENT_TIMESTAMP, ?, ? )")
            newCustomerStatement.setInt(1, orderId)
            newCustomerStatement.setInt(2, customerId)
            newCustomerStatement.setString(3, commentary)
            newCustomerStatement.setInt(4, userId)
            newCustomerStatement.setInt(5, 0)
            newCustomerStatement.setInt(6, 0)
            newCustomerStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("OrderInsertError")
        }
        return response
    }
}