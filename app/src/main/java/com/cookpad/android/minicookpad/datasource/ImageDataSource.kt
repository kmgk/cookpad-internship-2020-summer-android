package com.cookpad.android.minicookpad.datasource

interface ImageDataSource {
    fun saveImage(uri: String, onSuccess: (String) -> Unit, onFailed: (Throwable) -> Unit)
}