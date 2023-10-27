package com.homehuddle.common.base.di

import com.homehuddle.common.base.data.dbsource.CountryDbSource
import com.homehuddle.common.base.data.dbsource.CurrencyDbSource
import com.homehuddle.common.base.data.dbsource.TripDbSource
import com.homehuddle.common.base.data.dbsource.TripExpenseDbSource
import com.homehuddle.common.base.data.dbsource.TripPointDbSource
import com.homehuddle.common.base.data.dbsource.TripPostDbSource
import com.homehuddle.common.base.data.dbsource.UserDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.CountryMemorySource
import com.homehuddle.common.base.data.memorysource.CurrencyMemorySource
import com.homehuddle.common.base.data.memorysource.TripExpenseMemorySource
import com.homehuddle.common.base.data.memorysource.TripMemorySource
import com.homehuddle.common.base.data.memorysource.TripPointMemorySource
import com.homehuddle.common.base.data.memorysource.TripPostMemorySource
import com.homehuddle.common.base.data.memorysource.UserMemorySource
import com.homehuddle.common.base.data.networksource.CountryNetworkSource
import com.homehuddle.common.base.data.networksource.CurrencyNetworkSource
import com.homehuddle.common.base.data.networksource.TripExpenseNetworkSource
import com.homehuddle.common.base.data.networksource.TripNetworkSource
import com.homehuddle.common.base.data.networksource.TripPointNetworkSource
import com.homehuddle.common.base.data.networksource.TripPostNetworkSource
import com.homehuddle.common.base.data.networksource.UserNetworkSource
import com.homehuddle.common.base.data.repository.CountryRepository
import com.homehuddle.common.base.data.repository.CurrencyRepository
import com.homehuddle.common.base.data.repository.TripExpenseRepository
import com.homehuddle.common.base.data.repository.TripPointRepository
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.data.repository.UserRepository
import com.homehuddle.common.base.domain.general.usecase.AnonymousAuthUseCase
import com.homehuddle.common.base.domain.general.usecase.GetMeUseCase
import com.homehuddle.common.base.domain.general.usecase.GetUserUseCase
import com.homehuddle.common.base.domain.general.usecase.IsSignedInUseCase
import com.homehuddle.common.base.domain.general.usecase.RefreshDataUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.CreateTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.DeleteTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetLastTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetUserTripsFlowUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.UpdateTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.tripexpense.CreateOnlyTripExpenseUseCase
import com.homehuddle.common.base.domain.trips.usecase.tripexpense.GetTripExpenseUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.CreateTripPostUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.DeleteTripPostUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.GetTripPostUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.GetUserTripPostsFlowUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.UpdateTripPostUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

internal val coreDi = DI.Module(name = "Core") {
    bind<UserRepository>() with provider {
        UserRepository(
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance()
        )
    }
    bind<CurrencyRepository>() with provider {
        CurrencyRepository(
            instance(),
            instance(),
            instance(),
            instance()
        )
    }
    bind<CountryRepository>() with provider {
        CountryRepository(
            instance(),
            instance(),
            instance(),
            instance()
        )
    }
    bind<TripPostRepository>() with provider {
        TripPostRepository(
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
        )
    }
    bind<TripRepository>() with provider {
        TripRepository(
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance()
        )
    }
    bind<TripPointRepository>() with provider {
        TripPointRepository(
            instance(),
            instance(),
            instance(),
            instance(),
        )
    }
    bind<TripExpenseRepository>() with provider {
        TripExpenseRepository(
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
        )
    }

    bind<UserDbSource>() with provider { UserDbSource(instance()) }
    bind<UserLocalSource>() with provider { UserLocalSource() }
    bind<UserMemorySource>() with provider { UserMemorySource() }
    bind<UserNetworkSource>() with provider { UserNetworkSource(instance()) }

    bind<CurrencyMemorySource>() with provider { CurrencyMemorySource() }
    bind<CurrencyDbSource>() with provider { CurrencyDbSource(instance()) }
    bind<CurrencyNetworkSource>() with provider { CurrencyNetworkSource(
        instance(), instance(),
        instance(),instance(),instance(),
    ) }

    bind<CountryMemorySource>() with provider { CountryMemorySource() }
    bind<CountryDbSource>() with provider { CountryDbSource(instance()) }
    bind<CountryNetworkSource>() with provider { CountryNetworkSource(
        instance(), instance(), instance(),
    ) }

    bind<TripNetworkSource>() with provider { TripNetworkSource(instance()) }
    bind<TripDbSource>() with provider { TripDbSource(instance()) }
    bind<TripMemorySource>() with provider { TripMemorySource() }

    bind<TripExpenseNetworkSource>() with provider { TripExpenseNetworkSource(instance()) }
    bind<TripExpenseDbSource>() with provider { TripExpenseDbSource(instance()) }
    bind<TripExpenseMemorySource>() with provider { TripExpenseMemorySource() }

    bind<TripPointNetworkSource>() with provider { TripPointNetworkSource(instance()) }
    bind<TripPointDbSource>() with provider { TripPointDbSource(instance()) }
    bind<TripPointMemorySource>() with provider { TripPointMemorySource() }

    bind<TripPostNetworkSource>() with provider { TripPostNetworkSource(instance()) }
    bind<TripPostDbSource>() with provider { TripPostDbSource(instance()) }
    bind<TripPostMemorySource>() with provider { TripPostMemorySource() }



    bind<GetUserTripsFlowUseCase>() with singleton { GetUserTripsFlowUseCase(instance()) }
    bind<GetUserTripPostsFlowUseCase>() with singleton { GetUserTripPostsFlowUseCase(instance()) }



    bind<RefreshDataUseCase>() with provider {
        RefreshDataUseCase(
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance()
        )
    }
    bind<AnonymousAuthUseCase>() with provider { AnonymousAuthUseCase(instance(), instance(), instance()) }
    bind<IsSignedInUseCase>() with provider { IsSignedInUseCase(instance()) }
    bind<GetMeUseCase>() with provider { GetMeUseCase(instance(), instance()) }
    bind<GetUserUseCase>() with provider { GetUserUseCase(instance(), instance()) }

    bind<GetTripUseCase>() with provider { GetTripUseCase(instance(), instance()) }
    bind<GetLastTripUseCase>() with provider { GetLastTripUseCase(instance(), instance()) }
    bind<UpdateTripUseCase>() with provider { UpdateTripUseCase(instance(), instance(), instance()) }
    bind<CreateTripUseCase>() with provider { CreateTripUseCase(instance(), instance(), instance()) }
    bind<DeleteTripUseCase>() with provider { DeleteTripUseCase(instance(), instance()) }

    bind<GetTripPostUseCase>() with provider { GetTripPostUseCase(instance(), instance()) }
    bind<UpdateTripPostUseCase>() with provider { UpdateTripPostUseCase(instance(), instance()) }
    bind<CreateTripPostUseCase>() with provider { CreateTripPostUseCase(instance(), instance()) }
    bind<DeleteTripPostUseCase>() with provider { DeleteTripPostUseCase(instance(), instance()) }

    bind<GetTripExpenseUseCase>() with provider { GetTripExpenseUseCase(instance(), instance()) }
    bind<CreateOnlyTripExpenseUseCase>() with provider { CreateOnlyTripExpenseUseCase(instance(), instance(), instance()) }

}