package com.pavlouha.sql.selects

import com.pavlouha.sql.Connection
import com.pavlouha.models.Gun
import java.sql.Statement
import java.util.*

object GunList {

    @JvmStatic
    fun list(): ArrayList<Gun?> {
        var st: Statement?
        val guns = ArrayList<Gun?>()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val resultSet = st.executeQuery("SELECT * FROM mpiDB.GUNS")
            while (resultSet.next()) {
                guns.add(
                    Gun(resultSet.getInt("ID"), resultSet.getString("VENDOR_CODE"),
                        resultSet.getInt("PRICE"))
                )
            }
        } catch (e: Exception) {
            println("Ошибка при загрузке списка пушек")
        }
        return guns
    }
}