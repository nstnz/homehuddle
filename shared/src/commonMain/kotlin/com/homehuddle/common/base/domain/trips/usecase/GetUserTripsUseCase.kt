package com.homehuddle.common.base.domain.trips.usecase

import com.homehuddle.common.base.data.mapper.mapToTripModel
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetUserTripsUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val tripRepository: TripRepository,
) {

    suspend operator fun invoke(userModel: UserModel): List<TripModel> = withContext(dispatcher) {
        return@withContext tripRepository.getTrips(userModel.id)
            .mapNotNull { it.mapToTripModel(userModel) }
    }
}