package com.itami.stockterminal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.stockterminal.data.ApiFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TerminalViewModel : ViewModel() {

    private val apiService = ApiFactory.apiService

    private val _state = MutableStateFlow<TerminalScreenState>(TerminalScreenState.Initial)
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<TerminalScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _uiEvent.send(TerminalScreenUiEvent.OnShowSnackbar(throwable.message.toString()))
        }
    }

    init {
        loadBars()
    }

    private fun loadBars() {
        viewModelScope.launch(exceptionHandler) {
            val bars = apiService.loadBars().barList
            _state.value = TerminalScreenState.Content(bars)
        }
    }

}