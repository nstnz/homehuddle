package com.homehuddle.common.base.data.repository

import com.homehuddle.TripsDao
import com.homehuddle.common.base.data.dbsource.TripDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.TripMemorySource
import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.networksource.TripNetworkSource
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.fromJsonString
import com.homehuddle.common.base.domain.general.model.toJsonString
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

internal class TripRepository(
    userLocalSource: UserLocalSource,
    networkSource: TripNetworkSource,
    memorySource: TripMemorySource,
    dbSource: TripDbSource,
    private val userRepository: UserRepository,
    private val tripPostRepository: TripPostRepository,
    private val currencyRepository: CurrencyRepository,
    private val countryRepository: CountryRepository,
    private val json: Json
) : BaseRepository<Trip, TripModel, TripsDao, TripNetworkSource, TripDbSource, TripMemorySource>(
    networkSource,
    memorySource,
    dbSource,
    userLocalSource
) {

    override val refreshTimestampsDiff: Long
        get() = 60 * 1000

    override suspend fun mapToDbModel(model: Trip?): TripsDao? = model?.let {
        TripsDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            name = it.name,
            description = it.description,
            dateStart = it.dateStart,
            dateEnd = it.dateEnd,
            timestampStart = it.timestampStart,
            timestampEnd = it.timestampEnd,
            currencyCode = it.currencyCode,
            countries = it.countries,
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun mapToDbModel(model: TripModel?): TripsDao? = model?.let {
        TripsDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            name = it.name,
            description = it.description,
            dateStart = it.dateStart,
            dateEnd = it.dateEnd,
            timestampStart = it.timestampStart,
            timestampEnd = it.timestampEnd,
            currencyCode = it.currency?.id,
            countries = it.countries.toJsonString(json),
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun mapToDomainModel(model: TripsDao?): TripModel? {
        val localCountries = countryRepository.getUserItemsFlow().firstOrNull().orEmpty()
        return model?.let {
            TripModel(
                id = it.id,
                ownerId = it.ownerId,
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                currency = currencyRepository.get(it.currencyCode),
                user = userRepository.get(it.ownerId),
                posts = tripPostRepository.getByParent(it.id),
                dateStart = it.dateStart,
                dateEnd = it.dateEnd,
                timestampStart = it.timestampStart,
                timestampEnd = it.timestampEnd,
                countries = it.countries.fromJsonString(json, localCountries),
                createTs = it.createTs,
                editTs = it.editTs
            )
        }
    }

    override suspend fun delete(id: String?) {
        tripPostRepository.deleteByParent(id)
        super.delete(id)
    }
}