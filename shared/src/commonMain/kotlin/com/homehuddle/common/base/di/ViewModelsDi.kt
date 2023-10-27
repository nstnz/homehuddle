package com.homehuddle.common.base.di

import com.homehuddle.common.feature.general.login.LoginScreenViewModel
import com.homehuddle.common.feature.general.splash.SplashScreenViewModel
import com.homehuddle.common.feature.general.welcome.WelcomeScreenViewModel
import com.homehuddle.common.feature.personal.createpost.CreatePostScreenViewModel
import com.homehuddle.common.feature.personal.createtrip.CreateTripScreenViewModel
import com.homehuddle.common.feature.personal.main.MainScreenViewModel
import com.homehuddle.common.feature.personal.tripdetails.TripDetailsScreenViewModel
import com.homehuddle.common.feature.personal.trippost.TripPostScreenViewModel
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
internal val tripPostScope = object : UnboundedScope() {}
internal val tripDetailsScope = object : UnboundedScope() {}
internal val createTripScope = object : UnboundedScope() {}
internal val createPostScope = object : UnboundedScope() {}

internal val viewModelsDi = DI.Module(name = "ViewModels") {
    bind<SplashScreenViewModel>() with scoped(splashScope).singleton {
        SplashScreenViewModel(instance(), instance(), instance())
    }
    bind<WelcomeScreenViewModel>() with scoped(welcomeScope).multiton {
        WelcomeScreenViewModel(instance())
    }
    bind<LoginScreenViewModel>() with scoped(loginScope).multiton {
        LoginScreenViewModel(instance(), instance())
    }
    bind<MainScreenViewModel>() with scoped(mainScope).multiton {
        MainScreenViewModel(instance(), instance(), instance(), instance())
    }
    bind<TripPostScreenViewModel>() with scoped(tripPostScope).multiton { id: String? ->
        TripPostScreenViewModel(id.orEmpty(), instance())
    }
    bind<TripDetailsScreenViewModel>() with scoped(tripDetailsScope).multiton { id: String? ->
        TripDetailsScreenViewModel(id.orEmpty(), instance(), instance(), instance())
    }
    bind<CreateTripScreenViewModel>() with scoped(createTripScope).multiton { id: String? ->
        CreateTripScreenViewModel(id, instance(), instance(), instance(), instance())
    }
    bind<CreatePostScreenViewModel>() with scoped(createPostScope).multiton { id: String? ->
        CreatePostScreenViewModel(id, instance(), instance(), instance(), instance())
    }
}