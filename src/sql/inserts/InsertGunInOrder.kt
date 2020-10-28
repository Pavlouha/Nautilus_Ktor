package com.pavlouha.sql.inserts

import com.pavlouha.models.GunInOrder
import com.pavlouha.sql.Connection

object InsertGunInOrder {

    fun insert(gunInOrder: GunInOrder): Boolean {
       var response = false
        try {
            val conn = Connection.connection
            val newCustomerStatement = conn.prepareStatement("INSERT INTO mpiDB.GUNS_IN_ORDER VALUES ( ?, ?, ?,?, ?, ? )")
            newCustomerStatement.setInt(1, gunInOrder.gunInOrderId)
            newCustomerStatement.setInt(2, gunInOrder.gun.gunId)
            newCustomerStatement.setInt(3, gunInOrder.quantity)
            newCustomerStatement.setInt(4, gunInOrder.sum)
            newCustomerStatement.setInt(5, gunInOrder.orderId)
            newCustomerStatement.setInt(6, 0)
            newCustomerStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("GunInOrderInsertError")
        }
        return response
    }
}