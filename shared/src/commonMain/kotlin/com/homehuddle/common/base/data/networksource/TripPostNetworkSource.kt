package com.homehuddle.common.base.data.networksource

import com.homehuddle.common.base.data.mapper.mapToTripPost
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.domain.utils.safeGet
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where

internal class TripPostNetworkSource() {

    private val name = "tripposts"

    private val storage by lazy {
        Firebase.firestore
    }

    suspend fun createTripPost(tripPost: TripPost) {
        storage.collection(name)
            .document(tripPost.id)
            .set(tripPost)
    }

    suspend fun getTripPost(id: String): TripPost? {
        return storage.collection(name)
            .document(id)
            .safeGet()
            ?.mapToTripPost()
    }

    suspend fun getTripPosts(tripId: String?): List<TripPost> {
        return storage.collection(name)
            .where("tripId", tripId)
            .safeGet()
            ?.documents?.mapNotNull { it.mapToTripPost() }
            .orEmpty()
    }

    suspend fun deleteTripPost(id: String) {
        storage.collection(name)
            .document(id)
            .delete()
    }
}