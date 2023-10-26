package com.homehuddle.common.design.mocks

import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPointModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.UserModel

fun mockTrip() = TripModel(
    "sdfsdf",
    "sdfsdf",
    "Name",
    user = mockUser(),
    "My trip",
    posts = listOf(
        mockTripPost(),
        mockTripPost(),
        mockTripPost(),
        mockTripPost(),
        mockTripPost(),
    )
)

fun mockUser() = UserModel(
    "", "", "User", isMe = true, currencyCode = "USD"
)

fun mockTripPost() = TripPostModel(
    "sdfsdf",
    "",
    "",
    user = mockUser(),
    name = "sdfalskds",
    description = "Lorem ipsum skldjflskdf lsjdf;l jsl;dfjs; fjsldf jsdfbjhsbdfk s",
    photos = listOf("", ""),
    expenses = listOf(
        mockTripExpense(),
        mockTripExpense(),
        mockTripExpense(),
        mockTripExpense(),
    ),
    points = listOf(
        mockTripPoint(),
        mockTripPoint(),
        mockTripPoint(),
    ),
    dateStart = "10.10.1010",
    timestampStart = 10000231L,
)

fun mockTripExpense() = TripExpenseModel(
    id = "",
    ownerId = "",
    description = "Bought some food",
    sum = 1028.0,
    currencyCode = "USD",
    tripPostId = "",
    date = "10.10.1010",
    timestamp = 10000231L
)

fun mockTripPoint() = TripPointModel(
    id = "",
    ownerId = "",
    description = "Description",
    lat = 12.0,
    lon = 1.0,
    tripPostId = ""
)