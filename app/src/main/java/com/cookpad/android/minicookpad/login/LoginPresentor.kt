package com.cookpad.android.minicookpad.login

import androidx.navigation.fragment.findNavController

class LoginPresentor(
    val view: LoginFragment,
    val interactor: LoginInteractor,
    val routing: LoginRouting
) : LoginContract.Presentor {
    override fun onAutoLogin() {
        interactor.autoLogin {
            view.renderSuccess("${it.name}でログインしました")
            routing.navigateRecipeList()
        }
    }

    override fun onLogin(
        user: LoginContract.User
    ) {
        interactor.login(
            user,
            onSuccess = {
                view.renderSuccess("${user.name}でログインしました")
                routing.navigateRecipeList()
            },
            onFailed = {
                view.renderError(it, "ログインに失敗しました")
            }
        )
    }

    override fun onRegister(
        user: LoginContract.User
    ) {
        interactor.register(
            user,
            onSuccess = {
                view.renderSuccess("登録が完了しました")
                routing.navigateRecipeList()
            },
            onFailed = {
                view.renderError(it, "登録に失敗しました")
            }
        )
    }
}