package com.itami.stockterminal.data

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import java.util.Calendar
import java.util.Date

@Immutable
data class Bar(
    @SerializedName("o") val open: Float,
    @SerializedName("c") val close: Float,
    @SerializedName("h") val high: Float,
    @SerializedName("l") val low: Float,
    @SerializedName("t") val timestamp: Long,
) {
    val calendar: Calendar
        get() {
            return Calendar.getInstance().apply {
                time = Date(timestamp)
            }
        }
}