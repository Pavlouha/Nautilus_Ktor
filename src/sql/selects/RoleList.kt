package com.pavlouha.sql.selects

import com.pavlouha.sql.Connection
import com.pavlouha.models.Role
import java.sql.Statement
import java.util.*

object RoleList {

    @JvmStatic
    fun roleList(): ArrayList<Role?> {
        val st: Statement?
        val roles = ArrayList<Role?>()
        try {
            val conn = Connection.connection
            st = conn.createStatement()
            val roleSet = st.executeQuery("SELECT * FROM mpiDB.ROLES")
            while (roleSet.next()) {
                roles.add(Role(roleSet.getInt("ID"), roleSet.getString("TITLE")))
            }
        } catch (e: Exception) {
            println("RoleListError")
        }
        return roles
    }
}