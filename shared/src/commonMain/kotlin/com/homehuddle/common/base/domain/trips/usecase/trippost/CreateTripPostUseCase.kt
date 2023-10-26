package com.homehuddle.common.base.domain.trips.usecase.trippost

import com.benasher44.uuid.uuid4
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.repository.TripPostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class CreateTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
) {

    suspend operator fun invoke(
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
                id = uuid4().toString(),
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