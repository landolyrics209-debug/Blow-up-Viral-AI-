package com.example.data.model

enum class UserRole {
    SUPER_ADMIN,
    STANDARD_MEMBER
}

data class User(
    val email: String,
    val name: String,
    val role: UserRole,
    val isPro: Boolean = false,
    val credits: Int = 10,
    val customApiKey: String? = null
) {
    val isSuperAdmin: Boolean
        get() = role == UserRole.SUPER_ADMIN ||
                email.equals("landolyrics209@gmail.com", ignoreCase = true) ||
                email.equals("camellandoml209@gmail.com", ignoreCase = true)
}

enum class PlatformType(val displayName: String, val iconRes: String) {
    TIKTOK("TikTok 🎵", "ic_tiktok"),
    FACEBOOK("Facebook Reels & Pages 🔵", "ic_facebook")
}

data class HookAnalysis(
    val visualPullScore: Int,
    val visualPullText: String,
    val auditoryPullScore: Int,
    val auditoryPullText: String,
    val triggerKeywords: List<String>,
    val suggestedRewrittenHook: String
)

data class RetentionSeo(
    val pacingRating: String,
    val captionOptimization: String,
    val algorithmFactors: List<String>,
    val strengths: List<String>,
    val weaknesses: List<String>
)

data class AudioHashtagMatrix(
    val trendingAudioName: String,
    val audioViralTier: String,
    val recommendedHashtags: List<String>,
    val targetKeywords: List<String>
)

data class ViralReport(
    val id: String,
    val userEmail: String,
    val platform: PlatformType,
    val urlOrHandle: String,
    val viralScore: Int,
    val hookAnalysis: HookAnalysis,
    val retentionSeo: RetentionSeo,
    val audioHashtagMatrix: AudioHashtagMatrix,
    val timestamp: Long = System.currentTimeMillis()
)

data class VideoScriptConcept(
    val id: String,
    val analysisId: String,
    val conceptTitle: String,
    val conceptTag: String,
    val visualAction: String,
    val spokenScript: String,
    val callToAction: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class TrendingTopic(
    val id: String,
    val category: String,
    val platform: String,
    val title: String,
    val description: String,
    val viralGrowth: String,
    val trendingAudio: String,
    val suggestedHashtags: List<String>,
    val exampleHooks: List<String>
)

data class AdminMetrics(
    val totalUsers: Int,
    val totalAnalyses: Int,
    val estimatedRevenueUsd: Double,
    val activeProUsers: Int
)
