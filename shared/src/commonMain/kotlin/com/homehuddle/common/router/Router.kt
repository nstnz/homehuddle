package com.homehuddle.common.router

import moe.tlaster.precompose.navigation.Navigator

internal class Router() {

    private lateinit var navigator: Navigator

    fun init(navigator: Navigator) {
        this.navigator = navigator
    }

    fun back() {
        navigator.goBack()
    }

    fun navigateToWelcomeScreen() {
        navigator.navigate(Routes.Welcome)
    }

    fun navigateToMainScreen() {
        navigator.navigate(Routes.Main)
    }

    fun navigateToLogin() {
        navigator.navigate(Routes.Login)
    }
}