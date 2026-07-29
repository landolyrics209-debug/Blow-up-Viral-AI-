package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.PlatformType
import com.example.ui.MainViewModel
import com.example.ui.components.CircularScoreGauge
import com.example.ui.components.GlowingButton
import com.example.ui.components.NeonCard
import com.example.ui.components.PlatformToggle
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.PinkNeon
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletNeon

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val selectedPlatform by viewModel.selectedPlatform.collectAsState()
    val inputUrl by viewModel.inputUrlOrHandle.collectAsState()
    val isAnalyzing by viewModel.isAnalyzing.collectAsState()
    val analysisError by viewModel.analysisError.collectAsState()
    val report by viewModel.currentReport.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_banner),
                    contentDescription = "BlowUp Viral Hero",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    DarkBackground.copy(alpha = 0.3f),
                                    DarkBackground.copy(alpha = 0.9f)
                                )
                            )
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "BLOWUP VIRAL AI",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PinkNeon)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "2026 PRO",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                        }
                        Text(
                            text = "Boostez vos TikToks & Reels Facebook grâce à l'Analyse IA Approfondie.",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        // Section 1: Dual-Platform Switcher
        item {
            Column {
                Text(
                    text = "1. SÉLECTIONNER LA PLATEFORME",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyanNeon,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                PlatformToggle(
                    selectedPlatform = selectedPlatform,
                    onPlatformSelected = { viewModel.onPlatformSelected(it) }
                )
            }
        }

        // Section 2: Smart Input Engine
        item {
            Column {
                Text(
                    text = "2. LIEN VIDÉO / PROFIL / NOM D'UTILISATEUR",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyanNeon,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = inputUrl,
                    onValueChange = { viewModel.onInputUrlChanged(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = if (selectedPlatform == PlatformType.TIKTOK)
                                "Ex: https://tiktok.com/@utilisateur/video/123 ou @utilisateur"
                            else
                                "Ex: https://facebook.com/reel/123 ou Nom de Page FB",
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndigoNeon,
                        unfocusedBorderColor = DarkSurfaceVariant,
                        focusedContainerColor = DarkSurface,
                        unfocusedContainerColor = DarkSurface,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { viewModel.runViralAnalysis() })
                )

                if (analysisError != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "⚠️ $analysisError",
                        fontSize = 12.sp,
                        color = ErrorRed
                    )
                }
            }
        }

        // Section 3: Prominent Action Button
        item {
            GlowingButton(
                text = "GÉNÉRER L'ANALYSE VIRALE ✨",
                onClick = { viewModel.runViralAnalysis() },
                isLoading = isAnalyzing
            )
        }

        // Section 4: Deep Viral Analytics Report Output
        if (report != null) {
            val rep = report!!
            item {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                        // Viral Score Card
                        NeonCard(borderColor = VioletNeon) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "📊 SCORE POTENTIEL VIRAL",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VioletNeon,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    CircularScoreGauge(score = rep.viralScore)

                                    Column(modifier = Modifier.padding(start = 12.dp)) {
                                        Text(
                                            text = when {
                                                rep.viralScore >= 90 -> "🔥 NIVEAU VIRAL SUPRÊME"
                                                rep.viralScore >= 80 -> "⚡ TRÈS FORT POTENTIEL"
                                                else -> "📈 POTENTIEL MODÉRÉ"
                                            },
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (rep.viralScore >= 85) SuccessGreen else CyanNeon
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Cible: ${rep.platform.displayName}",
                                            fontSize = 12.sp,
                                            color = TextSecondary
                                        )
                                        Text(
                                            text = "Auteur: ${rep.urlOrHandle}",
                                            fontSize = 12.sp,
                                            color = TextMuted
                                        )
                                    }
                                }
                            }
                        }

                        // Hook Analyzer (First 3 Seconds)
                        NeonCard(borderColor = CyanNeon) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "🪝 HOOK ANALYZER (3 PREMIÈRES SECONDES)",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CyanNeon
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Visuel Pull: ${rep.hookAnalysis.visualPullScore}/100",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                        Text(
                                            text = rep.hookAnalysis.visualPullText,
                                            fontSize = 12.sp,
                                            color = TextSecondary
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Auditiif Pull: ${rep.hookAnalysis.auditoryPullScore}/100",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                        Text(
                                            text = rep.hookAnalysis.auditoryPullText,
                                            fontSize = 12.sp,
                                            color = TextSecondary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Mots Clés Déclencheurs (Triggers):",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    items(rep.hookAnalysis.triggerKeywords) { kw ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(IndigoNeon.copy(alpha = 0.3f))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "⚡ $kw",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextPrimary
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(DarkSurfaceVariant)
                                        .padding(12.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "💡 Hook Réécrit Suggéré par l'IA:",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PinkNeon
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "\"${rep.hookAnalysis.suggestedRewrittenHook}\"",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = TextPrimary
                                        )
                                    }
                                }
                            }
                        }

                        // Retention & SEO Diagnostic
                        NeonCard(borderColor = IndigoNeon) {
                            Column {
                                Text(
                                    text = "📈 RÉTENTION & DIAGNOSTIC SEO ALGORITHME",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = IndigoNeon
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "Rythme & Pacing: ${rep.retentionSeo.pacingRating}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Optimisation Légende: ${rep.retentionSeo.captionOptimization}",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Points Forts:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                                rep.retentionSeo.strengths.forEach { s ->
                                    Text(text = "  • $s", fontSize = 12.sp, color = TextPrimary)
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Axes d'Amélioration:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ErrorRed
                                )
                                rep.retentionSeo.weaknesses.forEach { w ->
                                    Text(text = "  • $w", fontSize = 12.sp, color = TextPrimary)
                                }
                            }
                        }

                        // Audio & Hashtag Matrix
                        NeonCard(borderColor = PinkNeon) {
                            Column {
                                Text(
                                    text = "🎵 MATRIX AUDIO & HASHTAGS VIRAUX",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PinkNeon
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "Son Tendance Détecté: ${rep.audioHashtagMatrix.trendingAudioName}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Vélocité: ${rep.audioHashtagMatrix.audioViralTier}",
                                    fontSize = 12.sp,
                                    color = CyanNeon
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Hashtags Recommandés:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextSecondary
                                    )

                                    Button(
                                        onClick = {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = ClipData.newPlainText("Hashtags", rep.audioHashtagMatrix.recommendedHashtags.joinToString(" "))
                                            clipboard.setPrimaryClip(clip)
                                            Toast.makeText(context, "Hashtags copiés!", Toast.LENGTH_SHORT).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = IndigoNeon),
                                        modifier = Modifier.height(32.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(text = "Copier All 📋", fontSize = 11.sp)
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = rep.audioHashtagMatrix.recommendedHashtags.joinToString(" "),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CyanNeon
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
