package com.homehuddle.common.base.data.networksource

import com.homehuddle.common.base.data.mapper.mapToTrip
import com.homehuddle.common.base.data.model.Trip
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where

internal class TripNetworkSource() {

    private val name = "trips"

    private val storage by lazy {
        Firebase.firestore
    }

    suspend fun createTrip(trip: Trip) {
        storage.collection(name)
            .document(trip.id)
            .set(trip)
    }

    suspend fun getTrip(id: String): Trip? {
        return storage.collection(name)
            .document(id)
            .get()
            .mapToTrip()
    }

    suspend fun getTrips(userId: String?): List<Trip> {
        return storage.collection(name)
            .where("", userId)
            .get()
            .documents.mapNotNull { it.mapToTrip() }
    }

    suspend fun deleteTrip(id: String) {
        storage.collection(name)
            .document(id)
            .delete()
    }
}