package com.example.fifascore.data.repositories

import com.example.fifascore.data.dao.RivalriesDao
import com.example.fifascore.data.models.Rivalry

class RivalriesRepository(private val dao: RivalriesDao) {
    fun getAllRivalries() = dao.getAllRivalries()
    fun addRivalry(rivalry: Rivalry) = dao.insertRivalry(rivalry)
    fun deleteRivalry(rivalry: Rivalry) = dao.deleteRivalry(rivalry)
    fun updateRivalry(rivalry: Rivalry) = dao.updateRivalry(rivalry)
}