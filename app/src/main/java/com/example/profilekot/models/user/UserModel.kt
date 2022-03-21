package com.example.profilekot.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var login: String,
    var password: String,
)