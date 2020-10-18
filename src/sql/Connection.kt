package com.pavlouha.sql

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object Connection {
    @get:Throws(SQLException::class, ClassNotFoundException::class)
    val connection: Connection
        get() {
            Class.forName("org.h2.Driver")
            return DriverManager.getConnection("jdbc:h2:file:C:/Users/pav5a/Desktop/Nautilus/nautilus_server/test",
                "sa", "1")
        }
}