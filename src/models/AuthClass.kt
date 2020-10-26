package com.pavlouha.models

import java.io.Serializable
import java.sql.Date

class AuthClass(var id: Int, var loginDate: Date, var user: String, var userId: Int) : Serializable