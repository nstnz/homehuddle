import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

actual class FirebaseRemoteConfigImpl {

    private val remoteConfig: FirebaseRemoteConfig by lazy {
        FirebaseRemoteConfig.getInstance()
    }

    init {
        FirebaseApp.initializeApp(Android.context)

        remoteConfig.fetch(60)
        remoteConfig.fetchAndActivate()
    }

    actual suspend fun get(
        collection: String,
    ): String {
        return remoteConfig
            .getValue(collection).asString()
    }
}