package com.homehuddle.common.base.data.repository

import com.homehuddle.TripPointsDao
import com.homehuddle.common.base.data.dbsource.TripPointDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.TripPointMemorySource
import com.homehuddle.common.base.data.model.TripPoint
import com.homehuddle.common.base.data.networksource.TripPointNetworkSource
import com.homehuddle.common.base.domain.general.model.TripPointModel

internal class TripPointRepository(
    userLocalSource: UserLocalSource,
    networkSource: TripPointNetworkSource,
    memorySource: TripPointMemorySource,
    dbSource: TripPointDbSource,
) : BaseRepository<TripPoint, TripPointModel, TripPointsDao, TripPointNetworkSource, TripPointDbSource, TripPointMemorySource>(
    networkSource,
    memorySource,
    dbSource,
    userLocalSource
) {

    override val refreshTimestampsDiff: Long
        get() = 60 * 1000

    override suspend fun mapToDbModel(model: TripPoint?): TripPointsDao? = model?.let {
        TripPointsDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            tripPostId = it.tripPostId,
            lat = it.lat,
            lon = it.lon,
            name = it.name.orEmpty(),
            description = it.description,
            address = it.address,
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun mapToDomainModel(model: TripPointsDao?): TripPointModel? = model?.let {
        TripPointModel(
            id = it.id,
            ownerId = it.ownerId,
            tripPostId = it.tripPostId,
            lat = it.lat ?: 0.0,
            lon = it.lon ?: 0.0,
            description = it.description.orEmpty(),
            address = it.address.orEmpty(),
            name = it.name.orEmpty(),
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun mapToDbModel(model: TripPointModel?): TripPointsDao? = model?.let {
        TripPointsDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            tripPostId = it.tripPostId.orEmpty(),
            lat = it.lat,
            lon = it.lon,
            description = it.description,
            address = it.address,
            name = it.name.orEmpty(),
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun mapToNetworkModel(model: TripPointsDao): TripPoint = model.let {
        TripPoint(
            id = it.id,
            ownerId = it.ownerId,
            tripPostId = it.tripPostId.orEmpty(),
            lat = it.lat ?: 0.0,
            lon = it.lon ?: 0.0,
            description = it.description.orEmpty(),
            createTs = it.createTs,
            editTs = it.editTs,
            name = it.name.orEmpty(),
            address = it.address.orEmpty(),
        )
    }
}