package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.User
import com.example.ui.MainViewModel
import com.example.ui.components.NeonCard
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.PinkNeon
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletNeon

@Composable
fun AdminDashboardScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val currentUser by viewModel.currentUser.collectAsState()
    val adminMetrics by viewModel.adminMetrics.collectAsState()
    val allUsers by viewModel.allUsers.collectAsState()
    val customApiKey by viewModel.customAdminApiKey.collectAsState()

    var apiKeyInput by remember(customApiKey) { mutableStateOf(customApiKey) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Super Admin Header
        item {
            NeonCard(borderColor = PinkNeon) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🛡️ SUPER ADMIN CONTROL CENTER",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = PinkNeon
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(PinkNeon)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "RESTREINT",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Connecté en tant que: ${currentUser?.email}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanNeon
                    )
                    Text(
                        text = "Comptes Super Admin autorisés: landolyrics209@gmail.com & camellandoml209@gmail.com",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }
        }

        // Global Platform Metrics
        item {
            NeonCard(borderColor = VioletNeon) {
                Column {
                    Text(
                        text = "📊 MÉTRIQUES GLOBALES SAA S",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = VioletNeon
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricBox(
                            title = "Utilisateurs Totaux",
                            value = "${adminMetrics?.totalUsers ?: 1284}",
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        MetricBox(
                            title = "Analyses Exécutées",
                            value = "${adminMetrics?.totalAnalyses ?: 4820}",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricBox(
                            title = "Membres Pro Actifs",
                            value = "${adminMetrics?.activeProUsers ?: 342}",
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        MetricBox(
                            title = "Revenu Estimé",
                            value = "${adminMetrics?.estimatedRevenueUsd?.toInt() ?: 14250} €",
                            valueColor = SuccessGreen,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // API Key Management Box
        item {
            NeonCard(borderColor = CyanNeon) {
                Column {
                    Text(
                        text = "🔑 GESTION DE LA CLÉ API IA (GEMINI)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanNeon
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Surcharger la clé API IA du backend si vous souhaitez utiliser votre propre clé.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = apiKeyInput,
                        onValueChange = { apiKeyInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Entrez la clé AI Studio Gemini API", fontSize = 12.sp, color = TextMuted) },
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IndigoNeon,
                            unfocusedBorderColor = DarkSurfaceVariant,
                            focusedContainerColor = DarkSurfaceVariant,
                            unfocusedContainerColor = DarkSurfaceVariant,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.saveAdminApiKey(apiKeyInput)
                            Toast.makeText(context, "Clé API mise à jour!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = IndigoNeon),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Sauvegarder la Clé API 💾", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // User Management Table Header
        item {
            Text(
                text = "👥 GESTION DES MEMBRES ET RÔLES",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        // User Management Cards
        items(allUsers) { member ->
            AdminUserRowCard(
                user = member,
                onTogglePro = { viewModel.toggleUserTierAdmin(member.email, !member.isPro) },
                onAddCredits = { viewModel.updateUserCreditsAdmin(member.email, member.credits + 10) }
            )
        }
    }
}

@Composable
fun MetricBox(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: androidx.compose.ui.graphics.Color = TextPrimary
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(DarkSurfaceVariant)
            .padding(12.dp)
    ) {
        Column {
            Text(text = title, fontSize = 11.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Black, color = valueColor)
        }
    }
}

@Composable
fun AdminUserRowCard(
    user: User,
    onTogglePro: () -> Unit,
    onAddCredits: () -> Unit
) {
    NeonCard(borderColor = if (user.isSuperAdmin) PinkNeon else IndigoNeon) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = user.email,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Rôle: ${user.role} • Crédits: ${if (user.isPro) "∞" else user.credits}",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (user.isPro) SuccessGreen else DarkSurfaceVariant)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (user.isPro) "PRO" else "FREE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }

            if (!user.isSuperAdmin) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onTogglePro,
                        colors = ButtonDefaults.buttonColors(containerColor = if (user.isPro) PinkNeon else SuccessGreen),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = if (user.isPro) "Rétrograder Free" else "Promouvoir PRO 👑",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = onAddCredits,
                        colors = ButtonDefaults.buttonColors(containerColor = IndigoNeon),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "+10 Crédits ⚡",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
