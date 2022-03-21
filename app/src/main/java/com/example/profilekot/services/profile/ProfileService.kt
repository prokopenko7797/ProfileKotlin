package com.example.profilekot.services.profile

import android.content.Context
import com.example.profilekot.App
import com.example.profilekot.Constants
import com.example.profilekot.models.profile.ProfileModel
import com.example.profilekot.services.repository.RepositoryService

object ProfileService {
    private val _repositoryService: RepositoryService = RepositoryService.getInstance(App.getContext())
    private val _preferences = App.getContext().getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)

    suspend fun saveProfile(profile: ProfileModel){
        if (profile.userId == 0){
            profile.userId = _preferences.getInt(Constants.USER_ID, 0)
        }

        if (profile.id == 0){
            _repositoryService.profileDao.insert(profile)
        }
        else {
            _repositoryService.profileDao.update(profile)
        }
    }

    suspend fun deleteProfile(profile: ProfileModel){
        _repositoryService.profileDao.delete(profile)
    }

    suspend fun getUserProfiles(): List<ProfileModel>{
        val userId = _preferences.getInt(Constants.USER_ID, 0)
        return _repositoryService.profileDao.getAll(userId)
    }

}