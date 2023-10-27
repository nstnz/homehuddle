package com.homehuddle.common.base.data.networksource

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

internal suspend inline fun HttpResponse.retrieve(): NetworkResponse {
    val obj = Json.parseToJsonElement(this.bodyAsText())

    return when (this.status) {
        HttpStatusCode.OK -> NetworkResponse.Success(obj as JsonObject)
        else -> NetworkResponse.Failed
    }
}