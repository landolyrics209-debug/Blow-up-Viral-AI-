package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.VioletNeon

@Composable
fun GlowingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(
            IndigoNeon,
            VioletNeon,
            CyanNeon
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = if (enabled) 12.dp else 2.dp,
                shape = RoundedCornerShape(14.dp),
                ambientColor = IndigoNeon.copy(alpha = glowAlpha),
                spotColor = VioletNeon.copy(alpha = glowAlpha)
            )
            .clip(RoundedCornerShape(14.dp))
            .background(if (enabled) buttonGradient else Brush.horizontalGradient(listOf(Color.Gray, Color.DarkGray)))
            .clickable(enabled = enabled && !isLoading, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = TextPrimary,
                    strokeWidth = 2.5.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "ANALYSE EN COURS (IA)... ✨",
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = text,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
