package com.pavlouha.models

import java.io.Serializable
import java.sql.Date

class Order(var orderId: Int, var customer: Customer, var commentary: String, var userId: Int, var userName: String, var orderDate: Date, var orderState: OrderState) : Serializable