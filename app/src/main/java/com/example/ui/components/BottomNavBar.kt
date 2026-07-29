package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

enum class NavTab(val title: String, val icon: String) {
    HOME("Accueil", "🏠"),
    TRENDS("Tendances", "🔥"),
    SCRIPTS("Scripts", "⚡"),
    PROFILE("Profil", "👤"),
    ADMIN("Admin", "🛡️")
}

@Composable
fun BottomNavBar(
    selectedTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    showAdminTab: Boolean,
    modifier: Modifier = Modifier
) {
    val visibleTabs = if (showAdminTab) NavTab.values() else NavTab.values().filter { it != NavTab.ADMIN }.toTypedArray()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        color = DarkSurface,
        shadowElevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            visibleTabs.forEach { tab ->
                val isSelected = selectedTab == tab
                val bgColor = if (isSelected) IndigoNeon.copy(alpha = 0.2f) else Color.Transparent
                val contentColor = if (isSelected) CyanNeon else TextSecondary

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgColor)
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = tab.icon,
                        fontSize = 18.sp
                    )
                    Text(
                        text = tab.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = contentColor
                    )
                }
            }
        }
    }
}
