package com.homehuddle.common.base.data.dbsource

import com.homehuddle.AppDatabaseQueries
import com.homehuddle.TripsDao
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal class TripDbSource(
    private val queries: AppDatabaseQueries
) : BaseDbSource<TripsDao>() {

    override fun getFlow(): Flow<List<TripsDao>> = queries.getTrips()
        .asFlow()
        .mapToList()

    override suspend fun create(model: TripsDao) {
        queries.createOrUpdateTrip(
            id = model.id,
            ownerId = model.ownerId,
            name = model.name,
            description = model.description,
            dateStart = model.dateStart,
            dateEnd = model.dateEnd,
            timestampStart = model.timestampStart,
            timestampEnd = model.timestampEnd,
            currencyCode = model.currencyCode,
            countries = model.countries
        )
    }

    override suspend fun delete(id: String?) {
        queries.deleteTrip(id.orEmpty())
    }

    override suspend fun get(id: String?): TripsDao? =
        queries.getTrip(id.orEmpty()).executeAsOneOrNull()

    override suspend fun getByParent(id: String?): List<TripsDao> {
        return queries.getTripsByParent(id.orEmpty()).executeAsList()
    }

    override suspend fun getByOwner(id: String?): List<TripsDao> {
        return queries.getTripsByOwner(id.orEmpty()).executeAsList()
    }

    override suspend fun deleteByParent(id: String?) {
        queries.deleteTripsByParent(id.orEmpty())
    }

    override suspend fun clear() {
        queries.clearTrips()
    }
}