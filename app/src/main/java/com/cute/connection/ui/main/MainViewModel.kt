package com.cute.connection.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.api.UrlViewState
import com.cute.connection.ui.main.model.ShortenUrlResponse
import com.cute.connection.ui.main.usecases.DeleteStoredUrlUseCase
import com.cute.connection.ui.main.usecases.GetAllStoredUrlUseCase
import com.cute.connection.ui.main.usecases.GetCuteUrlFromApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCuteUrlFromApiUseCase: GetCuteUrlFromApiUseCase,
    private val getAllStoredUrlUseCase: GetAllStoredUrlUseCase,
    private val deleteStoredUrlUseCase: DeleteStoredUrlUseCase
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<UrlViewState>(UrlViewState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState = _uiState.asStateFlow()

    private val _shortUrlResponse = MutableLiveData<GenericApiResponse<ShortenUrlResponse>>()
    val shortUrlResponse: LiveData<GenericApiResponse<ShortenUrlResponse>> = _shortUrlResponse

    init{
        viewModelScope.launch {
            getAllStoredUrlUseCase.invoke().collect { result ->
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
            _shortUrlResponse.postValue(result)
        }
    }

    fun deleteStoredUrlById(urlId:Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteStoredUrlUseCase.execute(urlId)
    }

}