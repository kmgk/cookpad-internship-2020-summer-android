package com.cookpad.android.minicookpad.recipelist

import com.cookpad.android.minicookpad.datasource.RecipeEntity
import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class RecipeEntityListInteractorTest {
    lateinit var recipeDataSource: RecipeDataSource

    lateinit var interactor: RecipeListInteractor

    @Before
    fun setup() {
        recipeDataSource = mock()
        interactor = RecipeListInteractor(recipeDataSource)
    }

    @Test
    fun verifyFetchRecipeListSuccess() {
        // given
        val onSuccess: (List<RecipeListContract.Recipe>) -> Unit = mock()
        val recipeList = listOf(
            RecipeEntity(
                id = "xxx",
                title = "おいしいきゅうりの塩もみ",
                imagePath = "images/recipe.png",
                steps = listOf("きゅうりを切る", "塩をまく", "もむ"),
                authorName = "クックパッド味"
            )
        )
        whenever(recipeDataSource.fetchAll(any(), any())).then {
            (it.arguments[0] as (List<RecipeEntity>) -> Unit).invoke(recipeList)
        }

        // when
        interactor.fetchRecipeList(onSuccess, {})

        // then
        val argumentCaptor = argumentCaptor<List<RecipeListContract.Recipe>>()
        verify(recipeDataSource).fetchAll(any(), any())
        verify(onSuccess).invoke(argumentCaptor.capture())
        argumentCaptor.firstValue.first().also {
            assertThat(it.id).isEqualTo("xxx")
            assertThat(it.title).isEqualTo("おいしいきゅうりの塩もみ")
            assertThat(it.imagePath).isEqualTo("images/recipe.png")
            assertThat(it.steps).isEqualTo("きゅうりを切る、塩をまく、もむ")
            assertThat(it.authorName).isEqualTo("クックパッド味")
        }
    }

    @Test
    fun verifyFetchRecipeListError() {
        // given
        val onFailed: (Throwable) -> Unit = mock()

        whenever(recipeDataSource.fetchAll(any(), any())).then {
            (it.arguments[1] as (Throwable) -> Unit).invoke(Throwable())
        }

        // when
        interactor.fetchRecipeList({}, onFailed)

        // then
        val argumentCaptor = argumentCaptor<Throwable>()
        verify(recipeDataSource).fetchAll(any(), any())
        verify(onFailed).invoke(argumentCaptor.capture())
    }
}