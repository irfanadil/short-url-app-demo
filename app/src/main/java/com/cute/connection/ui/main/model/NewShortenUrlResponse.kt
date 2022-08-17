package com.cute.connection.ui.main.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class NewShortenUrlResponse(
    @SerializedName("data") val urlResultEntity: UrlResultEntity?,
    @SerializedName("errors") val responseErrors: ResponseErrors
)


data class ResponseErrors(
    val errorCode: String?,
    val errorMessage: String?
)

@Entity(tableName = "urlHistoryTable" , indices = [Index(value = ["originalUrl"], unique = true)])
data class UrlResultEntity(
    val createdAt: String,
    val originalUrl: String,
    val shortUrl: String,
    var recycleItemState:Boolean= false,
    val title: String?,
    val totalClicks: Int,
    val updatedAt: String,
    @PrimaryKey(autoGenerate = true)
    var autoId:Int = 0
)


// https://urlo.in/docs/
data class UrlRawData (val originalUrl:String)