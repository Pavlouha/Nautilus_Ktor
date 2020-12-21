package com.pavlouha.dao

import com.pavlouha.models.Customer
import com.pavlouha.sql.checkUse.CustomerCheck
import com.pavlouha.sql.counters.CustomerCounter
import com.pavlouha.sql.deletes.CustomerDelete
import com.pavlouha.sql.inserts.InsertCustomer
import com.pavlouha.sql.selects.CustomerList
import java.util.ArrayList

object CustomerDao {

    fun get(): ArrayList<Customer?> {
        return CustomerList.list()
    }

    fun insert(client: String, coords: String, connection: String): Boolean {
        var id = CustomerCounter.check()
        id++
        return InsertCustomer.insert(Customer(id, client, coords, connection))
    }

    fun delete(id: Int): String {
        return if (!CustomerCheck.check(id)) {
            CustomerDelete.delete(id)
        } else {
            "false"
        }
    }

}