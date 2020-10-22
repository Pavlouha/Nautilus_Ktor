package com.pavlouha.sql.selects

import com.pavlouha.models.Role
import com.pavlouha.models.User
import com.pavlouha.sql.Connection
import java.sql.Statement

object UserAuthentication {

    fun userList(login: String, password: String): User? {
        val st: Statement?
        var response = false
        var user : User? = null
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val userSet = st.executeQuery("SELECT mpiDB.USERS.ID, mpiDB.USERS.LOGIN, mpiDB.USERS.PASSWORD, mpiDB.USERS.USERNAME,\n" +
                    "       mpiDB.USERS.CELL, mpiDB.USERS.ROLE_ID, mpiDB.ROLES.TITLE FROM mpiDB.USERS\n" +
                    "        LEFT JOIN mpiDB.ROLES on mpiDB.ROLES.ID = mpiDB.USERS.ROLE_ID\n" +
                    "                    WHERE LOGIN='$login' AND PASSWORD='$password';")
            if (userSet.next()) {
                user = User((userSet.getInt("ID")), userSet.getString("LOGIN"),
                        userSet.getString("USERNAME"), userSet.getString("PASSWORD"),
                        Role(userSet.getInt("ROLE_ID"), userSet.getString("TITLE")),
                        userSet.getString("CELL"))
            }
        } catch (e: Exception) {
            println("UserAuthError")
        }
        return user
    }
}