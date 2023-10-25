package com.homehuddle.common.base.domain.trips.usecase

import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.domain.general.model.TripModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetTripUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val tripRepository: TripRepository,
) {

    suspend operator fun invoke(id: String): TripModel? = withContext(dispatcher) {
        return@withContext tripRepository.get(id)
    }
}