package com.cute.connection.api

import com.cute.connection.ui.main.model.ShortenUrlResponse
import com.cute.connection.ui.main.model.UrlRawData
import retrofit2.http.*

interface ServerApi {

    @POST("api/short-url")
    suspend fun getShortenUrl(@Body rawData:UrlRawData) :ShortenUrlResponse

}


