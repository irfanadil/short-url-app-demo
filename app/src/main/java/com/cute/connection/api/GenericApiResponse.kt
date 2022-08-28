package com.cute.connection.api

import com.cute.connection.ui.main.model.UrlResultEntity


sealed class GenericApiResponse<out T> {

    class Success<out T>(val value: T) : GenericApiResponse<T>()

    class Failure(
        val failureStatus: FailureStatus = FailureStatus.API_FAIL,
        val code: Int? = null,
        val message: String? = null
    ) : GenericApiResponse<Nothing>()

    object Loading : GenericApiResponse<Nothing>()

    object Default : GenericApiResponse<Nothing>()
}

enum class FailureStatus {
    EMPTY,
    API_FAIL,
    NO_INTERNET,
    OTHER
}


sealed class UrlViewState {
    object Empty : UrlViewState()
    object Loading : UrlViewState()
    data class Success(val urls: List<UrlResultEntity>) : UrlViewState()
    data class Error(val exception: Throwable) : UrlViewState()
}






