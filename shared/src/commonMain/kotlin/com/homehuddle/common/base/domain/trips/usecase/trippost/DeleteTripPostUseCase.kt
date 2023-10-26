package com.homehuddle.common.base.domain.trips.usecase.trippost

import com.homehuddle.common.base.data.repository.TripPostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class DeleteTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
) {

    suspend operator fun invoke(id: String): Unit = withContext(dispatcher) {
        return@withContext repository.delete(id)
    }
}