package com.cute.connection.ui.main.usecases

import com.cute.connection.ui.main.repo.ShortenUrlRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllStoredUrlUseCase @Inject constructor(private val repo: ShortenUrlRepo) {

    fun invoke() = repo.getAllStoredUrl().distinctUntilChanged().flowOn(Dispatchers.IO)

}