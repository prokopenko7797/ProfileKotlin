package com.example.profilekot.services.autorization

import android.content.Context
import com.example.profilekot.App
import com.example.profilekot.Constants
import com.example.profilekot.models.user.UserModel
import com.example.profilekot.services.repository.RepositoryService

object AutorizationService {
    private val _database: RepositoryService = RepositoryService.getInstance(App.getContext())
    private val _preferences = App.getContext().getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)

    val isAuthorized = _preferences.getInt(Constants.USER_ID, 0) != 0

    suspend fun createUser(login:String, password:String): Boolean {
        var result = false

        var user = _database.userDao.getByLogin(login)

        if (user != null)
        {
            var newUser = UserModel(login = login, password = password)
            _database.userDao.insert(newUser)
            result = true
        }

        return result
    }

    suspend fun autorize(login:String, password:String): Boolean {
        var result = false

        var user = _database.userDao.getUser(login, password)

        if (user != null)
        {
            _preferences.edit()
                .putInt(Constants.USER_ID, user.id)
                .apply()
            result = true
        }

        return  result
    }

    fun logout(){
        _preferences.edit()
            .putInt(Constants.USER_ID, 0)
            .apply()
    }
}