package com.homehuddle.common.base.domain.general.scenario

import com.homehuddle.common.base.domain.general.usecase.GetUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class LoginScenario(
    private val dispatcher: CoroutineDispatcher,
    private val getUserUseCase: GetUserUseCase,
) {

    suspend operator fun invoke() = withContext(dispatcher) {
        val user = getUserUseCase()
    }
}