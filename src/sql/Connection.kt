package com.pavlouha.sql

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

object Connection {

    @get:Throws(SQLException::class, ClassNotFoundException::class)
    val connection: Connection
        get() {
            val connectionProps = Properties()
            connectionProps["serverTimezone"] = "UTC"
            connectionProps["user"] = "student"
            connectionProps["password"] = "E9ZHvC+."
            Class.forName("com.mysql.cj.jdbc.Driver")
            return DriverManager.getConnection("jdbc:mysql://195.2.78.182:3306/", connectionProps)
        }
}