package com.pavlouha.models

import java.io.Serializable

class GunInOrder(var gunInOrderId : Int, var gun: Gun,var quantity: Int,var sum: Int,var orderId: Int,var gunState: GunState): Serializable