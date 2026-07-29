package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users ORDER BY createdAt DESC")
    fun getAllUsersFlow(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUser(email: String)
}

@Dao
interface AnalysisReportDao {
    @Query("SELECT * FROM analysis_reports ORDER BY timestamp DESC")
    fun getAllReportsFlow(): Flow<List<AnalysisReportEntity>>

    @Query("SELECT * FROM analysis_reports WHERE userEmail = :email ORDER BY timestamp DESC")
    fun getReportsForUserFlow(email: String): Flow<List<AnalysisReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: AnalysisReportEntity)

    @Query("DELETE FROM analysis_reports WHERE id = :id")
    suspend fun deleteReport(id: String)
}

@Dao
interface SavedScriptDao {
    @Query("SELECT * FROM saved_scripts ORDER BY timestamp DESC")
    fun getAllScriptsFlow(): Flow<List<SavedScriptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScript(script: SavedScriptEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScripts(scripts: List<SavedScriptEntity>)

    @Query("DELETE FROM saved_scripts WHERE id = :id")
    suspend fun deleteScript(id: String)
}

@Dao
interface TrendingTopicDao {
    @Query("SELECT * FROM trending_topics")
    fun getAllTrendingTopicsFlow(): Flow<List<TrendingTopicEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingTopics(topics: List<TrendingTopicEntity>)
}
