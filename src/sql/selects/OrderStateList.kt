package com.pavlouha.sql.selects

import com.google.gson.Gson
import com.pavlouha.models.OrderState
import com.pavlouha.sql.Connection
import java.sql.Statement
import java.util.ArrayList

object OrderStateList {

    @JvmStatic
    fun list(): MutableList<OrderState?> {
        val st: Statement?
        val states : MutableList<OrderState?> = mutableListOf()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val resultSet = st.executeQuery("SELECT * FROM mpiDB.ORDER_STATE")
            while (resultSet.next()) {
                states.add(OrderState(resultSet.getInt("ID"), resultSet.getString("STATE")))
            }
        } catch (e: Exception) {
            println("OrderStateListError")
        }
        return states
    }
}