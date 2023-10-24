package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.repository.UserRepository

internal class IsSignedInUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(): Boolean {
        return repository.isLoggedIn() && repository.getMe() != null
    }
}