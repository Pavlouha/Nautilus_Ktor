package com.pavlouha.sql.selects

import com.pavlouha.models.Role
import com.pavlouha.sql.Connection
import com.pavlouha.models.User
import java.sql.Statement
import java.util.*

object UserList {

    @JvmStatic
    fun userList(): ArrayList<User?> {
        val st: Statement?
        val users = ArrayList<User?>()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val userSet = st.executeQuery("SELECT mpiDB.USERS.*, mpiDB.ROLES.TITLE FROM mpiDB.USERS\n" +
                    "LEFT JOIN mpiDB.ROLES on mpiDB.ROLES.ID = mpiDB.USERS.ROLE_ID;")
            while (userSet.next()) {
                users.add(User(userSet.getInt("ID"), userSet.getString("LOGIN"),
                userSet.getString("USERNAME"), userSet.getString("PASSWORD"),
                Role(userSet.getInt("ROLE_ID"), userSet.getString("TITLE")),
                userSet.getString("CELL")))
            }
        } catch (e: Exception) {
            println("UserListError")
        }
        return users
    }
}