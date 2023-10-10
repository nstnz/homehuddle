package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher

internal class IsSignedInUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: UserRepository
) {

    operator fun invoke(): Boolean {
        return repository.isLoggedIn()
    }
}