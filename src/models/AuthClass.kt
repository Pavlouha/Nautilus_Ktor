package com.pavlouha.models

import java.io.Serializable
import java.sql.Date

class AuthClass(var authId: Int, var loginDate: Date, var user: String, var userId: Int) : Serializable