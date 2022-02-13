package com.example.profilekot.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.profilekot.R

class SignInViewModel : ViewModel()
{
    private val _isEnabled = MutableLiveData<Boolean>(true)
    val isEnabled : LiveData<Boolean> = _isEnabled

    private val _login = MutableLiveData<String>()
    val login: MutableLiveData<String> = _login

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String> = _password

    fun onSignUpClick(view: View){
        view.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    fun onSignInClick(view: View){
        //view.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }
}