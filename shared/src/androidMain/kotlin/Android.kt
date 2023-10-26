import android.annotation.SuppressLint
import android.content.Context
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@SuppressLint("StaticFieldLeak")
object Android {

    lateinit var context: Context
        private set

    fun init(appContext: Context) {
        context = appContext
        Napier.base(DebugAntilog())
    }
}