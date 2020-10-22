package com.pavlouha.dao

import com.pavlouha.models.Role
import com.pavlouha.sql.selects.RoleList
import java.util.ArrayList

object RoleDao {

   fun get(): ArrayList<Role?> {
       return RoleList.roleList()
    }
}