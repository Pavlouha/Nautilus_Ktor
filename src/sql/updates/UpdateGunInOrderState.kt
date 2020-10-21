package com.pavlouha.sql.updates

import com.pavlouha.sql.Connection

object UpdateGunInOrderState {

    fun update(orderId: Int, gunStateId: Int):Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val newStatement = conn.prepareStatement("update mpiDB.GUNS_IN_ORDER set GUN_STATE_ID = ? where ORDER_ID = ?;")
            newStatement.setInt(1, gunStateId)
            newStatement.setInt(2, orderId)
            newStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("GunInOrderUpdateError")
        }
        return response
    }
}