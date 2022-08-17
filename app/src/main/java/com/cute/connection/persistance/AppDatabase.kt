package com.cute.connection.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cute.connection.ui.main.model.UrlResultEntity

@Database(entities = [UrlResultEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getShortenUrlDao(): ShortenUrlDao
}