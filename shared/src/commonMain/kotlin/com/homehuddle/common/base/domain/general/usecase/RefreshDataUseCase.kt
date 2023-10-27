package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.repository.CurrencyRepository
import com.homehuddle.common.base.data.repository.TripExpenseRepository
import com.homehuddle.common.base.data.repository.TripPointRepository
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class RefreshDataUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
    private val tripExpenseRepository: TripExpenseRepository,
    private val tripPointRepository: TripPointRepository,
    private val tripPostRepository: TripPostRepository,
    private val tripRepository: TripRepository,
    private val currencyRepository: CurrencyRepository
) {

    suspend operator fun invoke() = withContext(dispatcher) {
        currencyRepository.refresh()
        userRepository.refresh()
        tripExpenseRepository.refresh()
        tripPointRepository.refresh()
        tripPostRepository.refresh()
        tripRepository.refresh()
    }
}