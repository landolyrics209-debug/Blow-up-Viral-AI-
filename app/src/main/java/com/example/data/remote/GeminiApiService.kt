package com.example.data.remote

import com.example.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiApiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    suspend fun generateViralAnalysis(
        platform: String,
        urlOrHandle: String,
        customApiKey: String? = null
    ): String? = withContext(Dispatchers.IO) {
        val apiKey = customApiKey?.takeIf { it.isNotBlank() }
            ?: try { BuildConfig.GEMINI_API_KEY } catch (e: Exception) { "" }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext null
        }

        val prompt = """
            You are BlowUp Viral AI, an elite viral content strategist for $platform.
            Analyze the following video link, channel, or content handle: "$urlOrHandle".
            
            Return a JSON object strictly matching this schema:
            {
              "viralScore": 92,
              "visualPullScore": 88,
              "visualPullText": "High contrast opening frame with rapid movement in first 1.5 seconds.",
              "auditoryPullScore": 95,
              "auditoryPullText": "Trending audio pitch drop paired with bold voiceover overlay.",
              "triggerKeywords": ["SECRET", "NEVER DO THIS", "ALGORITHM HACK"],
              "suggestedRewrittenHook": "If you are still posting $platform videos like this, you are losing 80% of your views in 3 seconds.",
              "pacingRating": "A+ Fast-Cut Pacing",
              "captionOptimization": "Optimal 120-word caption structured with line breaks and curiosity hook.",
              "algorithmFactors": ["First 3s Retention: High", "Watch Time Completion: 78%", "Shares Ratio: Excellent"],
              "strengths": ["Bold visual text overlays", "Immediate problem presentation", "High emotional curiosity"],
              "weaknesses": ["CTA is delayed until end of video", "Hashtag targeting is slightly broad"],
              "trendingAudioName": "Phonk Viral Beat - Bass Boosted #42",
              "audioViralTier": "TIER 1 (Top 1% Velocity)",
              "recommendedHashtags": ["#ViralHacks", "#ContentCreator", "#GrowFast", "#BlowUpAI"],
              "targetKeywords": ["viral strategy", "creator tips", "algorithm boost"]
            }
            Do not include markdown code block ticks in the raw string if possible, return clean json.
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
            }

            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val responseBodyStr = response.body?.string() ?: return@withContext null
                
                val responseObj = JSONObject(responseBodyStr)
                val candidates = responseObj.optJSONArray("candidates") ?: return@withContext null
                if (candidates.length() == 0) return@withContext null
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.optJSONObject("content") ?: return@withContext null
                val parts = content.optJSONArray("parts") ?: return@withContext null
                if (parts.length() == 0) return@withContext null
                val text = parts.getJSONObject(0).optString("text", "")
                
                // Clean markdown JSON ticks if present
                val cleanedText = text
                    .replace("```json", "")
                    .replace("```", "")
                    .trim()

                return@withContext cleanedText
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }
}
