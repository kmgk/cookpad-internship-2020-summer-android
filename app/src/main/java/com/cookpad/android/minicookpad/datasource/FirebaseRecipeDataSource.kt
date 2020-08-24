package com.cookpad.android.minicookpad.datasource

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRecipeDataSource : RecipeDataSource {
    private val db = FirebaseFirestore.getInstance()

    override fun fetchAll(onSuccess: (List<Recipe>) -> Unit, onFailed: (Throwable) -> Unit) {
        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->
                onSuccess.invoke(result.mapNotNull { Recipe.fromDocument(it) })
            }
            .addOnFailureListener(onFailed)
    }
}