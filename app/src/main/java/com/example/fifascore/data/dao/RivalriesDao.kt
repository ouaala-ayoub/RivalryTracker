package com.example.fifascore.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fifascore.data.models.Rivalry
import kotlinx.coroutines.flow.Flow

@Dao
interface RivalriesDao {

    @Query("SELECT * FROM Rivalry")
    fun getAllRivalries(): LiveData<List<Rivalry>>

    @Query("SELECT * FROM Rivalry WHERE id =:rivalryId")
    fun getRivalryById(rivalryId: Int): Rivalry

    @Update
    fun updateRivalry(rivalry: Rivalry)

    @Insert
    fun insertRivalry(rivalry: Rivalry)

    @Delete
    fun deleteRivalry(rivalryToDelete: Rivalry)

}