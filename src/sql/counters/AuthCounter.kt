package com.pavlouha.sql.counters

import com.pavlouha.sql.Connection
import java.sql.Statement

object AuthCounter {

    fun check(): Int? {
        val st: Statement?
        var counter: Int? = null
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val value = st.executeQuery("select max(ID) FROM mpiDB.AUTHS")
            while (value.next()) {
                counter = value.getInt("max(ID)")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return counter
    }
}