package com.homehuddle.common.base.domain.trips.usecase

import com.homehuddle.common.base.data.repository.TripRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class DeleteTripUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val tripRepository: TripRepository,
) {

    suspend operator fun invoke(id: String): Unit = withContext(dispatcher) {
        return@withContext tripRepository.delete(id)
    }
}