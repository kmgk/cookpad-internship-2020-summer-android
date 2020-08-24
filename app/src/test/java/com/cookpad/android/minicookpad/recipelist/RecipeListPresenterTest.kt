package com.cookpad.android.minicookpad.recipelist

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class RecipeListPresenterTest {
    lateinit var view: RecipeListContract.View
    lateinit var interactor: RecipeListContract.Interactor
    lateinit var routing: RecipeListContract.Routing
    lateinit var presenter: RecipeListPresenter

    @Before
    fun setup() {
        view = mock()
        routing = mock()
        interactor = mock()
        presenter = RecipeListPresenter(view, interactor, routing)
    }

    @Test
    fun verifyOnRecipeListRequestedSuccess() {
        // given
        val recipeList = listOf(
            RecipeListContract.Recipe(
                id = "xxx",
                title = "おいしいきゅうりの塩もみ",
                imagePath = "images/recipe.png",
                steps = "きゅうりを切る、塩をまく、もむ",
                authorName = "クックパッド味"
            )
        )
        whenever(interactor.fetchRecipeList(any(), any())).then {
            (it.arguments[0] as (List<RecipeListContract.Recipe>) -> Unit).invoke(recipeList)
        }

        // when
        presenter.onRecipeListRequested()

        // then
        val argumentCaptor = argumentCaptor<List<RecipeListContract.Recipe>>()
        verify(interactor).fetchRecipeList(any(), any())
        verify(view).renderRecipeList(argumentCaptor.capture())
        argumentCaptor.firstValue.also {
            assertThat(it).isEqualTo(recipeList)
        }
    }
}
