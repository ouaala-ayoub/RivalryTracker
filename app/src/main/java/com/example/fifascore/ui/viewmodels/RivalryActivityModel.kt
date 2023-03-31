package com.example.fifascore.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fifascore.data.models.Match
import com.example.fifascore.data.models.Rivalry
import com.example.fifascore.data.repositories.MatchesRepository

class RivalryActivityModel(
    private val rivalryId: Rivalry,
    private val matchesRepository: MatchesRepository
) : ViewModel() {

    private var _rivalry = MutableLiveData(rivalryId)
    private var _matches = MutableLiveData<List<Match>>()

    val rivalry: LiveData<Rivalry>
        get() = _rivalry
    val matches: LiveData<List<Match>>
        get() = matchesRepository.getMatchesByRivalry(rivalryId.id!!)

    fun getRivalryById(rivalryId: Rivalry) {
        matchesRepository.getRivalryById(rivalryId.id!!)
    }

    fun addMatch(match: Match) {
        matchesRepository.addMatch(match)
        val test = rivalry.value?.addScore(match)
        if (test != null) {
            matchesRepository.updateRivalry(test)
            _rivalry.postValue(test)
        }
    }

    fun deleteMatch(match: Match) {
        matchesRepository.deleteMatch(match)
        val test = rivalry.value?.deleteScore(match)
        if (test != null) {
            matchesRepository.updateRivalry(test)
            _rivalry.postValue(test)
        }
    }
}