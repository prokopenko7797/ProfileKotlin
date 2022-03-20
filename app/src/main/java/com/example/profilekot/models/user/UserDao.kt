package com.example.profilekot.models.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserModel)

    @Query("SELECT * FROM user WHERE login = :login")
    suspend fun getByLogin(login: String): UserModel?

    @Query("SELECT * FROM user WHERE login = :login AND password = :password")
    suspend fun getUser(login: String, password: String): UserModel?
}