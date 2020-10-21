package com.pavlouha.dao

import com.pavlouha.models.User
import com.pavlouha.sql.deletes.UserDelete
import com.pavlouha.sql.inserts.InsertUser
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
}