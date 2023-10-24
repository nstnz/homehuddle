package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToTripPost
import com.homehuddle.common.base.data.model.TripPost

internal class TripPostNetworkSource(
    private val storage: FirebaseFirestoreImpl
) {

    private val name = "tripposts"

    suspend fun createTripPost(tripPost: TripPost) {
        storage.createOrUpdate(name, tripPost.id, tripPost)
    }

    suspend fun getTripPost(id: String): TripPost? {
        return storage.get(name, id)?.mapToTripPost()
    }

    suspend fun getTripPosts(tripId: String): List<TripPost> {
        return storage.get(name, "tripId" to tripId)
            .mapNotNull { it.mapToTripPost() }
    }

    suspend fun deleteTripPost(id: String) {
        storage.delete(name, id)
    }
}