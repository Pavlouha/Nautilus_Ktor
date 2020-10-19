package com.pavlouha.sql.selects

import com.pavlouha.models.GunState
import com.pavlouha.sql.Connection
import java.sql.Statement
import java.util.ArrayList

object GunStateList {

    @JvmStatic
    fun list(): ArrayList<GunState?> {
        val st: Statement?
        val states = ArrayList<GunState?>()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val resultSet = st.executeQuery("SELECT * FROM mpiDB.GUN_STATE")
            while (resultSet.next()) {
                states.add(
                    GunState(resultSet.getInt("ID"), resultSet.getString("STATE"))
                )
            }
        } catch (e: Exception) {
            println("Ошибка при загрузке списка состояний")
        }
        return states
    }
}