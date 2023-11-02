package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.repository.UserRepository
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.base.domain.general.model.UserModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class AuthUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: UserRepository,
    private val refreshDataUseCase: RefreshDataUseCase,
) {

    suspend operator fun invoke(token: String): UserModel? = withContext(dispatcher) {
        val authResult =
            Firebase.auth.signInWithCredential(GoogleAuthProvider.credential(token, null))
        authResult.user?.let {
            val existingUser = repository.get(it.uid)
            if (existingUser == null) {
                val newUser = UserModel(
                    id = it.uid,
                    ownerId = it.uid,
                    name = it.displayName.orEmpty(),
                    currency = CurrencyModel.createEmpty(),
                    visitedCountries = emptyList(),
                    allCountries = emptyList(),
                    allCurrencies = emptyList(),
                    isMe = true
                )
                repository.create(newUser)
                repository.saveCurrentUser(newUser)
                GlobalScope.launch {
                    refreshDataUseCase()
                }
                return@withContext null
            } else {
                repository.saveCurrentUser(existingUser)
                GlobalScope.launch {
                    refreshDataUseCase()
                }
                return@withContext existingUser
            }
        }
    }
}