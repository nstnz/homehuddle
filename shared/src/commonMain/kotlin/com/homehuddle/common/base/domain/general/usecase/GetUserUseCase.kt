package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.data.repository.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetUserUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: UserRepository
) {

    suspend operator fun invoke(): User? = withContext(dispatcher) {
        return@withContext Firebase.auth.currentUser?.let {
            var existingUser = repository.getUser(it.uid)
            if (existingUser == null) {
                existingUser = User(
                    id = it.uid,
                    name = it.displayName.orEmpty(),
                )
                repository.saveUser(existingUser)
            }
            existingUser
        }
    }
}