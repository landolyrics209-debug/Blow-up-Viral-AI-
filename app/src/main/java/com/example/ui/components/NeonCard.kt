package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.VioletNeon

@Composable
fun NeonCard(
    modifier: Modifier = Modifier,
    borderColor: Color = VioletNeon,
    glowElevation: Dp = 8.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val gradientBorder = Brush.horizontalGradient(
        colors = listOf(
            IndigoNeon.copy(alpha = 0.6f),
            borderColor.copy(alpha = 0.8f),
            CyanNeon.copy(alpha = 0.6f)
        )
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = glowElevation,
                shape = RoundedCornerShape(16.dp),
                ambientColor = borderColor.copy(alpha = 0.3f),
                spotColor = borderColor.copy(alpha = 0.5f)
            )
            .clip(RoundedCornerShape(16.dp))
            .border(
                border = BorderStroke(1.dp, gradientBorder),
                shape = RoundedCornerShape(16.dp)
            ),
        color = DarkSurface,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(DarkSurface)
                .padding(16.dp),
            content = content
        )
    }
}
