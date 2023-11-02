package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.networksource.LocationsNetworkSource
import com.homehuddle.common.base.domain.general.model.LocationModel

internal class LocationsRepository(
    private val networkSource: LocationsNetworkSource
) {

    suspend fun search(searchString: String, lat: Double, lon: Double): List<LocationModel> =
        networkSource.search(searchString, lat, lon)
}