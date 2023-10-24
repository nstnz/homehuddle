package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToTripExpense
import com.homehuddle.common.base.data.model.TripExpense

internal class TripExpenseNetworkSource(
    private val storage: FirebaseFirestoreImpl
) {
    private val name = "tripexpenses"

    suspend fun createTripExpense(tripExpense: TripExpense) {
        storage.createOrUpdate(name, tripExpense.id, tripExpense)
    }

    suspend fun getTripExpenses(tripPostId: String): List<TripExpense> {
        return storage.get(name, "tripPostId" to tripPostId)
            .mapNotNull { it.mapToTripExpense() }
    }

    suspend fun deleteTripExpenses(tripPostId: String) {
        storage.delete(name, "tripPostId" to tripPostId)
    }
}