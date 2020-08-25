package com.cookpad.android.minicookpad.recipecreate

import com.cookpad.android.minicookpad.datasource.ImageDataSource
import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.RecipeEntity
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test


class RecipeCreateInteractorTest {
    lateinit var recipeDataSource: RecipeDataSource
    lateinit var imageDataSource: ImageDataSource
    lateinit var interactor: RecipeCreateInteractor

    @Before
    fun setup() {
        recipeDataSource = mock()
        imageDataSource = mock()
        interactor = RecipeCreateInteractor(imageDataSource, recipeDataSource)
    }

    @Test
    fun verifyCreateRecipeSuccess() {
        // given
        val onSuccess: () -> Unit = mock()
        val recipe = RecipeCreateContract.Recipe(
            title = "title",
            imageUri = "imageUri",
            steps = listOf("step")
        )
        whenever(imageDataSource.saveImage(any(), any(), any())).then {
            (it.arguments[1] as (String) -> Unit).invoke(recipe.imageUri)
        }
        whenever(recipeDataSource.createRecipe(any(), any(), any())).then {
            (it.arguments[1] as () -> Unit).invoke()
        }

        // when
        interactor.createRecipe(recipe, onSuccess, {})

        // then
        val argumentCaptor = argumentCaptor<RecipeEntity>()
        verify(imageDataSource).saveImage(any(), any(), any())
        verify(recipeDataSource).createRecipe(argumentCaptor.capture(), any(), any())
        verify(onSuccess).invoke()

        argumentCaptor.firstValue.also {
            assertThat(it.id).isNotEmpty()
            assertThat(it.title).isEqualTo(recipe.title)
            assertThat(it.imagePath).isEqualTo(recipe.imageUri)
            assertThat(it.steps).isEqualTo(recipe.steps)
            assertThat(it.authorName).isEqualTo("クックパッド味")
        }
    }

    @Test
    fun verifyCreateRecipeErrorOnRecipeDataSource() {
        // given
        val onFailed: (Throwable) -> Unit = mock()
        val error = Throwable("error")
        val recipe = RecipeCreateContract.Recipe(
            title = "title",
            imageUri = "imageUri",
            steps = listOf("step")
        )
        whenever(imageDataSource.saveImage(any(), any(), any())).then {
            (it.arguments[1] as (String) -> Unit).invoke(recipe.imageUri)
        }
        whenever(recipeDataSource.createRecipe(any(), any(), any())).then {
            (it.arguments[2] as (Throwable) -> Unit).invoke(error)
        }

        // when
        interactor.createRecipe(recipe, {}, onFailed)

        // then
        val argumentCaptor = argumentCaptor<(Throwable) -> Unit>()
        verify(imageDataSource).saveImage(any(), any(), argumentCaptor.capture())
        verify(recipeDataSource).createRecipe(any(), any(), argumentCaptor.capture())
        verify(onFailed).invoke(error)
    }

    @Test
    fun verifyCreateRecipeErrorOnImageDataSource() {
        // given
        val onFailed: (Throwable) -> Unit = mock()
        val error = Throwable("error")
        val recipe = RecipeCreateContract.Recipe(
            title = "title",
            imageUri = "imageUri",
            steps = listOf("step")
        )
        whenever(imageDataSource.saveImage(any(), any(), any())).then {
            (it.arguments[2] as (Throwable) -> Unit).invoke(error)
        }

        // when
        interactor.createRecipe(recipe, {}, onFailed)

        // then
        val argumentCaptor = argumentCaptor<(Throwable) -> Unit>()
        verify(imageDataSource).saveImage(any(), any(), argumentCaptor.capture())
        verify(recipeDataSource, never()).createRecipe(any(), any(), argumentCaptor.capture())
        verify(onFailed).invoke(error)
    }

}