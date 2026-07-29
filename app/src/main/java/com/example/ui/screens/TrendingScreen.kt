package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TrendingTopic
import com.example.ui.MainViewModel
import com.example.ui.components.NeonCard
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.PinkNeon
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletNeon

@Composable
fun TrendingScreen(
    viewModel: MainViewModel,
    onNavigateToHome: () -> Unit
) {
    val selectedCategory by viewModel.selectedTrendingCategory.collectAsState()
    val allTrends = viewModel.repository.getTrendingRadarTopics()

    val categories = listOf("All", "Business", "Tech", "Comedy", "Gaming", "Lifestyle")

    val filteredTrends = if (selectedCategory == "All") {
        allTrends
    } else {
        allTrends.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "🔥 RADAR DES TENDANCES VIRALES",
                        fontSize = 19.sp,
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
                            text = "LIVE 2026",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Formats, audios et défis en pleine explosion sur TikTok et Facebook Reels.",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        // Category Filter Pills
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { cat ->
                    val isSelected = cat == selectedCategory
                    val bgColor = if (isSelected) IndigoNeon else DarkSurfaceVariant
                    val textColor = if (isSelected) TextPrimary else TextSecondary

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(bgColor)
                            .clickable { viewModel.onTrendingCategorySelected(cat) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = cat,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = textColor
                        )
                    }
                }
            }
        }

        // Trend Topic Cards
        items(filteredTrends) { topic ->
            TrendingTopicCard(
                topic = topic,
                onAnalyze = {
                    viewModel.analyzeTrendTopic(topic)
                    onNavigateToHome()
                }
            )
        }
    }
}

@Composable
fun TrendingTopicCard(
    topic: TrendingTopic,
    onAnalyze: () -> Unit
) {
    NeonCard(borderColor = VioletNeon) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(IndigoNeon.copy(alpha = 0.3f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${topic.category} • ${topic.platform}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanNeon
                    )
                }

                Text(
                    text = topic.viralGrowth,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = SuccessGreen
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = topic.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = topic.description,
                fontSize = 12.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "🎵 Audio Tendance: ${topic.trendingAudio}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PinkNeon
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Exemples de Hooks Viraux:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary
            )

            topic.exampleHooks.forEach { hook ->
                Text(
                    text = " • \"$hook\"",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onAnalyze,
                colors = ButtonDefaults.buttonColors(containerColor = IndigoNeon),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "⚡ Générer un Script Basé sur cette Tendance",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
