package com.iboplus.app.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.security.MessageDigest
import kotlin.math.abs

@Composable
fun ActivationScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current

    val mac by remember { mutableStateOf(generateMac(context)) }
    val deviceKey by remember { mutableStateOf(generateDeviceKey(context)) }

    val panelBaseUrl = remember { "https://iboplus.motanetplay.top" }

    var checking by remember { mutableStateOf(false) }
    var checkResult by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Ativação do Aplicativo",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )

            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Passe estes dados ao responsável pelo painel para ativação:")

                    KeyRow(label = "MAC", value = mac) {
                        copyToClipboard(context, "MAC", mac)
                    }
                    KeyRow(label = "CHAVE", value = deviceKey) {
                        copyToClipboard(context, "CHAVE", deviceKey)
                    }

                    Text(
                        text = "Você também pode usar o QR pelo painel para direcionar ao site/WhatsApp.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { openUrl(context, panelBaseUrl) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Abrir site do painel")
                }
                OutlinedButton(
                    onClick = {
                        checking = true
                        checkResult = "Ativação pendente no painel"
                        checking = false
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (checking) "Verificando..." else "Verificar status")
                }
            }

            checkResult?.takeIf { it.isNotBlank() }?.let {
                AssistiveCard(message = it)
            }

            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "QR do painel",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        "O QR é configurado no painel e aparece quando o app está sem lista ou inativo. " +
                            "Nesta tela, vamos exibir a imagem recebida do painel assim que integrarmos a camada de rede.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* navegação para Home quando ativado */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Já ativei — Entrar no app")
            }
        }
    }
}

/* ----------------------------- UI Helpers ------------------------------ */

@Composable
private fun KeyRow(label: String, value: String, onCopy: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
        OutlinedButton(onClick = onCopy) {
            Text("Copiar")
        }
    }
}

@Composable
private fun AssistiveCard(message: String) {
    Surface(
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(12.dp)
        )
    }
}

/* --------------------------- Helpers -------------------------- */

private fun generateMac(context: Context): String {
    val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        ?: "iboplus"
    val md5 = md5(androidId)
    return md5.take(12).uppercase()
}

private fun generateDeviceKey(context: Context): String {
    val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        ?: System.currentTimeMillis().toString()
    val hash = androidId.hashCode()
    val six = abs(hash % 1_000_000)
    return six.toString().padStart(6, '0')
}

private fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bytes = md.digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

private fun openUrl(context: Context, url: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}

private fun copyToClipboard(context: Context, label: String, text: String) {
    val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    cm.setPrimaryClip(ClipData.newPlainText(label, text))
}
