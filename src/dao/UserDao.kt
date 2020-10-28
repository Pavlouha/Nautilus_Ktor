package com.pavlouha.dao

import com.pavlouha.models.Role
import com.pavlouha.models.User
import com.pavlouha.sql.counters.AuthCounter
import com.pavlouha.sql.counters.UserCounter
import com.pavlouha.sql.deletes.UserDelete
import com.pavlouha.sql.inserts.InsertAuth
import com.pavlouha.sql.inserts.InsertUser
import com.pavlouha.sql.selects.UserAuthentication
import com.pavlouha.sql.selects.UserList
import java.util.ArrayList

object UserDao {

    fun get(): ArrayList<User?> {
        return UserList.userList()
    }

    fun insert(login: String, password: String, roleId: Int, username: String, cell: String): Boolean {
        var id = UserCounter.check()
        id++
        return InsertUser.insert(User(id, login, username, password, Role(roleId, ""), cell))
    }

    fun delete(id: Int): Boolean {
        return UserDelete.delete(id)
    }

    fun authenticate(login: String, password: String): User? {
        val user = UserAuthentication.userList(login, password)
        if (user != null) {
            var id = AuthCounter.check()!!
            id++
            InsertAuth.insert(id, user.userId)
            return user
        }
        return null
    }
}