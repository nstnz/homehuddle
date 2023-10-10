import android.annotation.SuppressLint
import android.content.Context
import com.google.firebase.FirebaseApp
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

@SuppressLint("StaticFieldLeak")
object Android {

    lateinit var context: Context
        private set

    fun init(appContext: Context) {
        context = appContext
        FirebaseApp.initializeApp(appContext)
        Firebase.initialize(appContext)
    }
}