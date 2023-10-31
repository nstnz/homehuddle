package com.homehuddle.common.base.domain.trips.usecase.tripexpense

import com.benasher44.uuid.uuid4
import com.homehuddle.common.base.data.model.TripExpense
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.repository.TripExpenseRepository
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class CreateOnlyTripExpenseUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val expenseRepository: TripExpenseRepository,
) {

    suspend operator fun invoke(
        expense: TripExpenseModel,
        tripId: String,
    ): Unit = withContext(dispatcher) {
        val tripPostId = uuid4().toString()
        repository.create(
            TripPost(
                id = tripPostId,
                tripId = tripId,
                ownerId = repository.getOwnerId(),
                createTs = getTimeMillis(),
                name = "",
                description = "",
                dateStart = expense.date,
                dateEnd = null,
                timestampStart = expense.timestamp,
                timestampEnd = null,
                countries = "",
                photos = ""
            )
        )
        expenseRepository.create(
            TripExpense(
                id = uuid4().toString(),
                tripPostId = tripPostId,
                ownerId = repository.getOwnerId(),
                createTs = getTimeMillis(),
                description = expense.description,
                date = expense.date,
                sum = expense.sum,
                timestamp = expense.timestamp,
                category = expense.category.name,
                currencyCode = expense.currency?.id.orEmpty()
            )
        )
    }
}