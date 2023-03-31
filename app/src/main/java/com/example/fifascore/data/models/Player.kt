package com.example.fifascore.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "player_name") val Name: String?,
    @ColumnInfo(name = "player_score") val score: Int = 0
)
