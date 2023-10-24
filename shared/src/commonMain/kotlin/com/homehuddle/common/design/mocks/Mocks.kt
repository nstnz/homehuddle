package com.homehuddle.common.design.mocks

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.model.TripExpense
import com.homehuddle.common.base.data.model.TripPoint
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.model.User

fun mockTrip() = Trip("sdfsdf", "My trip", "Description")

fun mockUser() = User(
    "", "User"
)

fun mockTripPost() = TripPost(
    "sdfsdf",
    "",
    "Lorem ipsum skldjflskdf lsjdf;l jsl;dfjs; fjsldf jsdfbjhsbdfk s",
    photos = listOf("", ""),
    expenses = listOf(
        TripExpense(
            id = "",
            description = "Bought some food",
            sum = 1028.0,
            currencyCode = "USD"
        ),
        TripExpense(
            id = "",
            description = "Bought some food",
            sum = 1028.0,
            currencyCode = "USD"
        ),
        TripExpense(
            id = "",
            description = "Bought some food",
            sum = 1028.0,
            currencyCode = "USD"
        ),
        TripExpense(
            id = "",
            description = "Bought some food",
            sum = 1028.0,
            currencyCode = "USD"
        ),
    ),
    fromToRoute = listOf(
        TripPoint(
            id = "",
            description = "Description",
            lat = 12.0,
            lon = 1.0
        ),TripPoint(
            id = "",
            description = "Description",
            lat = 12.0,
            lon = 1.0
        ),
    )
)