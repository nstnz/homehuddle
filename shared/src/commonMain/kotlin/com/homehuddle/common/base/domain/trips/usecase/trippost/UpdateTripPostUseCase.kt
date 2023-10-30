package com.homehuddle.common.base.domain.trips.usecase.trippost

import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.toJsonString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class UpdateTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val json: Json
) {

    suspend operator fun invoke(
        tripId: String,
        tripPostModel: TripPostModel,
    ): Unit = withContext(dispatcher) {
        repository.create(
            TripPost(
                id = tripPostModel.id,
                tripId = tripId,
                ownerId = repository.getOwnerId(),
                name = tripPostModel.name,
                description = tripPostModel.description,
                dateStart = tripPostModel.dateStart,
                dateEnd = tripPostModel.dateEnd,
                timestampStart = tripPostModel.timestampStart,
                timestampEnd = tripPostModel.timestampEnd,
                countries = tripPostModel.countries.toJsonString(json).orEmpty()
            )
        )
    }
}