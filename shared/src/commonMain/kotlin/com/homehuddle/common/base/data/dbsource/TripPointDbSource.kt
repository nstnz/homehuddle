package com.homehuddle.common.base.data.dbsource

import com.homehuddle.AppDatabaseQueries
import com.homehuddle.TripPointsDao
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.Flow

internal class TripPointDbSource(
    private val queries: AppDatabaseQueries
) : BaseDbSource<TripPointsDao>() {

    override fun getFlow(): Flow<List<TripPointsDao>> = queries.getTripPoints()
        .asFlow()
        .mapToList()

    override suspend fun create(model: TripPointsDao) {
        queries.createOrUpdateTripPoint(
            id = model.id,
            ownerId = model.ownerId,
            tripPostId = model.tripPostId,
            description = model.description,
            lat = model.lat,
            lon = model.lon,
            createTs = getTimeMillis(),
            editTs = null
        )
    }

    override suspend fun delete(id: String?) {
        queries.deleteTripPoint(id.orEmpty())
    }

    override suspend fun get(id: String?): TripPointsDao? =
        queries.getTripPoint(id.orEmpty()).executeAsOneOrNull()

    override suspend fun getByParent(id: String?): List<TripPointsDao> {
        return queries.getTripPointsByParent(id.orEmpty()).executeAsList()
    }

    override suspend fun getByOwner(id: String?): List<TripPointsDao> {
        return queries.getTripPointsByOwner(id.orEmpty()).executeAsList()
    }

    override suspend fun deleteByParent(id: String?) {
        queries.deleteTripPointsByParent(id.orEmpty())
    }

    override suspend fun clear() {
        queries.clearTripPoints()
    }
}