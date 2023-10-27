package com.homehuddle.common.base.domain.trips.usecase.trip

import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.domain.general.model.TripModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

internal class GetLastTripUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripRepository,
) {

    suspend operator fun invoke(): TripModel? = withContext(dispatcher) {
        return@withContext repository.getUserItemsFlow().firstOrNull()?.get(0)
    }
}