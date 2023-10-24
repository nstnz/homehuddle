import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

actual class FirebaseFirestoreImpl {

    init {
        FirebaseApp.initializeApp(Android.context)
    }

    private val firestore = FirebaseFirestore.getInstance()

    actual suspend fun createOrUpdate(
        collection: String,
        id: String,
        model: Any
    ) {
        firestore.collection(collection)
            .document(id)
            .update(id, model)
            .await()
    }

    actual suspend fun get(
        collection: String,
        id: String
    ): MutableMap<String, Any>? = firestore.collection(collection)
        .document(id)
        .get()
        .await()
        .data

    actual suspend fun get(
        collection: String,
        condition: Pair<String, Any>
    ): List<MutableMap<String, Any>?> = firestore.collection(collection)
        .whereEqualTo(condition.first, condition.second)
        .get()
        .await()
        .documents
        .map { it.data }

    actual suspend fun delete(collection: String, id: String) {
        firestore.collection(collection)
            .document(id)
            .delete()
            .await()
    }

    actual suspend fun delete(collection: String, condition: Pair<String, Any>) {
        firestore.collection(collection)
            .whereEqualTo(condition.first, condition.second)
            .get()
            .await()
            .documents
            .forEach { it.reference.delete() }
    }
}