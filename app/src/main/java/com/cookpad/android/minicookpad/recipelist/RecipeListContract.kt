package com.cookpad.android.minicookpad.recipelist

interface RecipeListContract {
    interface View {
        fun renderRecipeList(recipeList: List<RecipeListContract.Recipe>)
        fun renderError(exception: Throwable)
    }

    interface Interactor {
        fun fetchRecipeList(onSuccess: (List<Recipe>) -> Unit, onFailed: (Throwable) -> Unit)
    }

    interface Presenter {
        fun onRecipeListRequested()
        fun onRecipeDetailRequested(recipeId: String, recipeName: String)
    }

    interface Routing {
        fun navigateRecipeDetail(recipeId: String, recipeName: String)
    }

    data class Recipe(
        val id: String,
        val title: String,
        val imagePath: String,
        val steps: String,
        val authorName: String
    )
}