package com.homehuddle.common.base.domain.general.usecase

import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.data.repository.UserRepository
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.base.domain.general.model.toJsonString
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class SaveMeUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val json: Json,
    private val repository: UserRepository
) {

    suspend operator fun invoke(
        userModel: UserModel?
    ): Unit = withContext(dispatcher) {
        userModel?.let {
            repository.saveCurrentUser(userModel)
            repository.update(
                User(
                    id = userModel.id,
                    ownerId = userModel.ownerId,
                    createTs = userModel.createTs,
                    editTs = getTimeMillis(),
                    name = userModel.name,
                    currencyCode = userModel.currency?.id.orEmpty(),
                    visitedCountries = userModel.visitedCountries.toJsonString(json).orEmpty()
                )
            )
        }
    }
}