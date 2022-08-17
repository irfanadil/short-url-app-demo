package com.cute.connection.di

import android.content.Context
import androidx.room.Room
import com.cute.connection.persistance.AppDatabase
import com.cute.connection.persistance.ShortenUrlDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideShortenUrlDao(appDatabase: AppDatabase): ShortenUrlDao {
        return appDatabase.getShortenUrlDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "ShortenUrlDatabase"
        ).build()
    }
}