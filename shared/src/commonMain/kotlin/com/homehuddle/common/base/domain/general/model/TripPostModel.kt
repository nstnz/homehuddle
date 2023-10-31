package com.homehuddle.common.base.domain.general.model

import com.homehuddle.common.base.domain.utils.formatDate
import com.homehuddle.common.base.domain.utils.formatSum
import io.ktor.util.date.getTimeMillis
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
data class TripPostModel(
    override val id: String?,
    override val ownerId: String?,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
    val tripId: String?,
    val user: UserModel?,
    val name: String,
    val description: String,
    val photos: List<String>,
    val expenses: List<TripExpenseModel>,
    val points: List<TripPointModel>? = null,
    val dateStart: String? = null,
    val timestampStart: Long? = null,
    val dateEnd: String? = null,
    val timestampEnd: Long? = null,
    val countries: List<CountryModel>
) : BaseDomainModel<TripPostModel> {

    val isMine: Boolean
        get() = user?.isMe ?: false

    val hasPoints: Boolean
        get() = !points.isNullOrEmpty()

    val hasPhotos: Boolean
        get() = photos.isNotEmpty()

    val hasExpenses: Boolean
        get() = expenses.isNotEmpty()

    val distance: Double
        get() = 100500.0

    val totalSpent: Double
        get() = expenses.sumOf { it.convertedSum }

    val formattedTotalSpent: String
        get() = totalSpent.formatSum() + user?.currency?.code

    val isOnlyExpenses: Boolean
        get() = !hasPoints && !hasPhotos && hasExpenses && description.isEmpty()

    companion object {

        fun createEmpty(): TripPostModel = TripPostModel(
            id = null,
            ownerId = null,
            tripId = null,
            user = null,
            name = "",
            description = "",
            photos = emptyList(),
            expenses = emptyList(),
            points = emptyList(),
            dateStart = getTimeMillis().formatDate(),
            timestampStart = getTimeMillis(),
            dateEnd = getTimeMillis().formatDate(),
            timestampEnd = getTimeMillis(),
            countries = emptyList()
        )
    }
}

fun List<String>?.photosToJsonString(json: Json) =
    this?.let { json.encodeToJsonElement(this).toString() }

fun String?.photosFromJsonString(json: Json) =
    this.orEmpty().takeIf { it.isNotEmpty() }?.let {
        json.decodeFromString<Array<String>>(it).toList()
    }.orEmpty()