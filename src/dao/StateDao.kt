package com.pavlouha.dao

import com.pavlouha.models.GunState
import com.pavlouha.models.OrderReviewState
import com.pavlouha.models.OrderState
import com.pavlouha.sql.selects.GunStateList
import com.pavlouha.sql.selects.OrderReviewStateList
import com.pavlouha.sql.selects.OrderStateList

object StateDao {

    fun getGunInOrderState(): ArrayList<GunState?> {
        return GunStateList.list()
    }

    fun getOrderState(): MutableList<OrderState?> {
        return OrderStateList.list()
    }

    fun getOrderReviewState(): MutableList<OrderReviewState?> {
        return OrderReviewStateList.list()
    }
}