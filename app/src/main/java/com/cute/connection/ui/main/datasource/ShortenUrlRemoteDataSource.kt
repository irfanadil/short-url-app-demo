package com.cute.connection.ui.main.datasource

import com.cute.connection.api.FailureStatus
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.api.ServerApi
import com.cute.connection.ui.main.model.NewShortenUrlResponse
import com.cute.connection.ui.main.model.UrlRawData
import com.orhanobut.logger.Logger
import javax.inject.Inject

class ShortenUrlRemoteDataSource  @Inject constructor(private val serverApi: ServerApi) {

    suspend fun getShortenUrl(userLongUrl: String): GenericApiResponse<NewShortenUrlResponse> {
        return try {

            val response = serverApi.getShortenUrl( UrlRawData(userLongUrl))
            //Logger.e("response coming = ${response.toString()} ")
            GenericApiResponse.Success(response)
        } catch (e: Throwable) {
            Logger.e("GenericResource.Failure = ${e.message} ")
            GenericApiResponse.Failure(FailureStatus.API_FAIL, 60,e.message)
        }
    }
}