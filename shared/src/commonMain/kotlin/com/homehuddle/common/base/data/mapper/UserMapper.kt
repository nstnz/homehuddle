package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.domain.general.model.UserModel
import dev.gitlive.firebase.firestore.DocumentSnapshot

internal fun DocumentSnapshot.mapToUser(): User? =
    this.exists.takeIf { true }?.let {
        User(
            id = this.id,
            name = this.get("name"),
            currencyCode = this.get("currencyCode"),
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