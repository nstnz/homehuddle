package com.homehuddle.common.base.domain.trips.usecase.trippoint

import com.homehuddle.common.base.data.repository.TripPointRepository
import com.homehuddle.common.base.domain.general.model.TripPointModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetTripPointUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPointRepository,
) {

    suspend operator fun invoke(id: String): TripPointModel? = withContext(dispatcher) {
        return@withContext repository.get(id)
    }
}