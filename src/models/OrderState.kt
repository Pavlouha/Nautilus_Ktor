package com.pavlouha.models

import com.pavlouha.models.abstractions.State
import java.io.Serializable

class OrderState(var orderStateId: Int, var title: String) : State()