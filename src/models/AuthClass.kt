package com.pavlouha.models

import java.io.Serializable
import java.sql.Date

class AuthClass(var authId: Int, var loginDate: String, var user: String, var userId: Int) : Serializable