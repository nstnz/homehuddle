package com.homehuddle.common.base.domain.trips.usecase.trippoint

import com.benasher44.uuid.uuid4
import com.homehuddle.common.base.data.repository.TripPointRepository
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPointModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.utils.formatDate
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class CreateOnlyTripPointUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val pointRepository: TripPointRepository,
    private val tripRepository: TripRepository,
) {

    suspend operator fun invoke(
        point: TripPointModel,
        trip: TripModel?,
    ): Unit = withContext(dispatcher) {
        val tripPostId = uuid4().toString()
        repository.create(
            TripPostModel.createEmpty().copy(
                id = tripPostId,
                tripId = trip?.id.orEmpty(),
                ownerId = repository.getOwnerId(),
                createTs = getTimeMillis(),
                dateStart = getTimeMillis().formatDate(),
                timestampStart = getTimeMillis(),
                name = "New expense",
            )
        )

        pointRepository.create(
            point.copy(
                id = uuid4().toString(),
                tripPostId = tripPostId,
                createTs = getTimeMillis(),
                ownerId = repository.getOwnerId(),
            )
        )

        trip?.let {
            tripRepository.update(
                it.copy(
                    editTs = getTimeMillis()
                )
            )
        }
    }
}