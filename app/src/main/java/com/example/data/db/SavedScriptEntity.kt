package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_scripts")
data class SavedScriptEntity(
    @PrimaryKey val id: String,
    val analysisId: String,
    val conceptTitle: String,
    val conceptTag: String,
    val visualAction: String,
    val spokenScript: String,
    val callToAction: String,
    val timestamp: Long
)
