package com.cookpad.android.minicookpad.login

import androidx.navigation.fragment.findNavController

class LoginRouting(
    private val fragment: LoginFragment
) : LoginContract.Routing {
    override fun navigateRecipeList() {
        fragment.findNavController().navigate(LoginFragmentDirections.showRecipeList())
    }
}