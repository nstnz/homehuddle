import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

actual class FirebaseStorageImpl {

    private val storage: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    init {
        FirebaseApp.initializeApp(Android.context)
    }

    actual suspend fun upload(
        id: String,
        byteArray: ByteArray,
    ): String {
        val storageRef = storage.reference
        val photoRef = storageRef.child(id)
        val uploadTask = photoRef.putBytes(byteArray)
        val result = uploadTask.await()
        val url = result.metadata?.reference?.downloadUrl?.await()?.toString()
        return url.orEmpty()
    }
}