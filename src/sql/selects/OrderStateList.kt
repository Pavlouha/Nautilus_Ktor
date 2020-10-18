package com.pavlouha.sql.selects

import com.pavlouha.models.OrderState
import com.pavlouha.sql.Connection
import java.sql.Statement
import java.util.ArrayList

object OrderStateList {

    @JvmStatic
    fun list(): ArrayList<OrderState?> {
        val st: Statement?
        val states = ArrayList<OrderState?>()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val resultSet = st.executeQuery("SELECT * FROM mpiDB.ORDER_STATE")
            while (resultSet.next()) {
                states.add(
                    OrderState(resultSet.getInt("ID"), resultSet.getString("TITLE"))
                )
            }
        } catch (e: Exception) {
            println("Ошибка при загрузке списка состояний")
        }
        return states
    }
}