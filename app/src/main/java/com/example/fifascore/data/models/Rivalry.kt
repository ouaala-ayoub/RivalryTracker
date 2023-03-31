package com.example.fifascore.data.models

import android.os.Parcelable
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

private const val TAG = "Rivalry"

@Parcelize
@Entity
data class Rivalry(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Int? = null,
    @ColumnInfo("game_name") val gameName: String = "Fifa",
    @ColumnInfo("player1_name") val player1Name: String,
    @ColumnInfo("player1_score") var player1Score: Int = 0,
    @ColumnInfo("player2_name") val player2Name: String,
    @ColumnInfo("player2_score") var player2Score: Int = 0,
    @ColumnInfo("num_draws") var numberOfDraws: Int = 0,
) : Parcelable {

    fun addScore(match: Match): Rivalry {
        if (match.team1Score > match.team2Score) {
            player1Score++
        } else if (match.team1Score < match.team2Score) {
            player2Score++
        } else {
            numberOfDraws++
        }
        return this
    }

    fun deleteScore(match: Match): Rivalry {
        if (match.team1Score > match.team2Score) {
            player1Score--
        } else if (match.team1Score < match.team2Score) {
            player2Score--
        } else {
            numberOfDraws--
        }
        return this
    }

    fun player1WinPercentage(): Int {
        if (player1Score == 0 && player2Score == 0) return 0
        val result = (player1Score.toDouble() / (player1Score + player2Score)) * 100
        return result.toInt()
    }

    fun player2WinPercentage(): Int {
        if (player1Score == 0 && player2Score == 0) return 0
        return 100 - player1WinPercentage()
    }
}
