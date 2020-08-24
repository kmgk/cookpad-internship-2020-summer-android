package com.cookpad.android.minicookpad.recipelist

import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.RecipeEntity

class RecipeListInteractor(
    private val recipeDataSource: RecipeDataSource
) : RecipeListContract.Interactor {
    override fun fetchRecipeList(
        onSuccess: (List<RecipeListContract.Recipe>) -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        recipeDataSource.fetchAll(
            onSuccess = { list -> onSuccess.invoke(list.map { it.translate() }) },
            onFailed = onFailed
        )
    }

    private fun RecipeEntity.translate(): RecipeListContract.Recipe = RecipeListContract.Recipe(
        id = this.id,
        title = this.title,
        imagePath = this.imagePath ?: "",
        steps = this.steps.joinToString("、"),
        authorName = this.authorName
    )

}