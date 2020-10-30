package com.pavlouha.sql.selects

import com.pavlouha.models.OrderReviewState
import com.pavlouha.models.OrderState
import com.pavlouha.sql.Connection
import java.sql.Statement

object OrderReviewStateList {

    @JvmStatic
    fun list(): MutableList<OrderReviewState?> {
        val st: Statement?
        val states : MutableList<OrderReviewState?> = mutableListOf()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val resultSet = st.executeQuery("SELECT * FROM mpiDB.ORDER_REVIEW_STATE")
            while (resultSet.next()) {
                states.add(OrderReviewState(resultSet.getInt("ID"), resultSet.getString("STATE")))
            }
        } catch (e: Exception) {
            println("OrderStateListError")
        }
        return states
    }

}