package com.example.savch_andgit.music.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TrackEntity::class], version = 2, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}
