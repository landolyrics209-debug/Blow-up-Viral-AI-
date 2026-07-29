package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.PinkNeon
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletNeon

@Composable
fun CircularScoreGauge(
    score: Int,
    modifier: Modifier = Modifier,
    size: Dp = 140.dp,
    strokeWidth: Dp = 12.dp
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(score) {
        animatedProgress.animateTo(
            targetValue = score / 100f,
            animationSpec = tween(durationMillis = 1200)
        )
    }

    val gradient = Brush.sweepGradient(
        colors = listOf(
            IndigoNeon,
            VioletNeon,
            PinkNeon,
            CyanNeon,
            IndigoNeon
        )
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            // Background ring
            drawCircle(
                color = DarkSurfaceVariant,
                radius = (size.toPx() - strokeWidth.toPx()) / 2,
                style = Stroke(width = strokeWidth.toPx())
            )

            // Animated progress ring
            drawArc(
                brush = gradient,
                startAngle = -90f,
                sweepAngle = animatedProgress.value * 360f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${(animatedProgress.value * 100).toInt()}%",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary
            )
            Text(
                text = "VIRAL SCORE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 1.sp
            )
        }
    }
}
