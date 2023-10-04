package com.itami.stockterminal.data

import com.google.gson.annotations.SerializedName

data class BarsResult(
    @SerializedName("results") val barList: List<Bar>,
)
