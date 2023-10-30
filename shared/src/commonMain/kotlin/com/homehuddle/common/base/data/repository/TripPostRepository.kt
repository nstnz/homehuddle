package com.homehuddle.common.base.data.repository

import com.homehuddle.TripPostsDao
import com.homehuddle.common.base.data.dbsource.TripPostDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.TripPostMemorySource
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.networksource.TripPostNetworkSource
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.fromJsonString
import com.homehuddle.common.base.domain.general.model.toJsonString
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

internal class TripPostRepository(
    userLocalSource: UserLocalSource,
    private val tripExpenseRepository: TripExpenseRepository,
    private val tripPointRepository: TripPointRepository,
    private val userRepository: UserRepository,
    networkSource: TripPostNetworkSource,
    memorySource: TripPostMemorySource,
    dbSource: TripPostDbSource,
    private val json: Json,
    private val countryRepository: CountryRepository,
) : BaseRepository<TripPost, TripPostModel, TripPostsDao, TripPostNetworkSource, TripPostDbSource, TripPostMemorySource>(
    networkSource,
    memorySource,
    dbSource,
    userLocalSource
) {

    override val refreshTimestampsDiff: Long
        get() = 60 * 1000

    override suspend fun mapToDbModel(model: TripPost?): TripPostsDao? = model?.let {
        TripPostsDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            tripId = it.tripId,
            name = it.name,
            description = it.description,
            dateStart = it.dateStart,
            dateEnd = it.dateEnd,
            timestampStart = it.timestampStart,
            timestampEnd = it.timestampEnd,
            countries = it.countries
        )
    }

    override suspend fun mapToDbModel(model: TripPostModel?): TripPostsDao? = model?.let {
        TripPostsDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            tripId = it.tripId,
            name = it.name,
            description = it.description,
            dateStart = it.dateStart,
            dateEnd = it.dateEnd,
            timestampStart = it.timestampStart,
            timestampEnd = it.timestampEnd,
            countries = it.countries.toJsonString(json)
        )
    }

    override suspend fun mapToDomainModel(model: TripPostsDao?): TripPostModel? {
        val localCountries = countryRepository.getUserItemsFlow().firstOrNull().orEmpty()
        return model?.let {
            TripPostModel(
                id = it.id,
                ownerId = it.ownerId,
                tripId = it.tripId,
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                user = userRepository.get(it.ownerId),
                dateStart = it.dateStart,
                dateEnd = it.dateEnd,
                timestampStart = it.timestampStart,
                timestampEnd = it.timestampEnd,
                expenses = tripExpenseRepository.getByParent(it.id),
                photos = emptyList(),
                points = tripPointRepository.getByParent(model.id),
                countries = it.countries.fromJsonString(json, localCountries)
            )
        }
    }

    override suspend fun delete(id: String?) {
        tripExpenseRepository.deleteByParent(id)
        tripPointRepository.deleteByParent(id)
        super.delete(id)
    }
}