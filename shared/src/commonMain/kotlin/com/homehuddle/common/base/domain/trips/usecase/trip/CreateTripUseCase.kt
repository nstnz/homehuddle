package com.homehuddle.common.base.domain.trips.usecase.trip

import com.benasher44.uuid.uuid4
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.domain.general.model.CountryModel
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.base.domain.general.model.TripModel
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class CreateTripUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripRepository,
) {

    suspend operator fun invoke(
        tripModel: TripModel,
        currencyModel: CurrencyModel?,
        countries: List<CountryModel>
    ): Unit = withContext(dispatcher) {
        repository.create(
            tripModel.copy(
                id = uuid4().toString(),
                ownerId = repository.getOwnerId(),
                createTs = getTimeMillis(),
                currency = currencyModel,
                countries = countries
            )
        )
    }
}