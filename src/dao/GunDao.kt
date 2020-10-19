package com.pavlouha.dao

import com.pavlouha.models.Gun
import com.pavlouha.sql.counters.GunCounter
import com.pavlouha.sql.deletes.GunDelete
import com.pavlouha.sql.inserts.InsertGun
import com.pavlouha.sql.selects.GunList
import java.util.ArrayList

object GunDao {

    fun get(): ArrayList<Gun?> {
        return GunList.list()
    }

    fun insert(vendorCode: String, price: Int): Boolean {
       var id = GunCounter.check()
        id++
        return InsertGun.insert(Gun(id, vendorCode, price))
    }

    fun delete(id: Int): Boolean {
        return GunDelete.delete(id)
    }
}