package com.cookpad.android.minicookpad.datasource

interface RecipeDataSource {
    fun fetchAll(onSuccess: (List<RecipeEntity>) -> Unit, onFailed: (Throwable) -> Unit)

    fun createRecipe(recipeEntity: RecipeEntity, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit)
}