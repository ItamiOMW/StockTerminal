package com.itami.stockterminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import com.itami.stockterminal.data.Bar
import kotlin.math.roundToInt


private const val MIN_VISIBLE_BARS_COUNT = 20

@Composable
fun Terminal(
    bars: List<Bar>,
) {
    var terminalState by rememberTerminalState(bars = bars)

    val transformableState = rememberTransformableState { zoomChange, panChange, rotationChange ->
        val visibleBarsCount = (terminalState.visibleBarsCount / zoomChange)
            .roundToInt()
            .coerceIn(minimumValue = MIN_VISIBLE_BARS_COUNT, maximumValue = bars.size)

        val scrolledBy = (terminalState.scrolledBy + panChange.x)
            .coerceAtLeast(0f)
            .coerceAtMost(bars.size * terminalState.barWidth - terminalState.terminalWidth)

        terminalState =
            terminalState.copy(visibleBarsCount = visibleBarsCount, scrolledBy = scrolledBy)
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .transformable(transformableState)
            .onSizeChanged { size ->
                terminalState = terminalState.copy(terminalWidth = size.width.toFloat())
            },
    ) {
        val highest = terminalState.visibleBars.maxOf { it.high }
        val lowest = terminalState.visibleBars.minOf { it.low }
        val pxPerPoint = size.height / (highest - lowest)
        translate(left = terminalState.scrolledBy) {
            bars.forEachIndexed { index, bar ->
                val offsetX = size.width - index * terminalState.barWidth
                drawLine(
                    color = Color.White,
                    start = Offset(
                        x = offsetX,
                        y = size.height - ((bar.low - lowest) * pxPerPoint)
                    ),
                    end = Offset(x = offsetX, y = size.height - ((bar.high - lowest) * pxPerPoint)),
                    strokeWidth = 1f
                )
                drawLine(
                    color = if (bar.open < bar.close) Color.Green else Color.Red,
                    start = Offset(
                        x = offsetX,
                        y = size.height - ((bar.open - lowest) * pxPerPoint)
                    ),
                    end = Offset(
                        x = offsetX,
                        y = size.height - ((bar.close - lowest) * pxPerPoint)
                    ),
                    strokeWidth = terminalState.barWidth / 2
                )
            }
        }
    }
}