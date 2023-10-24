package com.homehuddle.common.router

import cafe.adriel.voyager.navigator.Navigator
import com.homehuddle.common.feature.general.login.LoginScreenHolder
import com.homehuddle.common.feature.general.welcome.WelcomeScreenHolder
import com.homehuddle.common.feature.personal.main.MainScreenHolder

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
}