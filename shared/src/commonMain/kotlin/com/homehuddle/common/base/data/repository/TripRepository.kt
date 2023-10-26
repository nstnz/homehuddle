package com.homehuddle.common.base.data.repository

import com.homehuddle.TripsDao
import com.homehuddle.common.base.data.dbsource.TripDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.TripMemorySource
import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.networksource.TripNetworkSource
import com.homehuddle.common.base.domain.general.model.TripModel

internal class TripRepository(
    userLocalSource: UserLocalSource,
    networkSource: TripNetworkSource,
    memorySource: TripMemorySource,
    dbSource: TripDbSource,
    private val userRepository: UserRepository,
    private val tripPostRepository: TripPostRepository,
) : BaseRepository<Trip, TripModel, TripsDao, TripNetworkSource, TripDbSource, TripMemorySource>(
    networkSource,
    memorySource,
    dbSource,
    userLocalSource
) {
    override suspend fun mapToDbModel(model: Trip?): TripsDao? = model?.let {
        TripsDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            name = it.name,
            description = it.description,
            dateStart = it.dateStart,
            dateEnd = it.dateEnd,
            timestampStart = it.timestampStart,
            timestampEnd = it.timestampEnd,
        )
    }

    override suspend fun mapToDbModel(model: TripModel?): TripsDao? = model?.let {
        TripsDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            name = it.name,
            description = it.description,
            dateStart = it.dateStart,
            dateEnd = it.dateEnd,
            timestampStart = it.timestampStart,
            timestampEnd = it.timestampEnd,
        )
    }

    override suspend fun mapToDomainModel(model: TripsDao?): TripModel? = model?.let {
        TripModel(
            id = it.id,
            ownerId = it.ownerId,
            name = it.name.orEmpty(),
            description = it.description.orEmpty(),
            user = userRepository.get(it.ownerId),
            posts = tripPostRepository.getByParent(it.id),
            dateStart = it.dateStart,
            dateEnd = it.dateEnd,
            timestampStart = it.timestampStart,
            timestampEnd = it.timestampEnd,
        )
    }
}