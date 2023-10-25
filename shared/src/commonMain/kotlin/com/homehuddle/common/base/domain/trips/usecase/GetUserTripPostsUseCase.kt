package com.homehuddle.common.base.domain.trips.usecase

import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetUserTripPostsUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val tripPostRepository: TripPostRepository,
) {

    suspend operator fun invoke(tripModel: TripModel): List<TripPostModel> =
        withContext(dispatcher) {
            return@withContext tripPostRepository.getByParent(tripModel.id)
        }
}