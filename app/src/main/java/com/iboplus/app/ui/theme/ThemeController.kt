package com.iboplus.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage

/**
 * ThemeController é responsável por aplicar customizações dinâmicas
 * vindas do painel (ex.: logo, cores, background).
 *
 * Para simplificação, usamos apenas um estado interno com cor e imagem.
 */
object ThemeController {
    var backgroundUrl: String? = null
    var logoUrl: String? = null
    var primaryColor: Color = Color(0xFF0D6EFD) // azul padrão
}

/**
 * Wrapper do tema que aplica cores padrão + customizações do painel.
 */
@Composable
fun IboPlusThemeWrapper(
    content: @Composable () -> Unit
) {
    // Aqui poderíamos alterar cores dinamicamente baseado no painel
    val primary = ThemeController.primaryColor

    IboPlusTheme(
        darkTheme = false,
        dynamicColor = false,
        content = {
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    primary = primary
                ),
                typography = MaterialTheme.typography,
                shapes = MaterialTheme.shapes,
                content = content
            )
        }
    )
}

/**
 * Exibe logo configurada no painel, ou placeholder.
 */
@Composable
fun PanelLogo(modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    val logo = ThemeController.logoUrl
    if (logo != null) {
        AsyncImage(
            model = logo,
            contentDescription = "Logo do painel",
            modifier = modifier
        )
    } else {
        // Placeholder simples (texto)
        androidx.compose.material3.Text("IBO Plus", color = Color.White)
    }
}
