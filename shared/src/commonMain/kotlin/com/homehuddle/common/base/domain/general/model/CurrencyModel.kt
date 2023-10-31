package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyModel(
    override val id: String?,
    override val ownerId: String?,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
    val name: String,
    val code: String,
    val rate: Double
) : BaseDomainModel<CurrencyModel>
