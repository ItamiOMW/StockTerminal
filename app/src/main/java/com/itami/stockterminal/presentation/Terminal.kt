package com.itami.stockterminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.itami.stockterminal.data.Bar


@Composable
fun Terminal(
    bars: List<Bar>
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {

    }
}