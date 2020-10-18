package com.pavlouha.sql.updates

import com.pavlouha.sql.Connection
import java.sql.Statement

object UpdateGunInOrderState {

    fun update(orderId: Int, gunStateId: Int):Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val newCustomerStatement = conn.prepareStatement("update mpiDB.GUNS_IN_ORDER set 'GUN_STATE_ID' = ? where 'ORDER_ID' = ?;")
            newCustomerStatement.setInt(1, gunStateId)
            newCustomerStatement.setInt(2, orderId)
            newCustomerStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("GunInOrderUpdateError")
        }
        return response
    }
}