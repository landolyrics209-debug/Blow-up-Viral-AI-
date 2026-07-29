package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.MainViewModel
import com.example.ui.components.GlowingButton
import com.example.ui.components.NeonCard
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.PinkNeon
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletNeon

@Composable
fun AuthScreen(
    viewModel: MainViewModel,
    onLoginSuccess: () -> Unit
) {
    var emailInput by remember { mutableStateOf("landolyrics209@gmail.com") }
    var passwordInput by remember { mutableStateOf("password123") }
    var nameInput by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        NeonCard(
            borderColor = VioletNeon,
            glowElevation = 16.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(12.dp)
            ) {
                // App Icon / Logo
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(18.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_app_icon),
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "BLOWUP VIRAL AI",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )

                Text(
                    text = "TikTok & Facebook Reels Viral Analytics",
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (isSignUp) {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nom d'utilisateur", color = TextSecondary) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IndigoNeon,
                            unfocusedBorderColor = DarkSurfaceVariant,
                            focusedContainerColor = DarkSurface,
                            unfocusedContainerColor = DarkSurface,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                OutlinedTextField(
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Adresse Email", color = TextSecondary) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndigoNeon,
                        unfocusedBorderColor = DarkSurfaceVariant,
                        focusedContainerColor = DarkSurface,
                        unfocusedContainerColor = DarkSurface,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = { passwordInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Mot de passe", color = TextSecondary) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndigoNeon,
                        unfocusedBorderColor = DarkSurfaceVariant,
                        focusedContainerColor = DarkSurface,
                        unfocusedContainerColor = DarkSurface,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Quick Super Admin Login Hints
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(DarkSurfaceVariant)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "🛡️ COMPTES SUPER ADMINS PRÉ-CONFIGURÉS:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PinkNeon
                    )
                    Text(
                        text = "1. landolyrics209@gmail.com",
                        fontSize = 11.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "2. camellandoml209@gmail.com",
                        fontSize = 11.sp,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                GlowingButton(
                    text = if (isSignUp) "S'INSCRIRE ET DÉMARRER 🚀" else "SE CONNECTER À BLOWUP ⚡",
                    onClick = {
                        if (emailInput.isNotBlank()) {
                            viewModel.loginWithEmail(emailInput, nameInput)
                            onLoginSuccess()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { isSignUp = !isSignUp }) {
                    Text(
                        text = if (isSignUp) "Déjà un compte? Se connecter" else "Pas encore de compte? S'inscrire",
                        fontSize = 13.sp,
                        color = CyanNeon
                    )
                }
            }
        }
    }
}
