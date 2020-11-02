package com.pavlouha.dao

import com.pavlouha.models.Order
import com.pavlouha.sql.counters.OrderCounter
import com.pavlouha.sql.inserts.InsertOrder
import com.pavlouha.sql.selects.OrderList
import com.pavlouha.sql.selects.OrdersNotCancelledList
import com.pavlouha.sql.updates.UpdateOrderReviewState
import com.pavlouha.sql.updates.UpdateOrderState
import java.util.ArrayList

object OrderDao {

    fun get(): ArrayList<Order?> {
        return OrderList.list()
    }

    fun getNotCancelled(): ArrayList<Order?> {
        return OrdersNotCancelledList.list()
    }

    fun updateState(id: Int, stateId: Int): Boolean {
        return UpdateOrderState.update(id, stateId)
    }

    fun updateReviewState(id: Int, orderReviewStateId: Int): Boolean {
        return UpdateOrderReviewState.update(id, orderReviewStateId)
    }

    fun insert(customerId: Int, commentary: String, userId: Int): Int {
        var id = OrderCounter.check()
        id++
        val b = InsertOrder.insert(id, customerId, commentary, userId)
        if (b) {
            return id
        }
        return -1
    }
}