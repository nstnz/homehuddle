package com.homehuddle.common.base.domain.trips.scenario

import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.usecase.GetUserUseCase
import com.homehuddle.common.base.domain.trips.usecase.GetUserTripPostsUseCase
import com.homehuddle.common.base.domain.trips.usecase.GetUserTripsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetUserTripsScenario(
    private val dispatcher: CoroutineDispatcher,
    private val getUserUseCase: GetUserUseCase,
    private val getUserTripsUseCase: GetUserTripsUseCase,
    private val getUserTripPostsUseCase: GetUserTripPostsUseCase,
) {

    suspend operator fun invoke(userId: String): List<TripModel> = withContext(dispatcher) {
        val user = getUserUseCase(userId)
        return@withContext user?.let {
            getUserTripsUseCase(user).map {
                it.copy(posts = getUserTripPostsUseCase(it))
            }
        } ?: emptyList<TripModel>()
    }
}