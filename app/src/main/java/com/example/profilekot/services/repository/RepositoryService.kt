package com.example.profilekot.services.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.profilekot.models.profile.ProfileDao
import com.example.profilekot.models.profile.ProfileModel
import com.example.profilekot.models.user.UserDao
import com.example.profilekot.models.user.UserModel

@Database(
    entities = [UserModel::class, ProfileModel::class],
    version = 1,
    exportSchema = false
)
abstract class RepositoryService: RoomDatabase() {

    abstract val userDao: UserDao
    abstract val profileDao: ProfileDao

    companion object{
        @Volatile
        private var _instance: RepositoryService? = null

        fun getInstance(context: Context): RepositoryService {
            synchronized(this){
                var instance = _instance

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RepositoryService::class.java,
                        "sqlite"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    _instance = instance
                }

                return instance
            }
        }
    }
}