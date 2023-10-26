package com.homehuddle.common.base.domain.trips.usecase.trip

import com.homehuddle.common.base.data.repository.TripRepository

internal class GetUserTripsFlowUseCase(
    private val repository: TripRepository,
) {
    operator fun invoke() = repository.getUserItemsFlow()
}