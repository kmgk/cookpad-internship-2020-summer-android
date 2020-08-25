package com.cookpad.android.minicookpad.datasource

interface RemoteUserDataSource {
    fun fetch(name: String, onSuccess: (UserEntity?) -> Unit, onFailed: (Throwable) -> Unit)

    fun save(user: UserEntity, onSuccess: (UserEntity) -> Unit, onFailed: (Throwable) -> Unit)
}