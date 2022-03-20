package com.example.profilekot.viewModels

import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.profilekot.App
import com.example.profilekot.Constants
import com.example.profilekot.MainActivity
import com.example.profilekot.R
import com.example.profilekot.services.autorization.AutorizationService
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel()
{
    private val _login = MutableLiveData<String>()
    val login: MutableLiveData<String> = _login

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: MutableLiveData<String> = _confirmPassword

    fun onSignUpClick(view: View){
        viewModelScope.launch {
            if (!hasValidLogin())
            {
                if (!hasValidPassword())
                {
                    if (password != confirmPassword) {
                        var isUserCreated =
                            AutorizationService.createUser(login.value!!, password.value!!)

                        if (isUserCreated) {
                            view.findNavController().previousBackStackEntry?.savedStateHandle?.set(
                                Constants.LOGIN,
                                login.value
                            )
                            view.findNavController().popBackStack()
                        } else {
                            val builder = AlertDialog.Builder(MainActivity.instance)
                            builder.setTitle("Error")
                                .setMessage("User already exist")
                                .setPositiveButton("OK") { dialog, which ->
                                    dialog.cancel()
                                }.create()

                            builder.show()
                        }
                    }
                    else
                    {
                        showErrorAlert(App.getContext().getString(R.string.PasswordsFieldsNotMatches))
                    }
                }
                else
                {
                    showErrorAlert(App.getContext().getString(R.string.PasswordIsInvalid))
                }
            }
            else
            {
                showErrorAlert(App.getContext().getString(R.string.LoginIsInvalid))
            }
        }
    }

    private fun hasValidLogin() : Boolean{
        val loginRegex = Regex("^[A-Za-z][A-Za-z\\d]{3,15}\$")
        return loginRegex.containsMatchIn(login.value.toString())
    }

    private fun hasValidPassword() : Boolean{
        val passRegex = Regex("^[A-Z](?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{7,15}\$")
        return passRegex.containsMatchIn(password.value.toString())
    }

    private fun showErrorAlert(message: String){
        val builder = AlertDialog.Builder(MainActivity.instance)
        builder.setTitle(App.getContext().getString(R.string.Alert))
            .setMessage(message)
            .setPositiveButton(App.getContext().getString(R.string.OK)) { dialog, which ->
                dialog.cancel()
            }.create()

        builder.show()
    }
}