package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_topics")
data class TrendingTopicEntity(
    @PrimaryKey val id: String,
    val category: String,
    val platform: String,
    val title: String,
    val description: String,
    val viralGrowth: String,
    val trendingAudio: String,
    val suggestedHashtagsJson: String,
    val exampleHooksJson: String
)
