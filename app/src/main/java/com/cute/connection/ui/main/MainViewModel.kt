package com.cute.connection.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.api.UrlViewState
import com.cute.connection.ui.main.model.ShortenUrlResponse
import com.cute.connection.ui.main.screens.listing.SingleLiveEvent
import com.cute.connection.ui.main.usecases.DeleteStoredUrlUseCase
import com.cute.connection.ui.main.usecases.GetAllStoredUrlUseCase
import com.cute.connection.ui.main.usecases.GetCuteUrlFromApiUseCase
import com.cute.connection.ui.main.usecases.UpdateStoredUrlUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCuteUrlFromApiUseCase: GetCuteUrlFromApiUseCase,
    private val getAllStoredUrlUseCase: GetAllStoredUrlUseCase,
    private val deleteStoredUrlUseCase: DeleteStoredUrlUseCase,
    private val updateStoredUrlUseCase: UpdateStoredUrlUseCase
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<UrlViewState>(UrlViewState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState = _uiState.asStateFlow()


    val shortUrlResponse: SingleLiveEvent<GenericApiResponse<ShortenUrlResponse>> = SingleLiveEvent()

    init{
        viewModelScope.launch {
            getAllStoredUrlUseCase.invoke().distinctUntilChanged().collect { result ->
                Logger.e(" it should run only when new items have different values...")
                if (result.isEmpty()) {
                    _uiState.value = UrlViewState.Empty
                } else {
                    _uiState.value = UrlViewState.Success(result)
                }
            }
        }
    }

    fun getShortUrl(potentialUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCuteUrlFromApiUseCase.execute(potentialUrl)
            shortUrlResponse.postValue(result)
        }
    }

    fun deleteStoredUrlById(urlId:Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteStoredUrlUseCase.execute(urlId)
    }

    fun updateUrlRecordById(urlId:Int) = viewModelScope.launch(Dispatchers.IO)  {
        updateStoredUrlUseCase.execute(urlId)
    }

}