package com.homehuddle.common.base.domain.trips.usecase

import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.domain.general.model.TripPostModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
) {

    suspend operator fun invoke(id: String): TripPostModel? = withContext(dispatcher) {
        return@withContext repository.get(id)
    }
}