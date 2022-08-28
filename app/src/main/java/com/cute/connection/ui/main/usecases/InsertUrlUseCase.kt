package com.cute.connection.ui.main.usecases

import com.cute.connection.ui.main.model.UrlResultEntity
import com.cute.connection.ui.main.repo.ShortenUrlRepo
import javax.inject.Inject

class InsertUrlUseCase @Inject constructor(private val repo: ShortenUrlRepo) {

    suspend fun execute(urlResultEntity: UrlResultEntity) = repo.insertUrl(urlResultEntity)

}