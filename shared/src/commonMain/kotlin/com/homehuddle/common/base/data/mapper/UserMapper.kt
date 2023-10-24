package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.domain.general.model.UserModel

internal fun MutableMap<String, Any>?.mapToUser(): User? =
    this?.let {
        User(
            id = this["id"].toString(),
            name = this["name"].toString(),
            currencyCode = this["currencyCode"].toString(),
        )
    }

internal fun User?.mapToUserModel(isMe: Boolean): UserModel? =
    this?.let {
        UserModel(
            id = this.id,
            name = this.name,
            currencyCode = this.currencyCode,
            isMe = isMe
        )
    }