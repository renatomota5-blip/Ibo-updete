package com.iboplus.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Tema Compose do IBO Plus.
 * - Por ora usa paletas estáticas (claro/escuro).
 * - Futuramente, substituir cores com dados do painel (bg.php, setting.json, logo.php)
 *   via um ThemeController central.
 */

private val LightColors = lightColorScheme(
    primary = Color(0xFF0D6EFD),
    onPrimary = Color.White,
    secondary = Color(0xFF6C757D),
    onSecondary = Color.White,
    tertiary = Color(0xFF198754),
    onTertiary = Color.White,
    background = Color(0xFF0A0A0A),
    onBackground = Color(0xFFEDEDED),
    surface = Color(0xFF151515),
    onSurface = Color(0xFFEDEDED),
    surfaceVariant = Color(0xFF1E1E1E),
    outline = Color(0xFF2B2B2B)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF0D6EFD),
    onPrimary = Color.White,
    secondary = Color(0xFFADB5BD),
    onSecondary = Color(0xFF0A0A0A),
    tertiary = Color(0xFF20C997),
    onTertiary = Color(0xFF0A0A0A),
    background = Color(0xFF000000),
    onBackground = Color(0xFFEDEDED),
    surface = Color(0xFF0E0E0E),
    onSurface = Color(0xFFEDEDED),
    surfaceVariant = Color(0xFF161616),
    outline = Color(0xFF2B2B2B)
)

/**
 * Extensão de cores para possíveis ajustes vindos do painel (ex.: cor de destaque do tema escolhido).
 */
@Immutable
data class IboPlusExtendedColors(
    val accent: Color = Color(0xFFFFA500) // laranja suave para destaques
)

val LocalIboPlusColors = staticCompositionLocalOf { IboPlusExtendedColors() }

/**
 * Composable raiz do tema.
 */
@Composable
fun IboPlusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorScheme: ColorScheme? = null,
    content: @Composable () -> Unit
) {
    val scheme = colorScheme ?: if (darkTheme) DarkColors else LightColors

    // No futuro, poderemos injetar cores/planos de fundo vindos do painel
    // (ex.: aplicar imagem de fundo global via Box na MainActivity, ou via tema com Brush)

    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
