package com.homehuddle.common.base.di

import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.repository.UserRepository
import com.homehuddle.common.base.domain.general.usecase.AnonymousAuthUseCase
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
        IsSignedInUseCase(instance(), instance())
    }
    bind<UserRepository>() with provider {
        UserRepository(instance())
    }
    bind<UserLocalSource>() with provider {
        UserLocalSource()
    }
}