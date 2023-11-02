package com.homehuddle.common.design.mocks

import com.homehuddle.common.base.domain.general.model.CountryModel
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.base.domain.general.model.TripExpenseCategory
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPointModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.UserModel

fun mockTrip() = TripModel(
    "sdfsdf",
    "sdfsdf",
    createTs = null,
    editTs = null,
    "Name",
    user = mockUser(),
    currency = mockCurrency(),
    description = "My trip",
    posts = listOf(
        mockTripPost(),
        mockTripPost(),
        mockTripPost(),
        mockTripPost(),
        mockTripPost(),
    ),
    countries = listOf()
)

fun mockUser() = UserModel(
    "", "",
    createTs = null,
    editTs = null,
    "User", isMe = true, currency = mockCurrency(),
    allCurrencies = emptyList(),
    allCountries = listOf(mockCountry()),
    visitedCountries = listOf(mockCountry()),
)

fun mockCountry() = CountryModel(
    "sdlksdflsd", "",
    createTs = null,
    editTs = null,
    "Ascension Island", "\uD83C\uDDE6\uD83C\uDDE8"
)

fun mockCurrency() = CurrencyModel(
    id = "USD",
    name = "American Dollar",
    rate = 1.0,
    code = "Зрол",
    ownerId = null
)

fun mockTripPost() = TripPostModel(
    "sdfsdf",
    "",
    createTs = null,
    editTs = null,
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
    dateEnd = "10.10.1010",
    timestampEnd = 10000231L,
    countries = listOf(mockCountry())
)

fun mockTripExpense() = TripExpenseModel(
    id = "",
    ownerId = "",
    description = "Bought some food",
    sum = 1028.0,
    currency = mockCurrency(),
    tripPostId = "",
    date = "10.10.1010",
    timestamp = 10000231L,
    category = TripExpenseCategory.Train
)

fun mockTripPoint() = TripPointModel(
    id = "",
    ownerId = "",
    name = "Point",
    description = "Description",
    address = "Address",
    lat = 0.0,
    lon = 1.0,
    tripPostId = ""
)