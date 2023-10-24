package com.homehuddle.common.base.domain.utils

import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.Query

internal fun <T, K> List<T>.flatten(
    func: (T) -> List<K>
): List<K> {
    val result = mutableListOf<K>()
    this.forEach {
        result.addAll(func(it))
    }
    return result
}

internal suspend fun DocumentReference.safeGet() = try {
    this.get()
} catch (e: Exception) {
    null
}

internal suspend fun Query.safeGet() = try {
    this.get()
} catch (e: Exception) {
    null
}