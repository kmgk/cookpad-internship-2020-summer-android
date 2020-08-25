package com.cookpad.android.minicookpad.datasource

interface LocalUserDataSource {
    fun fetch(): UserEntity?

    fun save(user: UserEntity, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit)
}