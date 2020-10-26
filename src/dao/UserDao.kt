package com.pavlouha.dao

import com.pavlouha.models.User
import com.pavlouha.sql.counters.AuthCounter
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

    fun insert(user: User): Boolean {
        return InsertUser.insert(user)
    }

    fun delete(id: Int): Boolean {
        return UserDelete.delete(id)
    }

    fun authenticate(login: String, password: String): User? {
        val user = UserAuthentication.userList(login, password)
        if (user != null) {
            var id = AuthCounter.check()!!
            id++
            InsertAuth.insert(id, user.id)
            return user
        }
        return null
    }
}