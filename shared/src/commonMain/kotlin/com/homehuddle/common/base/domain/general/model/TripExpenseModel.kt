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

    val formattedSum: String?
        get() = sum.takeIf { it > 0 }?.formatSum()

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
