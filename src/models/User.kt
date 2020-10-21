package com.pavlouha.models

import java.io.Serializable
import java.security.Principal

class User(var id: Int, var login: String, var username: String, var password: String, var role: Role, var cell: String)