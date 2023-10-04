package com.itami.stockterminal.presentation

sealed class TerminalScreenUiEvent {

    data class OnShowSnackbar(val message: String): TerminalScreenUiEvent()

}
