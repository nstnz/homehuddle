package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToTrip
import com.homehuddle.common.base.data.model.Trip

internal class TripNetworkSource(
    private val storage: FirebaseFirestoreImpl
) {
    private val name = "trips"

    suspend fun createTrip(trip: Trip) {
        storage.createOrUpdate(name, trip.id, trip)
    }

    suspend fun getTrip(id: String): Trip? {
        return storage.get(name, id)?.mapToTrip()
    }

    suspend fun getTrips(userId: String): List<Trip> {
        return storage.get(name, "userId" to userId)
            .mapNotNull { it.mapToTrip() }
    }

    suspend fun deleteTrip(id: String) {
        storage.delete(name, id)
    }
}