package com.homehuddle.common.router

import cafe.adriel.voyager.navigator.Navigator
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.feature.general.login.LoginScreenHolder
import com.homehuddle.common.feature.general.setup.SetupScreenHolder
import com.homehuddle.common.feature.general.welcome.WelcomeScreenHolder
import com.homehuddle.common.feature.personal.createexpense.CreateExpenseScreenHolder
import com.homehuddle.common.feature.personal.createpost.CreatePostScreenHolder
import com.homehuddle.common.feature.personal.createtrip.CreateTripScreenHolder
import com.homehuddle.common.feature.personal.main.MainScreenHolder
import com.homehuddle.common.feature.personal.tripdetails.TripDetailsScreenHolder
import com.homehuddle.common.feature.personal.trippost.TripPostScreenHolder
import org.kodein.di.bindings.UnboundedScope

internal class Router() {

    private lateinit var navigator: Navigator

    fun init(navigator: Navigator) {
        this.navigator = navigator
    }

    fun back(scope: UnboundedScope) {
        navigator.pop()
        scope.close()
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

    fun navigateToSetup() {
        navigator.replace(SetupScreenHolder)
    }

    fun navigateToAddTrip() {
        navigator.push(CreateTripScreenHolder(null))
    }

    fun navigateToEditTrip(trip: TripModel?) {
        navigator.push(CreateTripScreenHolder(trip))
    }

    fun navigateToEditTripPost(tripPost: TripPostModel?) {
        navigator.push(CreatePostScreenHolder(tripPost))
    }

    fun navigateToAddTripPost() {
        navigator.push(CreatePostScreenHolder(null))
    }

    fun navigateToAddExpenses(
        onCustomExpenseCreation: ((TripExpenseModel?) -> Unit)? = null,
    ) {
        navigator.push(CreateExpenseScreenHolder(null, onCustomExpenseCreation))
    }

    fun navigateToAddLocations() {
        navigator.push(CreatePostScreenHolder(null))
    }

    fun navigateToTripPost(tripPost: TripPostModel) {
        navigator.push(TripPostScreenHolder(tripPost))
    }

    fun navigateToTrip(trip: TripModel) {
        navigator.push(TripDetailsScreenHolder(trip))
    }
}