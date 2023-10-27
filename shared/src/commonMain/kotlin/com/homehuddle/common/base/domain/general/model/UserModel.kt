package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    override val id: String?,
    override val ownerId: String?,
    val name: String,
    val isMe: Boolean,
    val currency: CurrencyModel?,
    val allCurrencies: List<CurrencyModel>,
    val allCountries: List<CountryModel>,
    val visitedCountries: List<CountryModel>,
) : BaseDomainModel<UserModel>
