package com.example.profilekot.models.profile

import androidx.room.*

@Dao
interface ProfileDao {
    @Insert
    suspend fun insert(profile: ProfileModel)

    @Update
    suspend fun update(profile: ProfileModel)

    @Delete
    suspend fun delete(profile: ProfileModel)

    @Query("SELECT * FROM profile WHERE userId = :userId")
    suspend fun getAll(userId: Int) : List<ProfileModel>

    @Query("SELECT * FROM profile WHERE id = :id")
    suspend fun getById(id: Int) : ProfileModel
}