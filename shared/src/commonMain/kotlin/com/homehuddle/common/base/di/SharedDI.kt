package com.homehuddle.common.base.di

import com.homehuddle.common.router.Router
import io.ktor.client.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.kodein.di.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object SharedDI {

    fun initializeWithParams() {
        println("Launch application")
    }

    internal val di = DI {
        bind<Router>() with singleton { Router() }
        bind<CoroutineDispatcher>() with singleton { Dispatchers.Default }
        bind<CoroutineScope>() with singleton {
            CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }

        bind<HttpClient>() with singleton { HttpClient() }
        bind<Json>() with singleton { Json { ignoreUnknownKeys = true } }
        /*bind<SharedPreferences>() with singleton { sharedPreferences }
        bind<AppDatabaseQueries>() with singleton {
            val database = AppDatabase(databaseDriver)
            database.appDatabaseQueries
        }
        bind<Strings>() with singleton { En_Strings }*/

        import(coreDi)
        import(viewModelsDi)
    }
}
