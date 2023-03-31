package com.example.fifascore.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fifascore.data.models.Match
import com.example.fifascore.data.models.Rivalry

@Dao
interface MatchesDao {
    @Query("SELECT * FROM Match WHERE rivalry_id == :rivalryId")
    fun getRivalryMatches(rivalryId: Int): LiveData<List<Match>>

    @Insert
    fun addMatch(match: Match)

    @Delete
    fun deleteMatch(match: Match)
}