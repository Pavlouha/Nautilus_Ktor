package com.pavlouha.dao

import com.pavlouha.models.GunInOrder
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
}