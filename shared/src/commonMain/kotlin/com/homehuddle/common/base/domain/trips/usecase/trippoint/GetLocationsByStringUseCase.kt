package com.homehuddle.common.base.domain.trips.usecase.trippoint

import com.homehuddle.common.base.data.repository.LocationsRepository
import com.homehuddle.common.base.domain.general.model.LocationModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetLocationsByStringUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: LocationsRepository,
) {

    suspend operator fun invoke(
        searchString: String,
        lat: Double,
        lon: Double
    ): List<LocationModel> = withContext(dispatcher) {
        return@withContext repository.search(searchString, lat, lon)
    }
}