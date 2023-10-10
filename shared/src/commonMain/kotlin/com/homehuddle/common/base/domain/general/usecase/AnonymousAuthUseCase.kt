package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.repository.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class AnonymousAuthUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: UserRepository
) {

    suspend operator fun invoke(): Boolean = withContext(dispatcher) {
        val result = Firebase.auth.signInAnonymously().user != null
        repository.setLoggedIn(result)
        return@withContext result
    }
}