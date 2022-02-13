package com.example.profilekot.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val login: String,
    val password: String,
)