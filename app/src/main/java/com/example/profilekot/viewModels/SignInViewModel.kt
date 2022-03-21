package com.example.profilekot.viewModels

import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.profilekot.MainActivity
import com.example.profilekot.R
import com.example.profilekot.services.autorization.AuthorizationService
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel()
{
    private val _login = MutableLiveData<String>()
    val login: MutableLiveData<String> = _login

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String> = _password

    fun onSignUpClick(view: View){
        view.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    fun onSignInClick(view: View){
        viewModelScope.launch {
            var isAutorized = AuthorizationService.autorize(login.value!!, password.value!!)
            if (isAutorized)
            {
                
            }
            else
            {
                val builder = AlertDialog.Builder(MainActivity.instance)
                builder.setTitle("Error")
                    .setMessage("Wrong login or password")
                    .setPositiveButton("OK") { dialog, which ->
                        dialog.cancel()
                    }.create()

                builder.show()
            }
        }
    }
}