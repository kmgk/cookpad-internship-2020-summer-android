package com.cookpad.android.minicookpad.datasource

import com.google.firebase.firestore.DocumentSnapshot

data class UserEntity(
    val id: String? = null,
    val name: String
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "name" to name
    )

    companion object {
        fun fromDocument(document: DocumentSnapshot): UserEntity =
            UserEntity(id = document.id, name = document["name"] as String)
    }
}