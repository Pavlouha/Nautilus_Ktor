package com.pavlouha.sql.inserts

import com.pavlouha.models.Customer
import com.pavlouha.sql.Connection

object InsertCustomer {

    fun insert(customer: Customer): Boolean {
        var response = false
        try {
            val conn = Connection.connection
            val newCustomerStatement = conn.prepareStatement("INSERT INTO mpiDB.CUSTOMERS VALUES ( ?, ?, ?, ? )")
            newCustomerStatement.setInt(1, customer.id)
            newCustomerStatement.setString(2, customer.client)
            newCustomerStatement.setString(3, customer.coords)
            newCustomerStatement.setString(4, customer.connection)
            newCustomerStatement.execute()
            response = true
        } catch (e: Exception) {
            print(e.printStackTrace())
            print("CustomerInsertError")
        }
        return response
    }
}