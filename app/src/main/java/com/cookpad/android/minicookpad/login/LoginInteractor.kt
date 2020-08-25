package com.cookpad.android.minicookpad.login

import com.cookpad.android.minicookpad.datasource.UserEntity
import com.cookpad.android.minicookpad.datastore.UserDataStore

class LoginInteractor(
    private val userDataStore: UserDataStore
) : LoginContract.Interactor {
    override fun autoLogin(onSuccess: (LoginContract.User) -> Unit) {
        val currentUser = userDataStore.currentUser()
        if (currentUser != null) {
            onSuccess.invoke(currentUser.translate())
        }
    }

    override fun login(
        user: LoginContract.User,
        onSuccess: () -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        userDataStore.fetch(user.name, {
            onSuccess
        }, onFailed)
    }

    override fun register(
        user: LoginContract.User,
        onSuccess: () -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        userDataStore.register(
            UserEntity(name = user.name),
            onSuccess,
            onFailed
        )
    }

    fun UserEntity.translate(): LoginContract.User = LoginContract.User(
        name = this.name
    )
}