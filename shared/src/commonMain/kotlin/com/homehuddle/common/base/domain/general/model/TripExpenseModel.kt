package com.homehuddle.common.base.domain.general.model

import com.homehuddle.common.base.domain.utils.formatDate
import com.homehuddle.common.base.domain.utils.formatSum
import io.ktor.util.date.getTimeMillis
import kotlinx.serialization.Serializable

@Serializable
data class TripExpenseModel(
    override val id: String?,
    override val ownerId: String?,
    val sum: Double,
    val currency: CurrencyModel?,
    val description: String,
    val tripPostId: String?,
    val date: String?,
    val timestamp: Long?,
    val category: TripExpenseCategory
) : BaseDomainModel<TripExpenseModel> {

    val formattedSum: String
        get() = sum.takeIf { it > 0 }?.formatSum() + currency?.code

    val convertedSum: Double
        get() = sum / (currency?.rate ?: 1.0)

    fun getFormattedConvertedSum(
        toCurrency: CurrencyModel,
    ) = convertedSum.takeIf { it > 0 }?.formatSum() + toCurrency.code

    companion object {

        fun createEmpty(currency: CurrencyModel?): TripExpenseModel = TripExpenseModel(
            id = null,
            ownerId = null,
            sum = 0.0,
            currency = currency,
            description = "",
            tripPostId = null,
            date = getTimeMillis().formatDate(),
            timestamp = getTimeMillis(),
            category = TripExpenseCategory.Other
        )
    }
}
