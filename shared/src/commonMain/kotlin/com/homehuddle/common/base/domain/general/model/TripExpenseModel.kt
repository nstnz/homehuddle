package com.homehuddle.common.base.domain.general.model

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
    val timestamp: Long?
) : BaseDomainModel<TripExpenseModel>
