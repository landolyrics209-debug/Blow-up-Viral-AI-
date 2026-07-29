package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val name: String,
    val role: String,
    val isPro: Boolean,
    val credits: Int,
    val customApiKey: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
