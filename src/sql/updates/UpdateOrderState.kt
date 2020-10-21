package com.pavlouha.sql.updates

import com.pavlouha.sql.Connection

object UpdateOrderState {

    fun update(orderId: Int, orderStateId: Int): Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val updateStatement = conn.prepareStatement("update mpiDB.ORDERS set ORDER_STATE_ID = ? where ID = ?;")
            updateStatement.setInt(1, orderStateId)
            updateStatement.setInt(2, orderId)
            updateStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("OrderUpdateError")
        }
        return response
    }
}