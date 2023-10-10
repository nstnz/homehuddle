import androidx.compose.ui.text.font.FontFamily
import com.russhwolf.settings.Settings

expect fun getFont(): FontFamily

expect fun getSettings(name: String): Settings