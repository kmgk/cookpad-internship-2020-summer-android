package com.cookpad.android.minicookpad.recipelist

import androidx.navigation.fragment.findNavController

class RecipeListRouting(
    private val fragment: RecipeListFragment
) : RecipeListContract.Routing {
    override fun navigateRecipeDetail(recipeId: String, recipeName: String) {
        fragment.findNavController()
            .navigate(RecipeListFragmentDirections.showRecipeDetail(recipeId, recipeName))
    }
}