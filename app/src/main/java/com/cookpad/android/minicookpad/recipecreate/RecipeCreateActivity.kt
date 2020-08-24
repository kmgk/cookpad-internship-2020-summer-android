package com.cookpad.android.minicookpad.recipecreate

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cookpad.android.minicookpad.databinding.ActivityRecipeCreateBinding
import com.cookpad.android.minicookpad.datasource.FirebaseImageDataSource
import com.cookpad.android.minicookpad.datasource.FirebaseRecipeDataSource
import com.cookpad.android.minicookpad.datasource.ImageDataSource

class RecipeCreateActivity : AppCompatActivity(), RecipeCreateContract.View {
    private lateinit var binding: ActivityRecipeCreateBinding

    private val viewModel: RecipeCreateViewModel by viewModels()

    lateinit var presenter: RecipeCreateContract.Presenter

    private val launcher: ActivityResultLauncher<Unit> by lazy {
        registerForActivityResult(ImageSelector()) { imageUri ->
            imageUri?.let {
                Glide.with(this)
                    .load(imageUri)
                    .into(binding.image)
                viewModel.updateImageUri(imageUri.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = RecipeCreatePresenter(
            view = this,
            interactor = RecipeCreateInteractor(
                FirebaseImageDataSource(),
                FirebaseRecipeDataSource()
            ),
            routing = RecipeCreateRouting(this)
        )

        binding = ActivityRecipeCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "レシピ作成"
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        binding.image.setOnClickListener {
            launcher.launch(null)
        }

        binding.saveButton.setOnClickListener {
            presenter.onRecipeCreated(createRecipe())
        }
    }

    private fun createRecipe(): RecipeCreateContract.Recipe {
        return RecipeCreateContract.Recipe(
            binding.title.text.toString(),
            viewModel.imageUri.value ?: "",
            listOf(
                binding.step1.text.toString(),
                binding.step2.text.toString(),
                binding.step3.text.toString()
            )
        )
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class ImageSelector : ActivityResultContract<Unit, Uri?>() {
        override fun createIntent(context: Context, input: Unit?): Intent =
            Intent.createChooser(
                Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                },
                "レシピ写真"
            )

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? = intent?.data
    }

    override fun renderRecipeCreateSuccessToast() {
        Toast.makeText(baseContext, "レシピを作成しました", Toast.LENGTH_SHORT).show()
    }

    override fun renderRecipeCreateFailedToast() {
        Toast.makeText(baseContext, "レシピの保存に失敗しました", Toast.LENGTH_SHORT).show()
    }
}