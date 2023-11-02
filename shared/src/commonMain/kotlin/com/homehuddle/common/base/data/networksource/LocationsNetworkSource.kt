package com.homehuddle.common.base.data.networksource

import com.homehuddle.common.base.data.model.LocationResult
import com.homehuddle.common.base.domain.general.model.LocationModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.encodeURLPath
import kotlinx.serialization.json.Json

internal class LocationsNetworkSource(
    private val json: Json,
    private val httpClient: HttpClient,
) {

    suspend fun search(searchString: String, lat: Double, lon: Double): List<LocationModel> {
        val key = "AeZwIRHXi9GwDzj6WV25Ixj0pfTd2GNW"
        val commonUri =
            URLBuilder("https://api.tomtom.com/search/2/geocode/${searchString.encodeURLPath()}.json?storeResult=false&lat=${lat}&lon=${lon}&view=Unified&key=$key").buildString()
        val commonResponse = httpClient.get(commonUri).retrieve()

        if (commonResponse is NetworkResponse.Success) {
            val values =
                json.decodeFromString<List<LocationResult>>(commonResponse.obj["results"].toString())
            return values.map {
                LocationModel(
                    name = "",
                    description = "",
                    address = it.address.freeformAddress,
                    lat = it.position.lat,
                    lon = it.position.lon,
                )
            }
        }
        return emptyList()
    }
}