package com.cute.connection.ui.main.repo

import com.cute.connection.api.GenericApiResponse
import com.cute.connection.persistance.ShortenUrlDao
import com.cute.connection.ui.main.datasource.ShortenUrlRemoteDataSource
import com.cute.connection.ui.main.model.ShortenUrlResponse
import com.cute.connection.ui.main.model.UrlResultEntity
import javax.inject.Inject

class ShortenUrlRepo @Inject constructor(
    private val remoteDataSource : ShortenUrlRemoteDataSource,
    private val localDataSource : ShortenUrlDao
    ):Repository
    {
        suspend fun getShortenUrl(userLongUrl: String): GenericApiResponse<ShortenUrlResponse> {
            return remoteDataSource.getShortenUrl(userLongUrl = userLongUrl)
        }

        suspend fun insertUrl(urlResultEntity: UrlResultEntity) = localDataSource.insertUrl(urlResultEntity)

        suspend fun deleteStoredUrl(urlId:Int) = localDataSource.deleteStoredUrl(urlId)

        suspend fun updateClickUrlToCopiedState(urlId:Int) {
            localDataSource.resetSelectUrlToInitialState()
            localDataSource.updateClickUrlToCopiedState(urlId)
        }

        fun getAllStoredUrl() = localDataSource.getAllStoredUrl()

    }



