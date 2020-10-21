package com.pavlouha.sql.selects

import com.pavlouha.sql.Connection
import com.pavlouha.models.AuthClass
import java.sql.Statement
import java.util.ArrayList

object AuthList {

    @JvmStatic
    fun list(): ArrayList<AuthClass> {
        val st: Statement?
        val auths = ArrayList<AuthClass>()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val authSet = st.executeQuery("SELECT mpiDB.AUTHS.ID, mpiDB.AUTHS.LOGIN_DATE, mpiDB.AUTHS.USER_ID,\n" +
                    "mpiDB.USERS.USERNAME FROM mpiDB.AUTHS\n" +
                    "LEFT JOIN mpiDB.USERS on mpiDB.AUTHS.USER_ID = mpiDB.USERS.ID;")
            while (authSet.next()) {
                auths.add(AuthClass(authSet.getInt("ID"), authSet.getDate("LOGIN_DATE"),
                        authSet.getString("USERNAME"), authSet.getInt("USER_ID")))
            }
        } catch (e: Exception) {
            println("AuthListError")
        }
        return auths
    }
}