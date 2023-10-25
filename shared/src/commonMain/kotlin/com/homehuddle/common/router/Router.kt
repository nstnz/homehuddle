package com.homehuddle.common.router

import cafe.adriel.voyager.navigator.Navigator
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.feature.general.login.LoginScreenHolder
import com.homehuddle.common.feature.general.welcome.WelcomeScreenHolder
import com.homehuddle.common.feature.personal.main.MainScreenHolder
import com.homehuddle.common.feature.personal.tripdetails.TripDetailsScreenHolder
import com.homehuddle.common.feature.personal.trippost.TripPostScreenHolder

internal class Router() {

    private lateinit var navigator: Navigator

    fun init(navigator: Navigator) {
        this.navigator = navigator
    }

    fun back() {
        navigator.pop()
    }

    fun navigateToWelcomeScreen() {
        navigator.replace(WelcomeScreenHolder)
    }

    fun navigateToMainScreen() {
        navigator.replace(MainScreenHolder)
    }

    fun navigateToLogin() {
        navigator.replace(LoginScreenHolder)
    }

    fun navigateToAddTrip() {

    }

    fun navigateToAddTripPost() {

    }

    fun navigateToAddExpenses() {

    }

    fun navigateToAddLocations() {

    }

    fun navigateToTripPost(tripPost: TripPostModel) {
        navigator.push(TripPostScreenHolder(tripPost))
    }

    fun navigateToTrip(trip: TripModel) {
        navigator.push(TripDetailsScreenHolder(trip))
    }
}