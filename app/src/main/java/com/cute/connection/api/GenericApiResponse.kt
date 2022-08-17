package com.cute.connection.api


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






