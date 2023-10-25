package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.mapper.mapToTripModel
import com.homehuddle.common.base.data.memorysource.BaseMemorySource
import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.networksource.TripNetworkSource
import com.homehuddle.common.base.domain.general.model.TripModel

internal class TripRepository(
    private val userRepository: UserRepository,
    tripNetworkSource: TripNetworkSource,
) : BaseRepository<Trip, TripModel, TripNetworkSource, BaseMemorySource<Trip>>(
    tripNetworkSource,
    null
)  {

    override suspend fun map(model: Trip?): TripModel? {
        return userRepository.get(model?.userId)?.let {
            model.mapToTripModel(it)
        }
    }
}