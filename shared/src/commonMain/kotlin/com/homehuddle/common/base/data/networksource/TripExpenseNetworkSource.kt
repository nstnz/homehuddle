package com.homehuddle.common.base.data.networksource

import com.homehuddle.common.base.data.mapper.mapToTripExpense
import com.homehuddle.common.base.data.model.TripExpense
import com.homehuddle.common.base.domain.utils.safeGet
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where

internal class TripExpenseNetworkSource() {

    private val name = "tripexpenses"

    private val storage by lazy {
        Firebase.firestore
    }

    suspend fun createTripExpense(tripExpense: TripExpense) {
        storage.collection(name)
            .document(tripExpense.id)
            .set(tripExpense)
    }

    suspend fun getTripExpenses(tripPostId: String): List<TripExpense> {
        return storage.collection(name)
            .where("tripPostId", tripPostId)
            .safeGet()
            ?.documents?.mapNotNull { it.mapToTripExpense() }
            .orEmpty()
    }

    suspend fun deleteTripExpenses(tripPostId: String) {
        storage.collection(name)
            .where("tripPostId", tripPostId)
            .safeGet()
            ?.documents
            ?.forEach { it.reference.delete() }
    }
}