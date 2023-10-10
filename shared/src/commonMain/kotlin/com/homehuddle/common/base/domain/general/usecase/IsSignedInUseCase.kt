package com.homehuddle.common.base.domain.general.usecase

import kotlinx.coroutines.CoroutineDispatcher

internal class IsSignedInUseCase(
    private val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(): Boolean {
        return false
    }
}