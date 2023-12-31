package com.homehuddle.common.base.data.model

interface BaseDataModel<T> {
    val id: String?
    val ownerId: String?
    val createTs: Long?
    val editTs: Long?
}