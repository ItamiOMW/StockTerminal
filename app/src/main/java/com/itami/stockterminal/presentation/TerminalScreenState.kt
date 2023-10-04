package com.itami.stockterminal.presentation

import com.itami.stockterminal.data.Bar

sealed class TerminalScreenState {

    data object Initial: TerminalScreenState()

    data class Content(val bars: List<Bar>): TerminalScreenState()

}
