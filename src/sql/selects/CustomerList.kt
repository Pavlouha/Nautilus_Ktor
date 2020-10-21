package com.pavlouha.sql.selects

import com.pavlouha.models.Customer
import com.pavlouha.sql.Connection
import java.sql.Statement
import java.util.ArrayList

object CustomerList {

    @JvmStatic
    fun list(): ArrayList<Customer?> {
        val st: Statement?
        val customers = ArrayList<Customer?>()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val resultSet = st.executeQuery("SELECT * FROM mpiDB.CUSTOMERS")
            while (resultSet.next()) {
                customers.add(
                    Customer(resultSet.getInt("ID"), resultSet.getString("CLIENT"),
                    resultSet.getString("COORDS"), resultSet.getString("CONNECTION"))
                )
            }
        } catch (e: Exception) {
            println("CustomerListError")
        }
        return customers
    }
}