package com.cute.connection.ui.main.usecases

import android.util.Patterns
import com.cute.connection.api.FailureStatus
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.ui.main.model.NewShortenUrlResponse
import com.cute.connection.ui.main.repo.ShortenUrlRepo
import javax.inject.Inject

class GetCuteUrlFromApiUseCase @Inject constructor(
    private val repo: ShortenUrlRepo

) {
    suspend fun execute(potentialUrl: String): GenericApiResponse<NewShortenUrlResponse> {
        return if (Patterns.WEB_URL.matcher(potentialUrl).matches())
            repo.getShortenUrl(potentialUrl)
        else
            GenericApiResponse.Failure(FailureStatus.OTHER, 90, "Failure due to validation...")
    }
}