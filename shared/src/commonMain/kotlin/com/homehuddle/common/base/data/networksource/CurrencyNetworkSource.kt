package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import FirebaseRemoteConfigImpl
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.model.Currency
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

private const val ApiKey = "fca_live_UWNksa371NGoyovOzyf697oEK0BI1ew1FVbdVuN4"

internal class CurrencyNetworkSource(
    storage: FirebaseFirestoreImpl,
    private val firebaseRemoteConfigImpl: FirebaseRemoteConfigImpl,
    private val json: Json,
    private val httpClient: HttpClient,
    private val userLocalSource: UserLocalSource,
) : BaseNetworkSource<Currency>(storage) {

    override val name = "currencies"

    override val parentIdName: String?
        get() = null

    override suspend fun getByOwner(id: String?): List<Currency> {
        val result = mutableListOf<Currency>()
        val str = firebaseRemoteConfigImpl.get("Currencies")
        val values = json.decodeFromString<Array<JsonObject>>(str)
        val rates = getRates()
        values.forEach {
            val code = (it.getValue("code") as JsonPrimitive).content
            result.add(
                Currency(
                    id = code,
                    ownerId = null,
                    name = (it.getValue("name") as JsonPrimitive).content,
                    code = (it.getValue("symbol_native") as JsonPrimitive).content,
                    rate = rates[code] ?: 1.0
                )
            )
        }
        return result
    }

    override fun map(map: MutableMap<String, Any>?): Currency? = null

    private suspend fun getRates(): Map<String, Double> {
        if (userLocalSource.getUserCurrencyCode().isNullOrEmpty()) {
            return emptyMap()
        }

        val commonUri =
            URLBuilder("https://api.freecurrencyapi.com/v1/latest?apikey=$ApiKey&base_currency=${userLocalSource.getUserCurrencyCode()}").buildString()
        val commonResponse = httpClient.get(commonUri).retrieve()

        if (commonResponse is NetworkResponse.Success) {
            val values =
                json.decodeFromString<Map<String, Double>>(commonResponse.obj["data"].toString())
            return values
        }
        return emptyMap()
    }
}