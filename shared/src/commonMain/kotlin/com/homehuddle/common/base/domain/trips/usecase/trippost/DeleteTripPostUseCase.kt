package com.homehuddle.common.base.domain.trips.usecase.trippost

import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.domain.general.model.TripModel
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class DeleteTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val tripRepository: TripRepository,
) {

    suspend operator fun invoke(
        trip: TripModel?,
        id: String,
    ): Unit = withContext(dispatcher) {
        repository.delete(id)

        trip?.let {
            tripRepository.update(
                it.copy(
                    editTs = getTimeMillis()
                )
            )
        }
    }
}