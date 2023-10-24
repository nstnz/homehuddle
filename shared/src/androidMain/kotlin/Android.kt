import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object Android {

    lateinit var context: Context
        private set

    fun init(appContext: Context) {
        context = appContext
        //FirebaseApp.initializeApp(appContext)
        //Firebase.initialize(appContext)
    }
}