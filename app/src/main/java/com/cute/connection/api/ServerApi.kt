package com.cute.connection.api

import com.cute.connection.ui.main.model.NewShortenUrlResponse
import retrofit2.http.*

interface ServerApi {


    @POST("api/short-url")
    suspend fun getShortenUrl(@Body  rawData:RawData) :NewShortenUrlResponse


}

data class RawData (val originalUrl:String)
