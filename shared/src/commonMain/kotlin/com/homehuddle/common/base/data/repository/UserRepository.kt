package com.homehuddle.common.base.data.repository

import com.homehuddle.UsersDao
import com.homehuddle.common.base.data.dbsource.UserDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.UserMemorySource
import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.data.networksource.UserNetworkSource
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.base.domain.general.model.fromJsonString
import com.homehuddle.common.base.domain.general.model.toJsonString
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

internal class UserRepository(
    private val userLocalSource: UserLocalSource,
    networkSource: UserNetworkSource,
    memorySource: UserMemorySource,
    dbSource: UserDbSource,
    private val currencyRepository: CurrencyRepository,
    private val countryRepository: CountryRepository,
    private val json: Json
) : BaseRepository<User, UserModel, UsersDao, UserNetworkSource, UserDbSource, UserMemorySource>(
    networkSource,
    memorySource,
    dbSource,
    userLocalSource
) {

    override val refreshTimestampsDiff: Long
        get() = Long.MAX_VALUE

    suspend fun getCurrentUser() = get(getOwnerId())

    fun saveCurrentUser(user: User) = userLocalSource.setUser(user)

    fun saveCurrentUser(user: UserModel) = userLocalSource.setUser(user)

    fun isLoggedIn() = userLocalSource.isLoggedIn()

    override suspend fun mapToDbModel(model: User?): UsersDao? = model?.let {
        UsersDao(
            id = it.id.orEmpty(),
            name = it.name,
            currencyCode = it.currencyCode,
            ownerId = it.ownerId.orEmpty(),
            visitedCountries = it.visitedCountries,
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun mapToDomainModel(model: UsersDao?): UserModel? {
        return model?.let {
            val countries = countryRepository.getUserItemsFlow().firstOrNull().orEmpty()
            UserModel(
                isMe = it.ownerId == getOwnerId(),
                id = it.id,
                name = it.name.orEmpty(),
                currency = currencyRepository.get(it.currencyCode),
                ownerId = it.ownerId,
                allCurrencies = currencyRepository.getUserItemsFlow().firstOrNull().orEmpty(),
                allCountries = countries,
                visitedCountries = it.visitedCountries.fromJsonString(json, countries),
                createTs = it.createTs,
                editTs = it.editTs
            )
        }
    }

    override suspend fun mapToDbModel(model: UserModel?): UsersDao? = model?.let {
        UsersDao(
            id = it.id.orEmpty(),
            name = it.name,
            currencyCode = it.currency?.id,
            ownerId = it.ownerId,
            visitedCountries = it.visitedCountries.toJsonString(json),
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun clear() {
        userLocalSource.clear()
        super.clear()
    }
}