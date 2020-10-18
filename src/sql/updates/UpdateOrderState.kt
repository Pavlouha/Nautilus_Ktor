package com.pavlouha.sql.updates

import com.pavlouha.sql.Connection

object UpdateOrderState {

    fun update(orderId: Int, orderStateId: Int): Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val newCustomerStatement = conn.prepareStatement("update mpiDB.ORDERS set 'ORDER_STATE_ID' = ? where 'ID' = ?;")
            newCustomerStatement.setInt(1, orderStateId)
            newCustomerStatement.setInt(2, orderId)
            newCustomerStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("OrderUpdateError")
        }
        return response
    }
}