package com.pavlouha.dao

import com.pavlouha.models.AuthClass
import com.pavlouha.sql.deletes.AuthTrunicate
import com.pavlouha.sql.selects.AuthList
import java.util.ArrayList

object AuthDao {

    fun get(): ArrayList<AuthClass> {
        return AuthList.list()
    }

    fun delete(): Boolean {
        return AuthTrunicate.clean()
    }
}