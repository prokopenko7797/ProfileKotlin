package com.example.profilekot.models.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var image: String?,
    var nickname: String,
    var name: String,
    var description: String,
    var dateTime: String,
    var userId: Int = 0
)