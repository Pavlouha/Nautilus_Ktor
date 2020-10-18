package com.pavlouha.sql.inserts

import com.pavlouha.models.Gun
import com.pavlouha.sql.Connection

object InsertGun {

    fun insert(gun: Gun): Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val newStatement = conn.prepareStatement("INSERT INTO mpiDB.GUNS VALUES ( ?, ?, ?)")
            newStatement.setInt(1, gun.id)
            newStatement.setString(2, gun.vendor_code)
            newStatement.setInt(3, gun.price)
            newStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("GunInsertError")
        }
        return response
    }
}