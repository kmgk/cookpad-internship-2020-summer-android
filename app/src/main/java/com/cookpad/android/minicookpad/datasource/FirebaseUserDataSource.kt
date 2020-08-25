package com.cookpad.android.minicookpad.datasource

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseUserDataSource : RemoteUserDataSource {
    private val db = FirebaseFirestore.getInstance()

    override fun fetch(
        name: String,
        onSuccess: (UserEntity?) -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        db.collection(COLLECTION_PATH)
            .whereEqualTo("name", name)
            .get()
            .addOnSuccessListener {
                it.documents.firstOrNull()?.let { document ->
                    onSuccess.invoke(UserEntity.fromDocument(document))
                } ?: onSuccess.invoke(null)
            }
            .addOnFailureListener(onFailed)
    }

    override fun save(
        user: UserEntity,
        onSuccess: (UserEntity) -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        db.collection(COLLECTION_PATH)
            .add(user.toMap())
            .addOnSuccessListener { onSuccess.invoke(user.copy(id = it.id)) }
            .addOnFailureListener(onFailed)
    }

    private companion object {
        const val COLLECTION_PATH = "users"
    }
}