package com.pavlouha.sql.updates

import com.pavlouha.sql.Connection

object UpdateOrderReviewState {

    fun update(orderId: Int, orderReviewStateId: Int): Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val updateStatement = conn.prepareStatement("update mpiDB.ORDERS set ORDER_REVIEW_STATE_ID = ? where ID = ?;")
            updateStatement.setInt(1, orderReviewStateId)
            updateStatement.setInt(2, orderId)
            updateStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("OrderReviewUpdateError")
        }
        return response
    }
}