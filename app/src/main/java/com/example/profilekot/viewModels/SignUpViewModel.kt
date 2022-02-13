package com.example.profilekot.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.profilekot.R

class SignUpViewModel : ViewModel()
{
    private val _isEnabled = MutableLiveData<Boolean>(true)
    val isEnabled : LiveData<Boolean> = _isEnabled

    private val _login = MutableLiveData<String>()
    val login: MutableLiveData<String> = _login

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: MutableLiveData<String> = _confirmPassword

    fun onSignUpClick(view: View){
        view.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }
}