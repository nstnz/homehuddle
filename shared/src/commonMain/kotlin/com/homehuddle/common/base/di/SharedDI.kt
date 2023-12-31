package com.homehuddle.common.base.di

import FirebaseFirestoreImpl
import FirebaseRemoteConfigImpl
import FirebaseStorageImpl
import GoogleAuthApi
import com.homehuddle.AppDatabase
import com.homehuddle.AppDatabaseQueries
import com.homehuddle.common.router.Router
import com.squareup.sqldelight.db.SqlDriver
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object SharedDI {

    private lateinit var databaseDriver: SqlDriver

    fun initializeWithParams(
        databaseDriver: SqlDriver
    ) {
        println("Launch application")
        this.databaseDriver = databaseDriver
    }

    val di = DI {
        bind<AppDatabaseQueries>() with singleton {
            val database = AppDatabase(databaseDriver)
            database.appDatabaseQueries
        }
        bind<FirebaseFirestore>() with singleton { Firebase.firestore }
        bind<FirebaseFirestoreImpl>() with singleton { FirebaseFirestoreImpl() }
        bind<GoogleAuthApi>() with singleton { GoogleAuthApi() }
        bind<FirebaseRemoteConfigImpl>() with singleton { FirebaseRemoteConfigImpl() }
        bind<FirebaseStorageImpl>() with singleton { FirebaseStorageImpl() }
        bind<Router>() with singleton { Router() }
        bind<CoroutineDispatcher>() with singleton { Dispatchers.Default }
        bind<CoroutineScope>() with singleton {
            CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }

        bind<HttpClient>() with singleton { HttpClient() }
        bind<Json>() with singleton { Json { ignoreUnknownKeys = true } }

        import(coreDi)
        import(viewModelsDi)
    }
}
