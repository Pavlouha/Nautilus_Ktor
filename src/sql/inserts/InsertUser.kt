package com.pavlouha.sql.inserts

import com.pavlouha.models.User
import com.pavlouha.sql.Connection

object InsertUser {

    fun insert(user: User): Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val newCustomerStatement = conn.prepareStatement("INSERT INTO mpiDB.USERS VALUES ( ?, ?, ?,?, ?, ? )")
            newCustomerStatement.setInt(1, user.userId)
            newCustomerStatement.setString(2, user.login)
            newCustomerStatement.setString(3, user.password)
            newCustomerStatement.setInt(4, user.role.roleId)
            newCustomerStatement.setString(5, user.username)
            newCustomerStatement.setString(6, user.cell)
            newCustomerStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("UserInsertError")
        }
        return response
    }
}