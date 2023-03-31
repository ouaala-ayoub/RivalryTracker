package com.example.fifascore.data.repositories

import com.example.fifascore.data.dao.MatchesDao
import com.example.fifascore.data.dao.RivalriesDao
import com.example.fifascore.data.models.Match
import com.example.fifascore.data.models.Rivalry

class MatchesRepository(private val dao: MatchesDao, private val daoRival: RivalriesDao) {
    fun getMatchesByRivalry(rivalryId: Int) = dao.getRivalryMatches(rivalryId)
    fun updateRivalry(rivalry: Rivalry) = daoRival.updateRivalry(rivalry)
    fun getRivalryById(rivalryId: Int) = daoRival.getRivalryById(rivalryId)
    fun addMatch(match: Match) = dao.addMatch(match)
    fun deleteMatch(match: Match) = dao.deleteMatch(match)
}