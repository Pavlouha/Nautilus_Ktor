package com.pavlouha.models

import com.pavlouha.models.abstractions.State
import java.io.Serializable

class GunState(var gunStateId: Int, var title: String) : State()