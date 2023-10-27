package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import FirebaseRemoteConfigImpl
import com.homehuddle.common.base.data.model.Country
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

internal class CountryNetworkSource(
    storage: FirebaseFirestoreImpl,
    private val firebaseRemoteConfigImpl: FirebaseRemoteConfigImpl,
    private val json: Json,
) : BaseNetworkSource<Country>(storage) {

    override val name = "countries"

    override val parentIdName: String?
        get() = null

    override suspend fun getByOwner(id: String?): List<Country> {
        val result = mutableListOf<Country>()
        val str = firebaseRemoteConfigImpl.get("Countries")
        val values = json.decodeFromString<Array<JsonObject>>(str)
        values.forEach {
            val code = (it.getValue("code") as JsonPrimitive).content
            result.add(
                Country(
                    id = code,
                    ownerId = null,
                    name = (it.getValue("name") as JsonPrimitive).content,
                    emoji = (it.getValue("emoji") as JsonPrimitive).content,
                )
            )
        }
        return result
    }

    override fun map(map: MutableMap<String, Any>?): Country? = null
}