package com.homehuddle.common.base.data.repository

import com.homehuddle.TripExpensesDao
import com.homehuddle.common.base.data.dbsource.TripExpenseDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.TripExpenseMemorySource
import com.homehuddle.common.base.data.model.TripExpense
import com.homehuddle.common.base.data.networksource.TripExpenseNetworkSource
import com.homehuddle.common.base.domain.general.model.TripExpenseCategory
import com.homehuddle.common.base.domain.general.model.TripExpenseModel

internal class TripExpenseRepository(
    userLocalSource: UserLocalSource,
    networkSource: TripExpenseNetworkSource,
    memorySource: TripExpenseMemorySource,
    dbSource: TripExpenseDbSource,
    private val currencyRepository: CurrencyRepository
) : BaseRepository<TripExpense, TripExpenseModel, TripExpensesDao, TripExpenseNetworkSource, TripExpenseDbSource, TripExpenseMemorySource>(
    networkSource,
    memorySource,
    dbSource,
    userLocalSource
) {

    override val refreshTimestampsDiff: Long
        get() = 60 * 1000

    override suspend fun mapToDbModel(model: TripExpense?): TripExpensesDao? = model?.let {
        TripExpensesDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            tripPostId = it.tripPostId,
            sum = it.sum,
            currencyCode = it.currencyCode,
            description = it.description,
            date = it.date.orEmpty(),
            timestamp = it.timestamp,
            category = it.category,
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun mapToDomainModel(model: TripExpensesDao?): TripExpenseModel? = model?.let {
        TripExpenseModel(
            id = it.id,
            ownerId = it.ownerId,
            tripPostId = it.tripPostId.orEmpty(),
            sum = it.sum ?: 0.0,
            currency = currencyRepository.get(it.currencyCode),
            description = it.description.orEmpty(),
            date = it.date.orEmpty(),
            timestamp = it.timestamp,
            category = it.category?.let { TripExpenseCategory.valueOf(it) }
                ?: TripExpenseCategory.Other,
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun mapToDbModel(model: TripExpenseModel?): TripExpensesDao? = model?.let {
        TripExpensesDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            tripPostId = it.tripPostId.orEmpty(),
            sum = it.sum,
            currencyCode = it.currency?.id,
            description = it.description,
            date = it.date.orEmpty(),
            timestamp = it.timestamp,
            category = it.category.name,
            createTs = it.createTs,
            editTs = it.editTs
        )
    }

    override suspend fun mapToNetworkModel(model: TripExpensesDao): TripExpense = model.let {
        TripExpense(
            id = it.id,
            ownerId = it.ownerId,
            tripPostId = it.tripPostId.orEmpty(),
            sum = it.sum ?: 0.0,
            currencyCode = it.currencyCode.orEmpty(),
            description = it.description.orEmpty(),
            date = it.date.orEmpty(),
            timestamp = it.timestamp,
            category = it.category.orEmpty(),
            createTs = it.createTs,
            editTs = it.editTs
        )
    }
}