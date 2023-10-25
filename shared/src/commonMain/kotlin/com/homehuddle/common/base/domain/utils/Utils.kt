package com.homehuddle.common.base.domain.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal fun <T, K> List<T>.flatten(
    func: (T) -> List<K>
): List<K> {
    val result = mutableListOf<K>()
    this.forEach {
        result.addAll(func(it))
    }
    return result
}

internal fun Long?.formatDate(): String? =
    this?.let {
        kotlinx.datetime.Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
            .let {
                "${it.dayOfMonth.zeroPrefixed(2)}.${it.monthNumber.zeroPrefixed(2)}.${it.year}"
            }
    }

internal fun Int.zeroPrefixed(
    maxLength: Int,
): String {
    if (this < 0 || maxLength < 1) return ""

    val string = this.toString()
    val currentStringLength = string.length
    return if (maxLength <= currentStringLength) {
        string
    } else {
        val diff = maxLength - currentStringLength
        var prefixedZeros = ""
        repeat(diff) {
            prefixedZeros += "0"
        }
        "$prefixedZeros$string"
    }
}