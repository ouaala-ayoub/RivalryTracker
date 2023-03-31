package com.example.fifascore.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo("rivalry_id") val rivalryId: Int,
    @ColumnInfo("team1_name") val team1Name: String,
    @ColumnInfo("team1_score") val team1Score: Int = 0,
    @ColumnInfo("team2_name") val team2Name: String,
    @ColumnInfo("team2_score") val team2Score: Int = 0,
)
