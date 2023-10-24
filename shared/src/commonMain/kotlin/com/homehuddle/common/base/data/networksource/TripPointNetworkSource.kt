package com.homehuddle.common.base.data.networksource

import com.homehuddle.common.base.data.mapper.mapToTripPoint
import com.homehuddle.common.base.data.model.TripPoint
import com.homehuddle.common.base.domain.utils.safeGet
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where

internal class TripPointNetworkSource() {

    private val name = "trippoints"

    private val storage by lazy {
        Firebase.firestore
    }

    suspend fun createTripPoint(tripPoint: TripPoint) {
        storage.collection(name)
            .document(tripPoint.id)
            .set(tripPoint)
    }

    suspend fun getTripPoints(tripPostId: String): List<TripPoint> {
        return storage.collection(name)
            .where("tripPostId", tripPostId)
            .safeGet()
            ?.documents?.mapNotNull { it.mapToTripPoint() }
            .orEmpty()
    }

    suspend fun deleteTripPoints(tripPostId: String) {
        storage.collection(name)
            .where("tripPostId", tripPostId)
            .safeGet()
            ?.documents
            ?.forEach { it.reference.delete() }
    }
}