package com.homehuddle.common.base.domain.general.model

import com.homehuddle.common.base.domain.utils.formatSum
import kotlinx.serialization.Serializable

@Serializable
data class TripExpenseModel(
    override val id: String?,
    override val ownerId: String?,
    val sum: Double,
    val currencyCode: String,
    val description: String,
    val tripPostId: String?,
    val date: String?,
    val timestamp: Long?,
    val category: TripExpenseCategory
) : BaseDomainModel<TripExpenseModel> {

    val formattedSum: String?
        get() = sum.takeIf { it > 0 }?.formatSum()

    companion object {

        fun createEmpty() : TripExpenseModel = TripExpenseModel(
            id = null,
            ownerId = null,
            sum = 0.0,
            currencyCode = "",
            description = "",
            tripPostId = null,
            date = null,
            timestamp = null,
            category = TripExpenseCategory.Other
        )
    }
}
