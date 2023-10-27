package com.homehuddle.common.base.domain.trips.usecase.trip

import com.benasher44.uuid.uuid4
import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class CreateTripUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripRepository,
) {

    suspend operator fun invoke(
        name: String,
        description: String?,
        dateStart: String?,
        dateEnd: String?,
        timestampStart: Long?,
        timestampEnd: Long?,
        currencyModel: CurrencyModel
    ): Unit = withContext(dispatcher) {
        repository.create(
            Trip(
                id = uuid4().toString(),
                ownerId = repository.getOwnerId(),
                name = name,
                description = description.orEmpty(),
                dateStart = dateStart,
                dateEnd = dateEnd,
                timestampStart = timestampStart,
                timestampEnd = timestampEnd,
                currencyCode = currencyModel.id
            )
        )
    }
}