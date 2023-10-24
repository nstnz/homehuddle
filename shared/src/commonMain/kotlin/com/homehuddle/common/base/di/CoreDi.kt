package com.homehuddle.common.base.di

import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.UserMemorySource
import com.homehuddle.common.base.data.networksource.TripNetworkSource
import com.homehuddle.common.base.data.networksource.UserNetworkSource
import com.homehuddle.common.base.data.repository.UserRepository
import com.homehuddle.common.base.domain.general.scenario.LoginScenario
import com.homehuddle.common.base.domain.general.usecase.AnonymousAuthUseCase
import com.homehuddle.common.base.domain.general.usecase.GetUserUseCase
import com.homehuddle.common.base.domain.general.usecase.IsSignedInUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

internal val coreDi = DI.Module(name = "Core") {
    bind<AnonymousAuthUseCase>() with provider {
        AnonymousAuthUseCase(instance(), instance())
    }
    bind<IsSignedInUseCase>() with provider {
        IsSignedInUseCase(instance())
    }
    bind<UserRepository>() with provider {
        UserRepository(instance(), instance(), instance())
    }
    bind<UserLocalSource>() with provider {
        UserLocalSource()
    }
    bind<UserMemorySource>() with provider {
        UserMemorySource()
    }
    bind<UserNetworkSource>() with provider {
        UserNetworkSource()
    }
    bind<TripNetworkSource>() with provider {
        TripNetworkSource()
    }
    bind<LoginScenario>() with provider {
        LoginScenario(instance(), instance())
    }
    bind<GetUserUseCase>() with provider {
        GetUserUseCase(instance(), instance())
    }
}