package com.example.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel
import com.example.ui.components.GlowingButton
import com.example.ui.components.NeonCard
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.PinkNeon
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletNeon

@Composable
fun ProfileScreen(viewModel: MainViewModel) {
    val currentUser by viewModel.currentUser.collectAsState()
    val isUpgradeModalOpen by viewModel.isUpgradeModalOpen.collectAsState()

    val user = currentUser

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Header Card
        item {
            NeonCard(borderColor = VioletNeon) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(IndigoNeon),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (user?.name?.take(1) ?: "U").uppercase(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = user?.name ?: "Utilisateur",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Text(
                        text = user?.email ?: "",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (user?.isPro == true) SuccessGreen else DarkSurfaceVariant)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (user?.isPro == true) "👑 ABONNEMENT PRO" else "MEMBER FREE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        if (user?.isSuperAdmin == true) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PinkNeon)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "🛡️ SUPER ADMIN",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Subscription & Credits Card
        item {
            NeonCard(borderColor = CyanNeon) {
                Column {
                    Text(
                        text = "💳 CRÉDITS & ABONNEMENT",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanNeon
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Crédits Analyses Restants:",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = if (user?.isPro == true || user?.isSuperAdmin == true) "ILLIMITÉ ∞" else "${user?.credits ?: 0} / 10",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = if (user?.isPro == true) SuccessGreen else PinkNeon
                            )
                        }

                        if (user?.isPro != true) {
                            Button(
                                onClick = { viewModel.toggleUpgradeModal(true) },
                                colors = ButtonDefaults.buttonColors(containerColor = PinkNeon),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "Passer à PRO 👑",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Statut du Compte: " + if (user?.isSuperAdmin == true) "Super Administrateur Autorisé (Lando/Camel)" else "Actif",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Account Actions
        item {
            OutlinedButton(
                onClick = { viewModel.logout() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed)
            ) {
                Text(
                    text = "Se Déconnecter / Changer de Compte 🚪",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Upgrade Modal Simulation
    if (isUpgradeModalOpen) {
        AlertDialog(
            onDismissRequest = { viewModel.toggleUpgradeModal(false) },
            containerColor = DarkBackground,
            title = {
                Text(
                    text = "👑 Débloquez BlowUp Pro Unlimited",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "Obtenez un accès illimité aux analyses virales TikTok & Facebook et aux 3 concepts de scripts prêts à tourner.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "• ✨ Analyses virales illimitées", fontSize = 12.sp, color = TextPrimary)
                    Text(text = "• 🪝 Réécriture IA des Hooks en direct", fontSize = 12.sp, color = TextPrimary)
                    Text(text = "• 🎵 Radar des sons et hashtags Tier 1", fontSize = 12.sp, color = TextPrimary)
                    Text(text = "• ⚡ Générateur de scripts 3 scènes", fontSize = 12.sp, color = TextPrimary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Prix: 29€ / mois (Simulation)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanNeon
                    )
                }
            },
            confirmButton = {
                GlowingButton(
                    text = "CONFIRMER L'ABONNEMENT PRO ⚡",
                    onClick = { viewModel.upgradeCurrentToPro() }
                )
            },
            dismissButton = {
                TextButton(onClick = { viewModel.toggleUpgradeModal(false) }) {
                    Text(text = "Annuler", color = TextSecondary)
                }
            }
        )
    }
}
