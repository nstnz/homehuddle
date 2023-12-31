package com.homehuddle.common.base.domain.general.model

import com.homehuddle.common.base.domain.utils.flatten
import com.homehuddle.common.base.domain.utils.formatDate
import com.homehuddle.common.base.domain.utils.formatSum
import io.ktor.util.date.getTimeMillis
import kotlinx.serialization.Serializable

@Serializable
data class TripModel(
    override val id: String?,
    override val ownerId: String?,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
    val name: String,
    val user: UserModel?,
    val currency: CurrencyModel?,
    val description: String,
    val posts: List<TripPostModel>,
    val dateStart: String? = null,
    val timestampStart: Long? = null,
    val dateEnd: String? = null,
    val timestampEnd: Long? = null,
    val countries: List<CountryModel>
): BaseDomainModel<TripModel> {

    val isMine: Boolean
        get() = user?.isMe ?: false

    val photos: List<String>
        get() = posts.flatten { it.photos }

    val distance: Double
        get() = 100500.0

    val totalSpent: Double
        get() = expenses.sumOf { it.convertedSum }

    val formattedTotalSpent: String
        get() = totalSpent.formatSum() + user?.currency?.code

    val expenses: List<TripExpenseModel>
        get() = posts.flatten { it.expenses }

    val start: String?
        get() = dateStart ?: posts.minBy { it.timestampStart ?: 0L }.dateStart

    val end: String?
        get() = dateEnd ?: posts.maxBy { it.timestampEnd ?: 0L }.dateEnd

    companion object {

        fun createEmpty(currency: CurrencyModel?): TripModel = TripModel(
            id = null,
            ownerId = null,
            user = null,
            name = "",
            description = "",
            dateStart = getTimeMillis().formatDate(),
            timestampStart = getTimeMillis(),
            dateEnd = getTimeMillis().formatDate(),
            timestampEnd = getTimeMillis(),
            countries = emptyList(),
            posts = emptyList(),
            currency = currency
        )
    }
}
