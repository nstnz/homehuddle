package com.homehuddle.common.base.data.networksource

import kotlinx.serialization.json.JsonObject

internal sealed interface NetworkResponse {

    object Failed : NetworkResponse
    data class Success(val obj: JsonObject) : NetworkResponse
}