package com.example.profilekot.viewModels

import android.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profilekot.App
import com.example.profilekot.Constants
import com.example.profilekot.MainActivity
import com.example.profilekot.R
import com.example.profilekot.models.profile.ProfileModel
import com.example.profilekot.services.profile.ProfileService
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddEditViewModel : ViewModel() {
    private lateinit var _profile: ProfileModel

    private val _image = MutableLiveData<String>()
    val image: MutableLiveData<String> = _image

    private val _nickname = MutableLiveData<String>()
    val nickname: MutableLiveData<String> = _nickname

    private val _name = MutableLiveData<String>()
    val name: MutableLiveData<String> = _name

    private val _description = MutableLiveData<String>()
    val description: MutableLiveData<String> = _description

    fun initializeProfileById(id: Int){
        viewModelScope.launch {
            _profile = ProfileService.getProfileById(id)
            image.value = _profile.image
            nickname.value = _profile.nickname
            name.value = _profile.name
            description.value = _profile.description
        }
    }

    fun onProfileSaved() : Boolean{
        var result = false

        if (!hasEmptyFields()){
            if (!this::_profile.isInitialized){
                _profile = createProfile()
            }
            else{
                editProfile()
            }

            viewModelScope.launch {
                ProfileService.saveProfile(_profile)
            }

            result = true
        }
        else{
            showErrorAlert(App.getContext().getString(R.string.NickNameOrNameFieldIsEmpty))
        }

        return result
    }

    private fun hasEmptyFields() : Boolean{
        return nickname.value.isNullOrBlank() || name.value.isNullOrBlank()
    }

    private fun createProfile() : ProfileModel{
        return ProfileModel(
            image = image.value,
            nickname = nickname.value.toString(),
            name = name.value.toString(),
            description = description.value.toString(),
            dateTime = SimpleDateFormat(Constants.DATE_OUTPUT_PATTERN).format(Date()).toString(),
            userId = 0
        )
    }

    private fun editProfile() {
        _profile.image = image.value
        _profile.nickname = nickname.value.toString()
        _profile.name = name.value.toString()
        _profile.description = description.value.toString()
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