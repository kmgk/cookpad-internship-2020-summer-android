package com.cookpad.android.minicookpad.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cookpad.android.minicookpad.databinding.FragmentLoginBinding
import com.cookpad.android.minicookpad.datasource.FirebaseUserDataSource
import com.cookpad.android.minicookpad.datasource.SharedPreferenceUserDataSource
import com.cookpad.android.minicookpad.datastore.UserDataStoreImpl

class LoginFragment : Fragment(), LoginContract.View {
    private lateinit var binding: FragmentLoginBinding
    lateinit var presentor: LoginContract.Presentor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        presentor = LoginPresentor(
            view = this,
            interactor = LoginInteractor(
                UserDataStoreImpl(
                    SharedPreferenceUserDataSource(requireContext()),
                    FirebaseUserDataSource()
                )
            ),
            routing = LoginRouting(this)
        )

        presentor.onAutoLogin()

        binding.loginButton.setOnClickListener {
            val userName = binding.userName.toString()
            presentor.onLogin(LoginContract.User(userName))
        }

        binding.registerButton.setOnClickListener {
            val userName = binding.userName.toString()
            presentor.onRegister(LoginContract.User(userName))
        }
        return binding.root
    }

    override fun renderSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun renderError(exception: Throwable, message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
