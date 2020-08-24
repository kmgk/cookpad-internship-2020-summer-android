package com.cookpad.android.minicookpad.datasource

interface RecipeDataSource {
    fun fetchAll(onSuccess: (List<Recipe>) -> Unit, onFailed: (Throwable) -> Unit)
}