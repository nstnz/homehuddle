package com.homehuddle.common.base.domain.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.pow
import kotlin.math.roundToInt

internal fun <T, K> List<T>.flatten(
    func: (T) -> List<K>
): List<K> {
    val result = mutableListOf<K>()
    this.forEach {
        result.addAll(func(it))
    }
    return result
}

internal fun Double?.formatSum(): String? =
    this?.let {
        val integerDigits = this.toInt()
        val floatDigits = ((this - integerDigits) * 10.0.pow(2)).roundToInt()
        return if (floatDigits == 0) integerDigits.toString()
        else "${integerDigits}.${floatDigits}"
    }

internal fun String?.formatSum(): Double =
    this?.let {
        val parts = this
            .replace(",", ".")
            .split(".")
        val integerDigits = parts.getOrElse(0) { "0" }.toIntOrNull() ?: 0
        val floatDigits = parts.getOrElse(1) { "0" }.toDoubleOrNull() ?: 0.0
        return if (floatDigits == 0.0) integerDigits.toDouble()
        else integerDigits.toDouble() + floatDigits / 10.0.pow(2)
    } ?: 0.0

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