package com.cute.connection.ui.main.usecases

import android.util.Patterns
import com.cute.connection.api.FailureStatus
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.ui.main.model.ShortenUrlResponse
import com.cute.connection.ui.main.repo.ShortenUrlRepo
import javax.inject.Inject

class GetCuteUrlFromApiUseCase @Inject constructor(
    private val repo: ShortenUrlRepo,
    private val insertUrlUseCase: InsertUrlUseCase

) {
    suspend fun execute(potentialUrl: String): GenericApiResponse<ShortenUrlResponse> {
        if (Patterns.WEB_URL.matcher(potentialUrl).matches()) {
            var result = repo.getShortenUrl(potentialUrl)
            when (result) {
                is GenericApiResponse.Success -> {
                    result.value.urlResultEntity?.let {
                        insertUrlUseCase.execute(it)
                    }
                }
                is GenericApiResponse.Failure -> {
                    result = GenericApiResponse.Failure(FailureStatus.API_FAIL, 90, result.message)
                }
                else -> {
                    result =
                        GenericApiResponse.Failure(FailureStatus.OTHER, 90, "Unknown Failure...")
                }
            }
            return result
        } else
            return GenericApiResponse.Failure(
                FailureStatus.OTHER,
                90,
                "Failure due to validation..."
            )
    }
}