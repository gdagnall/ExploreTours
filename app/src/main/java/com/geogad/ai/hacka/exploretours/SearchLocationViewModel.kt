package com.geogad.ai.hacka.exploretours

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geogad.ai.hacka.exploretours.data.model.NexaYTVideoSearchOutput
import com.geogad.ai.hacka.exploretours.data.network.Resource
import com.geogad.ai.hacka.exploretours.data.network.httpClientAndroid
import com.geogad.ai.hacka.exploretours.data.repository.NexaSearchRepository
import com.geogad.ai.hacka.exploretours.data.repository.NexaSearchRepositoryImpl
import com.geogad.ai.hacka.exploretours.ui.screen.TabContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SearchLocationViewModel  : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _selectedTabIndex = MutableStateFlow<Int>(TabContent.LOCATION.ordinal)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _searchLocationText = MutableStateFlow<String?>(null)
    val searchLocationText: StateFlow<String?> = _searchLocationText

    private val repository: NexaSearchRepository = NexaSearchRepositoryImpl(httpClientAndroid)

    private val _streamingPrompt = MutableStateFlow<Resource<NexaYTVideoSearchOutput>?>(null)
    val streamingPrompt: StateFlow<Resource<NexaYTVideoSearchOutput>?> = _streamingPrompt

    init {
        viewModelScope.launch {
            _searchLocationText.value = null
        }
    }

    fun updateTab(tabPosition: TabContent) {
        _selectedTabIndex.value = tabPosition.ordinal
    }

    fun sendLocation(
        prompt: String
    ) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _streamingPrompt.value = Resource.Loading
            _streamingPrompt.value = repository.searchYTVideos(prompt)

            when (_streamingPrompt.value ) {
                is Resource.Success -> _uiState.value = UiState.Success("Location Found")
                is Resource.Failure -> _uiState.value = UiState.Error("Oops! Unable to find a match. Try again later")
                is Resource.Error -> _uiState.value = UiState.Error("Oops! Unable to find a match. Try again later")
                Resource.Loading -> null
                null -> null
            }
        }
    }

    fun updateUiState(uiState: UiState) {
        _uiState.value = uiState
    }
}