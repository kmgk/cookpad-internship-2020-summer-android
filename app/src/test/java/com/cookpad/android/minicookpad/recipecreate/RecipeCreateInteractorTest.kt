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
    fun verifyCreateRecipe() {
        // given
        val recipe = RecipeCreateContract.Recipe(
            title = "title",
            imageUri = "imageUri",
            steps = listOf("step")
        )
        whenever(imageDataSource.saveImage(any(), any(), any())).then {
            (it.arguments[1] as (String) -> Unit).invoke(recipe.imageUri)
        }

        // when
        interactor.createRecipe(recipe, {}, {})

        // then
        val argumentCaptor = argumentCaptor<RecipeEntity>()
        verify(imageDataSource).saveImage(any(), any(), any())
        verify(recipeDataSource).createRecipe(argumentCaptor.capture(), any(), any())
        
        argumentCaptor.firstValue.also {
            assertThat(it.id).isNotEmpty()
            assertThat(it.title).isEqualTo(recipe.title)
            assertThat(it.imagePath).isEqualTo(recipe.imageUri)
            assertThat(it.steps).isEqualTo(recipe.steps)
            assertThat(it.authorName).isEqualTo("クックパッド味")
        }
    }
}