package com.homehuddle.common.base.domain.trips.usecase

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class SaveTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val tripRepository: TripRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        id: String?,
        name: String,
        description: String?,
        dateStart: String?,
        dateEnd: String?,
        timestampStart: Long?,
        timestampEnd: Long?,
    ): Unit = withContext(dispatcher) {
        userRepository.getMe()?.let {
            tripRepository
                .createOrUpdate(
                    Trip(
                        id = id,
                        userId = it.id,
                        name = name,
                        description = description.orEmpty(),
                        dateStart = dateStart,
                        dateEnd = dateEnd,
                        timestampStart = timestampStart,
                        timestampEnd = timestampEnd,
                    )
                )
        }
    }
}