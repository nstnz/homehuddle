package com.homehuddle.common.base.di

import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.UserMemorySource
import com.homehuddle.common.base.data.networksource.CurrencyNetworkSource
import com.homehuddle.common.base.data.networksource.TripExpenseNetworkSource
import com.homehuddle.common.base.data.networksource.TripNetworkSource
import com.homehuddle.common.base.data.networksource.TripPointNetworkSource
import com.homehuddle.common.base.data.networksource.TripPostNetworkSource
import com.homehuddle.common.base.data.networksource.UserNetworkSource
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.data.repository.UserRepository
import com.homehuddle.common.base.domain.general.usecase.AnonymousAuthUseCase
import com.homehuddle.common.base.domain.general.usecase.GetMeUseCase
import com.homehuddle.common.base.domain.general.usecase.GetUserUseCase
import com.homehuddle.common.base.domain.general.usecase.IsSignedInUseCase
import com.homehuddle.common.base.domain.trips.scenario.GetUserTripsScenario
import com.homehuddle.common.base.domain.trips.usecase.GetUserTripPostsUseCase
import com.homehuddle.common.base.domain.trips.usecase.GetUserTripsUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

internal val coreDi = DI.Module(name = "Core") {
    bind<UserRepository>() with provider { UserRepository(instance(), instance(), instance()) }
    bind<TripPostRepository>() with provider {
        TripPostRepository(
            instance(),
            instance(),
            instance()
        )
    }
    bind<TripRepository>() with provider { TripRepository(instance()) }

    bind<CurrencyNetworkSource>() with provider { CurrencyNetworkSource(instance(), instance()) }
    bind<UserLocalSource>() with provider { UserLocalSource() }
    bind<UserMemorySource>() with provider { UserMemorySource() }
    bind<UserNetworkSource>() with provider { UserNetworkSource() }
    bind<TripNetworkSource>() with provider { TripNetworkSource() }
    bind<TripExpenseNetworkSource>() with provider { TripExpenseNetworkSource() }
    bind<TripPointNetworkSource>() with provider { TripPointNetworkSource() }
    bind<TripPostNetworkSource>() with provider { TripPostNetworkSource() }

    bind<GetUserTripsScenario>() with provider {
        GetUserTripsScenario(
            instance(),
            instance(),
            instance(),
            instance()
        )
    }

    bind<AnonymousAuthUseCase>() with provider { AnonymousAuthUseCase(instance(), instance()) }
    bind<IsSignedInUseCase>() with provider { IsSignedInUseCase(instance()) }
    bind<GetMeUseCase>() with provider { GetMeUseCase(instance(), instance()) }
    bind<GetUserUseCase>() with provider { GetUserUseCase(instance(), instance()) }
    bind<GetUserTripsUseCase>() with provider { GetUserTripsUseCase(instance(), instance()) }
    bind<GetUserTripPostsUseCase>() with provider {
        GetUserTripPostsUseCase(
            instance(),
            instance()
        )
    }
}