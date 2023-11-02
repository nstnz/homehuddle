package com.homehuddle.common.base.domain.trips.usecase.tripexpense

import com.benasher44.uuid.uuid4
import com.homehuddle.common.base.data.repository.TripExpenseRepository
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class CreateOnlyTripExpenseUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val expenseRepository: TripExpenseRepository,
    private val tripRepository: TripRepository,
) {

    suspend operator fun invoke(
        expense: TripExpenseModel,
        trip: TripModel?,
    ): Unit = withContext(dispatcher) {
        val tripPostId = uuid4().toString()
        repository.create(
            TripPostModel.createEmpty().copy(
                id = tripPostId,
                tripId = trip?.id.orEmpty(),
                ownerId = repository.getOwnerId(),
                createTs = getTimeMillis(),
                dateStart = expense.date,
                timestampStart = expense.timestamp,
                name = "New expense",
            )
        )

        expenseRepository.create(
            expense.copy(
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