package com.cookpad.android.minicookpad.recipecreate

class RecipeCreatePresenter(
    private val view: RecipeCreateActivity,
    private val interactor: RecipeCreateContract.Interactor,
    private val routing: RecipeCreateContract.Routing
) : RecipeCreateContract.Presenter {
    override fun onRecipeCreated(recipe: RecipeCreateContract.Recipe) {
        interactor.createRecipe(recipe, onSuccess = {
            view.renderRecipeCreateSuccessToast()
            routing.navigateRecipeList()
        }, onFailed = { view.renderRecipeCreateFailedToast() })
    }
}