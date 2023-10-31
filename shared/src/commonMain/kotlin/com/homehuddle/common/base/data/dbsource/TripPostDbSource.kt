package com.homehuddle.common.base.data.dbsource

import com.homehuddle.AppDatabaseQueries
import com.homehuddle.TripPostsDao
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.Flow

internal class TripPostDbSource(
    private val queries: AppDatabaseQueries
) : BaseDbSource<TripPostsDao>() {

    override fun getFlow(): Flow<List<TripPostsDao>> = queries.getTripPosts()
        .asFlow()
        .mapToList()

    override suspend fun create(model: TripPostsDao) {
        queries.createOrUpdateTripPost(
            id = model.id,
            ownerId = model.ownerId,
            tripId = model.tripId,
            name = model.name,
            description = model.description,
            dateStart = model.dateStart,
            dateEnd = model.dateEnd,
            timestampStart = model.timestampStart,
            timestampEnd = model.timestampEnd,
            countries = model.countries,
            photos = model.photos,
            createTs = getTimeMillis(),
            editTs = null
        )
    }

    override suspend fun delete(id: String?) {
        queries.deleteTripPost(id.orEmpty())
    }

    override suspend fun get(id: String?): TripPostsDao? =
        queries.getTripPost(id.orEmpty()).executeAsOneOrNull()

    override suspend fun getByParent(id: String?): List<TripPostsDao> {
        return queries.getTripPostsByParent(id.orEmpty()).executeAsList()
    }

    override suspend fun getByOwner(id: String?): List<TripPostsDao> {
        return queries.getTripPostsByOwner(id.orEmpty()).executeAsList()
    }

    override suspend fun deleteByParent(id: String?) {
        queries.deleteTripPostsByParent(id.orEmpty())
    }

    override suspend fun clear() {
        queries.clearTripPosts()
    }
}