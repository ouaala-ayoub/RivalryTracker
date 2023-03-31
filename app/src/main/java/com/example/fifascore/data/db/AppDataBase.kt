package com.example.fifascore.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fifascore.data.dao.MatchesDao
import com.example.fifascore.data.dao.RivalriesDao
import com.example.fifascore.data.models.Match
import com.example.fifascore.data.models.Player
import com.example.fifascore.data.models.Rivalry

private const val dbName = "rivalries_db"

@Database(entities = [Rivalry::class, Match::class], version = 4)
abstract class RivalriesDataBase : RoomDatabase() {

    abstract fun rivalriesDao(): RivalriesDao
    abstract fun matchesDao(): MatchesDao

    companion object {
        private var rivalriesDataBase: RivalriesDataBase? = null
        fun getInstance(context: Context): RivalriesDataBase {
            if (rivalriesDataBase == null) {
                rivalriesDataBase =
                    Room.databaseBuilder(context, RivalriesDataBase::class.java, dbName)
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return rivalriesDataBase!!
        }
    }
}