package com.homehuddle.common.base.domain.trips.usecase.trip

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.repository.TripRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class UpdateTripUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripRepository,
) {

    suspend operator fun invoke(
        id: String?,
        name: String,
        description: String?,
        dateStart: String?,
        dateEnd: String?,
        timestampStart: Long?,
        timestampEnd: Long?,
    ): Unit = withContext(dispatcher) {
        repository.update(
            Trip(
                id = id,
                ownerId = repository.getOwnerId(),
                name = name,
                description = description.orEmpty(),
                dateStart = dateStart,
                dateEnd = dateEnd,
                timestampStart = timestampStart,
                timestampEnd = timestampEnd,
            )
        )
    }
}