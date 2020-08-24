package com.cookpad.android.minicookpad.recipecreate


interface RecipeCreateContract {
    interface View {
        fun renderRecipeCreateSuccessToast()
        fun renderRecipeCreateFailedToast()
    }

    interface Interactor {
        fun createRecipe(recipe: Recipe, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit)
    }

    interface Presenter {
        fun onRecipeCreated(recipe: Recipe)
    }

    interface Routing {
        fun navigateRecipeList()
    }

    data class Recipe(
        val title: String,
        val imageUri: String,
        val Steps: List<String>
    )
}