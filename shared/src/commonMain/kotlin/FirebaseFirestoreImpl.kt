import com.homehuddle.common.base.data.model.BaseModel

expect class FirebaseFirestoreImpl() {

    suspend fun <T> create(
        collection: String,
        model: BaseModel<T>
    ): String

    suspend fun <T> update(
        collection: String,
        model: BaseModel<T>
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