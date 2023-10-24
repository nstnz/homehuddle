package com.homehuddle.common.base.di

import com.homehuddle.common.feature.general.login.LoginScreenViewModel
import com.homehuddle.common.feature.general.splash.SplashScreenViewModel
import com.homehuddle.common.feature.general.welcome.WelcomeScreenViewModel
import com.homehuddle.common.feature.personal.main.MainScreenViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindings.UnboundedScope
import org.kodein.di.instance
import org.kodein.di.multiton
import org.kodein.di.scoped
import org.kodein.di.singleton

internal val splashScope = object : UnboundedScope() {}
internal val mainScope = object : UnboundedScope() {}
internal val welcomeScope = object : UnboundedScope() {}
internal val loginScope = object : UnboundedScope() {}

internal val viewModelsDi = DI.Module(name = "ViewModels") {
    bind<SplashScreenViewModel>() with scoped(splashScope).singleton {
        SplashScreenViewModel(instance(), instance())
    }
    bind<WelcomeScreenViewModel>() with scoped(welcomeScope).multiton {
        WelcomeScreenViewModel(instance())
    }
    bind<LoginScreenViewModel>() with scoped(loginScope).multiton {
        LoginScreenViewModel(instance(), instance())
    }
    bind<MainScreenViewModel>() with scoped(mainScope).multiton {
        MainScreenViewModel(instance(), instance(), instance())
    }
}