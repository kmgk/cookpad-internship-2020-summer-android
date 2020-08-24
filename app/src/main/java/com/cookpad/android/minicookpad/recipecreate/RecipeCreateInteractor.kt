package com.cookpad.android.minicookpad.recipecreate

import com.cookpad.android.minicookpad.datasource.ImageDataSource
import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.RecipeEntity

class RecipeCreateInteractor(
    private val imageDataSource: ImageDataSource,
    private val recipeDataSource: RecipeDataSource
) : RecipeCreateContract.Interactor {
    override fun createRecipe(
        recipe: RecipeCreateContract.Recipe,
        onSuccess: () -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        imageDataSource.saveImage(recipe.imageUri, onSuccess = {
            recipeDataSource.createRecipe(
                translateRecipe(recipe, it),
                onSuccess = onSuccess,
                onFailed = onFailed
            )
        }, onFailed = onFailed)
    }

    private fun translateRecipe(
        recipe: RecipeCreateContract.Recipe,
        imagePath: String
    ): RecipeEntity = RecipeEntity(
        title = recipe.title,
        imagePath = imagePath,
        steps = recipe.Steps,
        authorName = "クックパッド味"
    )

    private fun RecipeCreateContract.Recipe.translate(imagePath: String):RecipeEntity = RecipeEntity(
        title = this.title,
        imagePath = imagePath,
        steps = this.Steps,
        authorName = "クックパッド味"
    )
}