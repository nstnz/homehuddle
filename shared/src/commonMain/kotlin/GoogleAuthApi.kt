import com.homehuddle.common.base.data.model.User

expect class GoogleAuthApi() {

    suspend fun signIn(
        callback: (String) -> Unit
    )
    fun logout()
    suspend fun getSignedUser(): User?
}