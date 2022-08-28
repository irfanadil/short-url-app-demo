package com.cute.connection.ui.main.datasource

import com.cute.connection.api.FailureStatus
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.api.ServerApi
import com.cute.connection.ui.main.model.ShortenUrlResponse
import com.cute.connection.ui.main.model.UrlRawData
import javax.inject.Inject

class ShortenUrlRemoteDataSource  @Inject constructor(private val serverApi: ServerApi) {

    suspend fun getShortenUrl(userLongUrl: String): GenericApiResponse<ShortenUrlResponse> {
        return try {
            val response = serverApi.getShortenUrl( UrlRawData(userLongUrl))
            GenericApiResponse.Success(response)
        } catch (e: Throwable) {
            GenericApiResponse.Failure(FailureStatus.API_FAIL, 60,e.message)
        }
    }
}