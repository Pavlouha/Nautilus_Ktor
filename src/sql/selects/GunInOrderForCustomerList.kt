package com.pavlouha.sql.selects

import com.pavlouha.models.Gun
import com.pavlouha.models.GunInOrder
import com.pavlouha.models.GunState
import com.pavlouha.sql.Connection
import java.sql.Statement

object GunInOrderForCustomerList {

    fun list(): java.util.ArrayList<GunInOrder> {
        val st: Statement?
        val result = ArrayList<GunInOrder>()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val resultSet = st.executeQuery("SELECT mpiDB.GUNS_IN_ORDER.ID, mpiDB.GUNS_IN_ORDER.QUANTITY, mpiDB.GUNS_IN_ORDER.SUM,\n" +
                    "                           mpiDB.GUNS_IN_ORDER.ORDER_ID, mpiDB.GUNS.ID, mpiDB.GUNS.VENDOR_CODE, mpiDB.GUNS.PRICE,\n" +
                    "                                        mpiDB.GUN_STATE.ID, mpiDB.GUN_STATE.STATE FROM mpiDB.GUNS_IN_ORDER\n" +
                    "                                        LEFT JOIN mpiDB.GUNS on mpiDB.GUNS_IN_ORDER.GUN_ID = mpiDB.GUNS.ID\n" +
                    "                    LEFT JOIN mpiDB.GUN_STATE on mpiDB.GUNS_IN_ORDER.GUN_STATE_ID = mpiDB.GUN_STATE.ID;")
            while (resultSet.next()) {
                result.add(
                        GunInOrder(resultSet.getInt("ID"), Gun(resultSet.getInt("GUNS.ID"),
                                resultSet.getString("VENDOR_CODE"), resultSet.getInt("PRICE")),
                                resultSet.getInt("QUANTITY"), resultSet.getInt("SUM"),
                                resultSet.getInt("ORDER_ID"),
                                GunState(resultSet.getInt("GUN_STATE.ID"),
                                        resultSet.getString("STATE")))
                )
            }
        } catch (e: Exception) {
            println("GunInOrderForCustomerListError")
        }
        return result
    }
}