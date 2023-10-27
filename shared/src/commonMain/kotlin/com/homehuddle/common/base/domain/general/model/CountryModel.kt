package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
data class CountryModel(
    override val id: String?,
    override val ownerId: String?,
    val name: String,
    val emoji: String,
) : BaseDomainModel<CountryModel>

fun List<CountryModel>?.toJsonString(json: Json) =
    this?.let { json.encodeToJsonElement(this.map { it.id.orEmpty() }).toString() }

fun String?.fromJsonString(json: Json, localCountries: List<CountryModel>) =
    this.orEmpty().takeIf { it.isNotEmpty() }?.let {
        json.decodeFromString<Array<String>>(it)
            .toList().mapNotNull { c ->
                localCountries.firstOrNull { it.id == c }
            }
    }.orEmpty()

