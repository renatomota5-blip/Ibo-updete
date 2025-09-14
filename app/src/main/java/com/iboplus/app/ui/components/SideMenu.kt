package com.iboplus.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.iboplus.app.ui.navigation.Destinations

/**
 * Menu lateral usado no layout "backdrop2" (menu à esquerda).
 * Compatível com TV (focusable) e touch.
 */
@Immutable
data class SideMenuItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val defaultItems = listOf(
    SideMenuItem(Destinations.HOME, "Início", Icons.Filled.Home),
    SideMenuItem(Destinations.LIVE, "TV ao Vivo", Icons.Filled.LiveTv),
    SideMenuItem(Destinations.MOVIES, "Filmes", Icons.Filled.Movie),
    SideMenuItem(Destinations.SERIES, "Séries", Icons.Filled.Tv),
    SideMenuItem(Destinations.SETTINGS, "Configurações", Icons.Filled.Settings),
    SideMenuItem("reload", "Recarregar", Icons.Filled.Refresh),
    SideMenuItem("exit", "Sair do App", Icons.Filled.ExitToApp),
)

@Composable
fun SideMenu(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    items: List<SideMenuItem> = defaultItems
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.25f)
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(item.route) }
                    .focusable(true, role = Role.Button)
                    .background(
                        if (selected) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    )
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
