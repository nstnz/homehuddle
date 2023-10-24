package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.networksource.TripNetworkSource

internal class TripRepository(
    private val tripNetworkSource: TripNetworkSource,
) {

    suspend fun createTrip(trip: Trip): Trip {
        tripNetworkSource.createTrip(trip)
        return trip
    }

    suspend fun getTrip(id: String) =
        tripNetworkSource.getTrip(id)

    suspend fun getTrips(userId: String) =
        tripNetworkSource.getTrips(userId)
}