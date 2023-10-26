package com.homehuddle.common.base.domain.general.model

interface BaseDomainModel<T> {
    val id: String?
    val ownerId: String?
}