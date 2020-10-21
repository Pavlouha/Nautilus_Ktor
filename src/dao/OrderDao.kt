package com.pavlouha.dao

import com.pavlouha.models.Order
import com.pavlouha.sql.inserts.InsertOrder
import com.pavlouha.sql.selects.OrderList
import com.pavlouha.sql.updates.UpdateOrderState
import java.util.ArrayList

object OrderDao {

    fun get(): ArrayList<Order?> {
        return OrderList.list()
    }

    fun updateState(id: Int, stateId: Int): Boolean {
        return UpdateOrderState.update(id, stateId)
    }

    fun insert(order: Order): Boolean {
        return InsertOrder.insert(order)
    }
}