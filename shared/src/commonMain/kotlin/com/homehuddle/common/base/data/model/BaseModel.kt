package com.homehuddle.common.base.data.model

interface BaseModel<T> {
    val id: String?

    fun copyId(id: String?): T
}