package com.example.profilekot.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.profilekot.App
import com.example.profilekot.R
import com.example.profilekot.models.profile.ProfileModel
import com.example.profilekot.services.autorization.AuthorizationService
import com.example.profilekot.services.profile.ProfileService
import com.example.profilekot.views.MainListFragmentDirections
import kotlinx.coroutines.launch

class MainListViewModel : ViewModel() {
    private var _profiles = arrayListOf<ProfileModel>()
    var profiles = MutableLiveData<ArrayList<ProfileModel>>()

    fun onGetProfiles(){
        viewModelScope.launch {
            _profiles = ArrayList(ProfileService.getUserProfiles())
            profiles.value = _profiles
        }
    }

    fun onAddProfile(view: View){
        view.findNavController().navigate(
            MainListFragmentDirections.actionMainListFragmentToAddEditFragment(App.getContext().getString(R.string.AddProfile), 0))
    }

    fun onDeleteProfile(profile: ProfileModel){
        viewModelScope.launch {
            ProfileService.deleteProfile(profile)
        }
    }

    fun onLogout(){
        AuthorizationService.logout()
    }
}