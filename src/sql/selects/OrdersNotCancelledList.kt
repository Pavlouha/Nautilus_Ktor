package com.pavlouha.sql.selects

import com.pavlouha.models.Customer
import com.pavlouha.models.Order
import com.pavlouha.models.OrderReviewState
import com.pavlouha.models.OrderState
import com.pavlouha.sql.Connection
import java.sql.Statement
import java.util.ArrayList

object OrdersNotCancelledList {

    fun list(): ArrayList<Order?> {
        val st: Statement?
        val orders = ArrayList<Order?>()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val resultSet = st.executeQuery("SELECT mpiDB.ORDERS.ID, mpiDB.ORDERS.CUSTOMER_ID, mpiDB.CUSTOMERS.CLIENT, mpiDB.CUSTOMERS.COORDS,\n" +
                    "                                              mpiDB.CUSTOMERS.CONNECTION, mpiDB.ORDERS.COMMENTARY, mpiDB.ORDERS.USER_ID, mpiDB.USERS.USERNAME,\n" +
                    "                                          mpiDB.ORDERS.ORDERDATE, mpiDB.ORDERS.ORDER_STATE_ID, mpiDB.ORDER_STATE.STATE,\n" +
                    "                         mpiDB.ORDERS.ORDER_REVIEW_STATE_ID, mpiDB.ORDER_REVIEW_STATE.STATE\n" +
                    "                                              FROM mpiDB.ORDERS\n" +
                    "                                                      LEFT JOIN mpiDB.CUSTOMERS on mpiDB.ORDERS.CUSTOMER_ID = mpiDB.CUSTOMERS.ID\n" +
                    "                                                        LEFT JOIN mpiDB.USERS on mpiDB.ORDERS.USER_ID = mpiDB.USERS.ID\n" +
                    "                                                           LEFT JOIN mpiDB.ORDER_STATE on mpiDB.ORDERS.ORDER_STATE_ID = mpiDB.ORDER_STATE.ID\n" +
                    "                                         LEFT JOIN mpiDB.ORDER_REVIEW_STATE on mpiDB.ORDERS.ORDER_REVIEW_STATE_ID = mpiDB.ORDER_REVIEW_STATE.ID\n" +
                    "WHERE mpiDB.ORDERS.ORDER_REVIEW_STATE_ID = 0 OR mpiDB.ORDERS.ORDER_REVIEW_STATE_ID = 1;")
            while (resultSet.next()) {
                orders.add(
                        Order(resultSet.getInt("ID"), Customer(resultSet.getInt("CUSTOMER_ID"),
                                resultSet.getString("CLIENT"), resultSet.getString("COORDS"),
                                resultSet.getString("CONNECTION")), resultSet.getString("COMMENTARY"),
                                resultSet.getInt("USER_ID"), resultSet.getString("USERNAME"),
                                resultSet.getString("ORDERDATE"), OrderState(resultSet.getInt("ORDER_STATE_ID"),
                                resultSet.getString("ORDER_STATE.STATE"),),
                                OrderReviewState(resultSet.getInt("ORDER_REVIEW_STATE_ID"),
                                        resultSet.getString("ORDER_REVIEW_STATE.STATE")))
                )
            }
        } catch (e: Exception) {
            println("OrderListError")
        }
        return orders
    }
}