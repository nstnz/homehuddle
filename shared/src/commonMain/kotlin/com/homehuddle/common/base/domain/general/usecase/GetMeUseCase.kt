package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.repository.UserRepository
import com.homehuddle.common.base.domain.general.model.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetMeUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: UserRepository
) {

    suspend operator fun invoke(): UserModel? = withContext(dispatcher) {
        val existingUser = repository.getCurrentUser()
        return@withContext existingUser
    }
}