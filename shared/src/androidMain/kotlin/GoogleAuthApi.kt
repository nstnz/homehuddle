import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.homehuddle.common.base.data.model.User
import io.github.aakira.napier.Napier

private const val ServerClientId =
    "554504042887-oicvmi4sevlgpn8j9et6rsvnsts07fgi.apps.googleusercontent.com"

actual class GoogleAuthApi {

    private lateinit var callback: (String) -> Unit
    lateinit var activity: Activity

    private val googleSignIn by lazy {
        GoogleSignIn.getClient(
            Android.context,
            GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(ServerClientId)
                .requestServerAuthCode(ServerClientId)
                .requestEmail()
                .build()
        )
    }

    fun requestGoogleLogin(data: Intent?) =
        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken = requireNotNull(task.result.idToken)
                    callback(idToken)
                } else {
                    Napier.e("requestGoogleLogin", task.exception)
                }
            }

    actual suspend fun signIn(
        callback: (String) -> Unit
    ) {
        this.callback = callback
        activity.startActivityForResult(googleSignIn.signInIntent, 200)
    }

    actual suspend fun getSignedUser(): User? {
        val account = GoogleSignIn.getLastSignedInAccount(Android.context)
        return account?.let {
            User(
                id = it.id.orEmpty(),
                ownerId = it.id.orEmpty(),
                createTs = null,
                editTs = null,
                name = it.displayName.orEmpty(),
                currencyCode = "",
                visitedCountries = ""
            )
        }
    }

    actual fun logout() {
        googleSignIn.signOut()
    }
}