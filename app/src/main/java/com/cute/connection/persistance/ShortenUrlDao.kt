package com.cute.connection.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cute.connection.ui.main.model.UrlResultEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@Dao
interface ShortenUrlDao {

    @Query("SELECT * FROM urlHistoryTable")
    fun getAllStoredUrl(): Flow<List<UrlResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUrl(urlResponseEntity: UrlResultEntity)

    @Query("DELETE FROM urlHistoryTable WHERE autoId=:id")
    suspend fun deleteStoredUrl(id: Int)
}