package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.data.repository.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class AnonymousAuthUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: UserRepository,
    private val refreshDataUseCase: RefreshDataUseCase,
) {

    suspend operator fun invoke(): Boolean = withContext(dispatcher) {
        val user = Firebase.auth.signInAnonymously().user
        val result = user != null

        Firebase.auth.currentUser?.let {
            val existingUser = repository.get(it.uid)
            if (existingUser == null) {
                val newUser = User(
                    id = it.uid,
                    ownerId = it.uid,
                    name = "Pupa",
                    currencyCode = "USD",
                )
                repository.create(newUser)
                repository.saveCurrentUser(newUser)
            } else {
                repository.saveCurrentUser(existingUser)
            }
        }
        GlobalScope.launch {
            refreshDataUseCase()
        }
        return@withContext result
    }
}