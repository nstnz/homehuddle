package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
enum class TripExpenseCategory {
    Plane,
    Train,
    Tram,
    Bus,
    Car,
    Boat,
    Hotel,
    Museum,
    Entertaining,
    Shop,
    Restaurant,
    Fastfood,
    Grocery,
    Souvenir,
    Other,
}