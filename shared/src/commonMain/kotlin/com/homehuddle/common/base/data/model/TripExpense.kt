package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripExpense(
    override val id: String?,
    override val ownerId: String?,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
    val tripPostId: String,
    val sum: Double,
    val currencyCode: String,
    val description: String,
    val date: String?,
    val timestamp: Long?,
    val category: String,
) : BaseDataModel<TripExpense>