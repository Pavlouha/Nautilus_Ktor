package com.pavlouha.sql.inserts

import com.pavlouha.models.Order
import com.pavlouha.sql.Connection

object InsertOrder {

    fun insert(order: Order): Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val newCustomerStatement = conn.prepareStatement("INSERT INTO mpiDB.ORDERS VALUES ( ?, ?, ?,?, CURRENT_TIMESTAMP, ?, ? )")
            newCustomerStatement.setInt(1, order.orderId)
            newCustomerStatement.setInt(2, order.customer.customerId)
            newCustomerStatement.setString(3, order.commentary)
            newCustomerStatement.setInt(4, order.userId)
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