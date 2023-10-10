package com.homehuddle.common.base.domain.general.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class AnonymousAuthUseCase(
    private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(callback: (Boolean) -> Unit) {
        withContext(dispatcher) {
            //firebase.anonymousAuth(callback)
        }
    }
}