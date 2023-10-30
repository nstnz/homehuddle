expect class FirebaseStorageImpl() {

    suspend fun upload(
        id: String,
        byteArray: ByteArray,
    ): String
}