import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.homehuddle.common.R
import com.homehuddle.common.router.RootView
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual fun getPlatformName(): String = "Android"

@Composable
fun MainView() = RootView()

actual fun getFont(): FontFamily = FontFamily(Font(R.font.comfortaa))

actual fun getSettings(name: String): Settings {
    val delegate = Android.context.getSharedPreferences(
        name, Context.MODE_PRIVATE
    )
    return SharedPreferencesSettings(delegate)
}
