package com.cookpad.android.minicookpad.datastore

import com.cookpad.android.minicookpad.datasource.UserEntity

interface UserDataStore {
    fun currentUser(): UserEntity?

    fun fetch(name: String, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit)

    fun register(user: UserEntity, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit)
}