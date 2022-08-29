package com.cute.connection.ui.main.usecases

import com.cute.connection.ui.main.repo.ShortenUrlRepo
import javax.inject.Inject

class UpdateStoredUrlUseCase @Inject constructor(private val repo: ShortenUrlRepo) {

    suspend fun execute(urlId: Int)  {
        repo.updateClickUrlToCopiedState(urlId)
    }

}