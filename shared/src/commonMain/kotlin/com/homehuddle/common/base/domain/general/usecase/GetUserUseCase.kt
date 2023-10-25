package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.repository.UserRepository
import com.homehuddle.common.base.domain.general.model.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetUserUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: UserRepository
) {

    suspend operator fun invoke(userId: String?): UserModel? = withContext(dispatcher) {
        val user = repository.getUser(userId)
        return@withContext user
    }
}