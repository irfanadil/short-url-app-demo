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

    // By ignoring, you can also avoid data refresh as fun getAllStoredUrl() using Flow and it will update ui if record changes...
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUrl(urlResponseEntity: UrlResultEntity)

    @Query("DELETE FROM urlHistoryTable WHERE autoId=:id")
    suspend fun deleteStoredUrl(id: Int)

    @Query("UPDATE urlHistoryTable set recycleItemState=1 WHERE autoId=:id")
    suspend fun updateClickUrlToCopiedState(id: Int)

    @Query("UPDATE urlHistoryTable set recycleItemState=0 WHERE recycleItemState=1")
    suspend fun resetSelectUrlToInitialState()
}