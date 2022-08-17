package com.cute.connection.ui.main.usecases

import androidx.lifecycle.LiveData
import com.cute.connection.persistance.ShortenUrlDao
import com.cute.connection.ui.main.model.UrlResultEntity
import javax.inject.Inject

class GetAllStoredUrlUseCase @Inject constructor(
    private val shortUrlDao: ShortenUrlDao
) {

    fun execute(): LiveData<List<UrlResultEntity>> {
         return shortUrlDao.getAllStoredUrl()
    }



}