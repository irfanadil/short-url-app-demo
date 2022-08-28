package com.cute.connection.ui.main.repo

import com.cute.connection.api.GenericApiResponse
import com.cute.connection.persistance.ShortenUrlDao
import com.cute.connection.ui.main.datasource.ShortenUrlRemoteDataSource
import com.cute.connection.ui.main.model.ShortenUrlResponse
import com.cute.connection.ui.main.model.UrlResultEntity
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ShortenUrlRepo @Inject constructor(
    private val remoteSource : ShortenUrlRemoteDataSource,
    private val localDataSource : ShortenUrlDao
    ):Repository
    {
        suspend fun getShortenUrl(userLongUrl: String): GenericApiResponse<ShortenUrlResponse> {
            return remoteSource.getShortenUrl(userLongUrl = userLongUrl)
        }

        suspend fun insertUrl(urlResultEntity: UrlResultEntity) = localDataSource.insertUrl(urlResultEntity)

        suspend fun deleteStoredUrl(urlId:Int) = localDataSource.deleteStoredUrl(urlId)

        fun getAllStoredUrl() = localDataSource.getAllStoredUrl()

    }



