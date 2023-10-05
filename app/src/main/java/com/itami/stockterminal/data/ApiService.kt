package com.itami.stockterminal.data

import retrofit2.http.GET

interface ApiService {

    @GET("aggs/ticker/AAPL/range/1/hour/2022-01-09/2023-01-09?adjusted=true&sort=desc&limit=50000&apiKey=66VVMGUJ0pGxZPcUm9R6gjda0PgitsYZ")
    suspend fun loadBars(): BarsResult

}