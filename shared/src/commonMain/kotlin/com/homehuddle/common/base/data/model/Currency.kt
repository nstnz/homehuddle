package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    override val id: String?,
    override val ownerId: String?,
    val name: String,
    val code: String,
    val rate: Double,
) : BaseDataModel<Currency>
