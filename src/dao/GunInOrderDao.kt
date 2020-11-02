package com.pavlouha.dao

import com.pavlouha.models.Gun
import com.pavlouha.models.GunInOrder
import com.pavlouha.models.GunState
import com.pavlouha.sql.counters.GunInOrderCounter
import com.pavlouha.sql.inserts.InsertGunInOrder
import com.pavlouha.sql.selects.GunInOrderByOrderList
import com.pavlouha.sql.updates.UpdateGunInOrderState
import java.util.ArrayList

object GunInOrderDao {

    fun get(id: Int): ArrayList<GunInOrder> {
        return GunInOrderByOrderList.list(id)
    }

    fun update(orderId: Int, stateId: Int): Boolean {
       return UpdateGunInOrderState.update(orderId, stateId)
    }

    fun insert(gunId: Int, quantity: Int, sum: Int, orderId: Int): Boolean {
       var id = GunInOrderCounter.check()!!
        id++
        return InsertGunInOrder.insert(GunInOrder(id, Gun(gunId,"", 0), quantity, sum, orderId,
        GunState(0, "")))
    }
}