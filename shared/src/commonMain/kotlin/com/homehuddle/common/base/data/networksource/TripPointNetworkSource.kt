package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToTripPoint
import com.homehuddle.common.base.data.model.TripPoint

internal class TripPointNetworkSource(
    private val storage: FirebaseFirestoreImpl
) {

    private val name = "trippoints"

    suspend fun createTripPoint(tripPoint: TripPoint) {
        storage.createOrUpdate(name, tripPoint.id, tripPoint)
    }

    suspend fun getTripPoints(tripPostId: String): List<TripPoint> {
        return storage.get(name, "tripPostId" to tripPostId)
            .mapNotNull { it.mapToTripPoint() }
    }

    suspend fun deleteTripPoints(tripPostId: String) {
        storage.delete(name, "tripPostId" to tripPostId)
    }
}