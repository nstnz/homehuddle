expect class FirebaseFirestoreImpl() {

    suspend fun createOrUpdate(
        collection: String,
        id: String,
        model: Any
    )

    suspend fun delete(collection: String, id: String)

    suspend fun delete(collection: String, condition: Pair<String, Any>)

    suspend fun get(
        collection: String,
        condition: Pair<String, Any>
    ): List<MutableMap<String, Any>?>

    suspend fun get(
        collection: String,
        id: String
    ): MutableMap<String, Any>?
}