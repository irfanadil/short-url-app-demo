package com.cute.connection.ui.main.usecases

import com.cute.connection.persistance.ShortenUrlDao
import com.cute.connection.ui.main.model.UrlResultEntity
import javax.inject.Inject

class InsertUpdateUrlUseCase @Inject constructor(
    private val shortUrlDao: ShortenUrlDao
) {

    suspend fun execute(urlResultEntity:UrlResultEntity){
        shortUrlDao.insertUrl(urlResultEntity)
    }
}