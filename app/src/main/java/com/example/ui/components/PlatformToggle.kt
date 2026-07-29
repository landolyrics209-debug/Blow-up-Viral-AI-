package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PlatformType
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.FacebookBlue
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TikTokRed

@Composable
fun PlatformToggle(
    selectedPlatform: PlatformType,
    onPlatformSelected: (PlatformType) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(DarkSurfaceVariant)
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // TikTok Toggle Button
            val isTikTokSelected = selectedPlatform == PlatformType.TIKTOK
            val tikTokBg by animateColorAsState(
                targetValue = if (isTikTokSelected) TikTokRed else DarkSurfaceVariant,
                label = "tikTokBg"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(22.dp))
                    .background(tikTokBg)
                    .clickable { onPlatformSelected(PlatformType.TIKTOK) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎵 TikTok",
                    fontSize = 15.sp,
                    fontWeight = if (isTikTokSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isTikTokSelected) TextPrimary else TextSecondary
                )
            }

            // Facebook Toggle Button
            val isFacebookSelected = selectedPlatform == PlatformType.FACEBOOK
            val facebookBg by animateColorAsState(
                targetValue = if (isFacebookSelected) FacebookBlue else DarkSurfaceVariant,
                label = "facebookBg"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(22.dp))
                    .background(facebookBg)
                    .clickable { onPlatformSelected(PlatformType.FACEBOOK) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔵 Facebook Reels & Pages",
                    fontSize = 13.sp,
                    fontWeight = if (isFacebookSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isFacebookSelected) TextPrimary else TextSecondary
                )
            }
        }
    }
}
