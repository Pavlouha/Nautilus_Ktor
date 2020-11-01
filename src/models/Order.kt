package com.pavlouha.models

import java.io.Serializable

class Order(var orderId: Int, var customer: Customer, var commentary: String, var userId: Int,
            var userName: String, var orderDate: String, var orderState: OrderState, var orderReviewState: OrderReviewState) : Serializable