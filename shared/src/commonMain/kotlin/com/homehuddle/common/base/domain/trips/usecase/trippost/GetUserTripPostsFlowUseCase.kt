package com.homehuddle.common.base.domain.trips.usecase.trippost

import com.homehuddle.common.base.data.repository.TripPostRepository

internal class GetUserTripPostsFlowUseCase(
    private val tripPostRepository: TripPostRepository,
) {
    operator fun invoke() = tripPostRepository.getUserItemsFlow()
}