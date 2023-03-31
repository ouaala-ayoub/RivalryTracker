package com.example.fifascore.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fifascore.data.models.Rivalry
import com.example.fifascore.data.repositories.RivalriesRepository

class MainActivityModel(private val rivalriesRepository: RivalriesRepository) : ViewModel() {

    private var _rivalries = MutableLiveData<List<Rivalry>>()
    val rivalries: LiveData<List<Rivalry>>
        get() = rivalriesRepository.getAllRivalries()


    fun addRivalry(rivalry: Rivalry) {
        rivalriesRepository.addRivalry(rivalry)
    }

    fun deleteRivalry(rivalry: Rivalry) {
        rivalriesRepository.deleteRivalry(rivalry)
    }
}