package com.cookpad.android.minicookpad.login


interface LoginContract {
    interface View {
        fun renderSuccess(message: String)

        fun renderError(exception: Throwable, message: String)
    }

    interface Interactor {
        fun autoLogin(onSuccess: (User) -> Unit)

        fun login(user: User, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit)

        fun register(user: User, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit)
    }

    interface Presentor {
        fun onAutoLogin()

        fun onLogin(user: User)

        fun onRegister(user: User)
    }

    interface Routing {
        fun navigateRecipeList()
    }

    data class User(
        val name: String
    )
}