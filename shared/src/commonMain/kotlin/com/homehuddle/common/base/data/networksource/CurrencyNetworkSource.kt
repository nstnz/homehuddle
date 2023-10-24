package com.homehuddle.common.base.data.networksource

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

internal class CurrencyNetworkSource(
    private val httpClient: HttpClient,
    private val json: Json,
) {

    suspend fun convertSum(
        sum: Double,
        fromCurrency: String,
        toCurrency: String,
    ): Double {
        //todo conversion
        return sum
    }
}