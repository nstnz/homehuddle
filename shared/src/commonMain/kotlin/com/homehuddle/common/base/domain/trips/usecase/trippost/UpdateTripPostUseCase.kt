package com.homehuddle.common.base.domain.trips.usecase.trippost

import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.repository.TripPostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class UpdateTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
) {

    suspend operator fun invoke(
        id: String?,
        tripId: String,
        name: String,
        description: String?,
        dateStart: String?,
        dateEnd: String?,
        timestampStart: Long?,
        timestampEnd: Long?,
    ): Unit = withContext(dispatcher) {
        repository.create(
            TripPost(
                id = id,
                tripId = tripId,
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