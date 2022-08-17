package com.cute.connection.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.ui.main.model.NewShortenUrlResponse
import com.cute.connection.ui.main.model.UrlResultEntity
import com.cute.connection.ui.main.usecases.DeleteStoredUrlUseCase
import com.cute.connection.ui.main.usecases.GetAllStoredUrlUseCase
import com.cute.connection.ui.main.usecases.GetCuteUrlFromApiUseCase
import com.cute.connection.ui.main.usecases.InsertUpdateUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val shortenUrlUseCase: GetCuteUrlFromApiUseCase,
    private val insertUpdateUrlUseCase: InsertUpdateUrlUseCase,
    private val getAllStoredUrlUseCase: GetAllStoredUrlUseCase,
    private val deleteStoredUrlUseCase: DeleteStoredUrlUseCase
) : ViewModel() {

    private val _shortUrlResponse = MutableLiveData<GenericApiResponse<NewShortenUrlResponse>>()
    val shortUrlResponse: LiveData<GenericApiResponse<NewShortenUrlResponse>> = _shortUrlResponse

    var urlEntities: LiveData<List<UrlResultEntity>> = getAllStoredUrlUseCase.execute()

    fun getShortUrl(potentialUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = shortenUrlUseCase.execute(potentialUrl)
            _shortUrlResponse.postValue(result)
            if (result is GenericApiResponse.Success) {
                result.value.urlResultEntity?.let {
                    insertUpdateUrlUseCase.execute(it)
                }
            }
        }
    }

    fun deleteStoredUrlById(urlId:Int) = viewModelScope.launch(Dispatchers.IO) {
            deleteStoredUrlUseCase.execute(urlId)
    }



}