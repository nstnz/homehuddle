package com.homehuddle.common.base.domain.trips.usecase.tripexpense

import com.homehuddle.common.base.data.repository.TripExpenseRepository
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetTripExpenseUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripExpenseRepository,
) {

    suspend operator fun invoke(id: String): TripExpenseModel? = withContext(dispatcher) {
        return@withContext repository.get(id)
    }
}