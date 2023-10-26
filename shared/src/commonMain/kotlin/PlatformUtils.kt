import androidx.compose.ui.text.font.FontFamily
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver

expect fun getFont(): FontFamily

expect fun getSettings(name: String): Settings

expect fun createDriver(): SqlDriver