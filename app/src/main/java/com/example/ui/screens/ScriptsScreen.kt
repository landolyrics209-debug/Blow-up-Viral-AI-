package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VideoScriptConcept
import com.example.ui.MainViewModel
import com.example.ui.components.NeonCard
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.IndigoNeon
import com.example.ui.theme.PinkNeon
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletNeon

@Composable
fun ScriptsScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val currentScripts by viewModel.currentScripts.collectAsState()
    val savedScripts by viewModel.savedScripts.collectAsState()

    val displayScripts = if (currentScripts.isNotEmpty()) currentScripts else savedScripts

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⚡ STUDIO SCRIPTS VIRAUX IA",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(IndigoNeon)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "3 CONCEPTS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Concepts prêts à tourner avec découpage scène, texte exact et Call-To-Action.",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        if (displayScripts.isEmpty()) {
            item {
                NeonCard(borderColor = VioletNeon) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 24.dp)
                    ) {
                        Text(
                            text = "🎬 Aucun script généré pour le moment.",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Lancez une analyse sur l'onglet Accueil pour débloquer vos 3 concepts vidéo personnalisés.",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        } else {
            items(displayScripts) { script ->
                ScriptConceptCard(script = script, onCopy = { fullText ->
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Script", fullText)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Script copié dans le presse-papier!", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}

@Composable
fun ScriptConceptCard(
    script: VideoScriptConcept,
    onCopy: (String) -> Unit
) {
    val fullTextToCopy = """
        ${script.conceptTitle} (${script.conceptTag})
        
        📹 ACTION VISUELLE:
        ${script.visualAction}
        
        🗣️ SCRIPT PARLÉ EXACT:
        ${script.spokenScript}
        
        🎯 CALL-TO-ACTION (CTA):
        ${script.callToAction}
    """.trimIndent()

    NeonCard(borderColor = if (script.conceptTag.contains("CONTRARIEN")) PinkNeon else if (script.conceptTag.contains("LISTE")) CyanNeon else VioletNeon) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = script.conceptTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(DarkSurfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = script.conceptTag,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyanNeon
                        )
                    }
                }

                Button(
                    onClick = { onCopy(fullTextToCopy) },
                    colors = ButtonDefaults.buttonColors(containerColor = IndigoNeon),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "Copier 📋", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Visual Action Block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(DarkSurfaceVariant)
                    .padding(10.dp)
            ) {
                Text(
                    text = "📹 ACTION VISUELLE A L'ÉCRAN:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = VioletNeon
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = script.visualAction,
                    fontSize = 12.sp,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Spoken Script Block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(DarkSurfaceVariant)
                    .padding(10.dp)
            ) {
                Text(
                    text = "🗣️ SCRIPT PARLÉ MOT À MOT:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyanNeon
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "\"${script.spokenScript}\"",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // CTA Block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(DarkSurfaceVariant)
                    .padding(10.dp)
            ) {
                Text(
                    text = "🎯 CALL-TO-ACTION (CTA) DE FIN:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkNeon
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = script.callToAction,
                    fontSize = 12.sp,
                    color = TextPrimary
                )
            }
        }
    }
}
