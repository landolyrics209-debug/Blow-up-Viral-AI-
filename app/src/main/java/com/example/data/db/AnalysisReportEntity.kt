package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analysis_reports")
data class AnalysisReportEntity(
    @PrimaryKey val id: String,
    val userEmail: String,
    val platform: String,
    val urlOrHandle: String,
    val viralScore: Int,
    val hookAnalysisJson: String,
    val retentionSeoJson: String,
    val audioHashtagsJson: String,
    val timestamp: Long
)
