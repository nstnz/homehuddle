package com.homehuddle.common.base.domain.trips.usecase.trippost

import com.benasher44.uuid.uuid4
import com.homehuddle.common.base.data.model.TripExpense
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.repository.TripExpenseRepository
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.toJsonString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class CreateTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val json: Json,
    private val expenseRepository: TripExpenseRepository,
) {

    suspend operator fun invoke(
        tripId: String,
        tripPostModel: TripPostModel,
    ): Unit = withContext(dispatcher) {
        val tripPostId = uuid4().toString()
        repository.create(
            TripPost(
                id = tripPostId,
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
        tripPostModel.expenses.forEach {
            expenseRepository.create(
                TripExpense(
                    id = uuid4().toString(),
                    tripPostId = tripPostId,
                    ownerId = repository.getOwnerId(),
                    description = it.description,
                    date = it.date,
                    sum = it.sum,
                    timestamp = it.timestamp,
                    category = it.category.name,
                    currencyCode = it.currency?.id.orEmpty()
                )
            )
        }
    }
}