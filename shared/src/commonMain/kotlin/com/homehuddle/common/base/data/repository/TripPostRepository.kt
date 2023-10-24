package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.networksource.TripExpenseNetworkSource
import com.homehuddle.common.base.data.networksource.TripPointNetworkSource
import com.homehuddle.common.base.data.networksource.TripPostNetworkSource

internal class TripPostRepository(
    private val tripPostNetworkSource: TripPostNetworkSource,
    private val tripExpenseNetworkSource: TripExpenseNetworkSource,
    private val tripPointNetworkSource: TripPointNetworkSource,
) {

    suspend fun createTripPost(tripPost: TripPost): TripPost {
        tripPostNetworkSource.createTripPost(tripPost)
        return tripPost
    }

    suspend fun getTripPost(id: String) =
        tripPostNetworkSource.getTripPost(id)
            ?.let {
                it.copy(
                    expenses = tripExpenseNetworkSource.getTripExpenses(it.id),
                    points = tripPointNetworkSource.getTripPoints(it.id)
                )
            }

    suspend fun getTripPosts(tripId: String): List<TripPost> {
        return tripPostNetworkSource.getTripPosts(tripId)
            .map {
                it.copy(
                    expenses = tripExpenseNetworkSource.getTripExpenses(it.id),
                    points = tripPointNetworkSource.getTripPoints(it.id)
                )
            }
    }

    suspend fun deleteTripPost(id: String) {
        tripPointNetworkSource.deleteTripPoints(id)
        tripExpenseNetworkSource.deleteTripExpenses(id)
        tripPostNetworkSource.deleteTripPost(id)
    }
}