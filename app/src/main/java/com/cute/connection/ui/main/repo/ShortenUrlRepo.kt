package com.cute.connection.ui.main.repo

import com.cute.connection.api.GenericApiResponse
import com.cute.connection.ui.main.datasource.ShortenUrlRemoteDataSource
import com.cute.connection.ui.main.model.NewShortenUrlResponse
import javax.inject.Inject

class ShortenUrlRepo @Inject constructor(
    private val remoteSource : ShortenUrlRemoteDataSource
    ):Repository
    {
        suspend fun getShortenUrl(userLongUrl: String): GenericApiResponse<NewShortenUrlResponse> {
            return remoteSource.getShortenUrl(userLongUrl = userLongUrl)
        }
    }



