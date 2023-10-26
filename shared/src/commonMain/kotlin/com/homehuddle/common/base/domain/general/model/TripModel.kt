package com.homehuddle.common.base.domain.general.model

import com.homehuddle.common.base.domain.utils.flatten
import kotlinx.serialization.Serializable

@Serializable
data class TripModel(
    override val id: String?,
    override val ownerId: String?,
    val name: String,
    val user: UserModel?,
    val description: String,
    val posts: List<TripPostModel>,
    val dateStart: String? = null,
    val timestampStart: Long? = null,
    val dateEnd: String? = null,
    val timestampEnd: Long? = null
): BaseDomainModel<TripModel> {

    val isMine: Boolean
        get() = user?.isMe ?: false

    val photos: List<String>
        get() = posts.flatten { it.photos }

    val distance: Double
        get() = 100500.0

    val totalSpent: Double
        get() = expenses.sumOf { it.sum }

    val expenses: List<TripExpenseModel>
        get() = posts.flatten { it.expenses }

    val start: String?
        get() = dateStart ?: posts.minBy { it.timestampStart ?: 0L }.dateStart

    val end: String?
        get() = dateEnd ?: posts.maxBy { it.timestampEnd ?: 0L }.dateEnd
}
