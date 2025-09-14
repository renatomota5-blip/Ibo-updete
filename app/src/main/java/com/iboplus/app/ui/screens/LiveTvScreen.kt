package com.iboplus.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iboplus.app.viewmodel.LiveViewModel
import com.iboplus.app.viewmodel.model.LiveCategoryUi
import com.iboplus.app.viewmodel.model.LiveChannelUi

/**
 * TV ao Vivo
 * - Lista categorias e canais
 * - Busca
 * - Ao clicar no canal -> callback onPlay(channel)
 *
 * Esta tela espera que o LiveViewModel já esteja provido pelo Hilt em quem chamar.
 */
@Composable
fun LiveTvScreen(
    vm: LiveViewModel,
    onPlay: (LiveChannelUi) -> Unit
) {
    val uiState by vm.state.collectAsState()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { vm.load() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Título
        Text(
            text = "TV ao Vivo",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        // Busca
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                vm.search(it)
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("procurar canal...") }
        )

        // Conteúdo
        when {
            uiState.loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                ErrorBox(
                    message = uiState.error ?: "Erro",
                    onRetry = { vm.load() }
                )
            }

            else -> {
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Categorias
                    CategoryList(
                        categories = uiState.categories,
                        selected = uiState.selectedCategoryId,
                        onClick = { id -> vm.selectCategory(id) },
                        modifier = Modifier
                            .width(260.dp)
                            .fillMaxHeight()
                    )

                    // Canais
                    ChannelList(
                        channels = uiState.channels,
                        onClick = onPlay,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

/* ---------------- Components ---------------- */

@Composable
private fun CategoryList(
    categories: List<LiveCategoryUi>,
    selected: String?,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 1.dp,
        modifier = modifier
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                Text(
                    "Categorias",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(6.dp)
                )
            }
            items(categories, key = { it.id }) { cat ->
                val isSel = cat.id == selected
                val colors = if (isSel) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface

                Surface(
                    color = colors,
                    tonalElevation = if (isSel) 2.dp else 0.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(cat.id) }
                ) {
                    Row(
                        Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(cat.name)
                        Text("${cat.count}")
                    }
                }
            }
        }
    }
}

@Composable
private fun ChannelList(
    channels: List<LiveChannelUi>,
    onClick: (LiveChannelUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 1.dp,
        modifier = modifier
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(channels, key = { it.id }) { ch ->
                Surface(
                    tonalElevation = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(ch) }
                ) {
                    Row(
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(ch.name, fontWeight = FontWeight.SemiBold)
                            if (!ch.group.isNullOrBlank()) {
                                Text(
                                    ch.group!!,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        Text(ch.number ?: "")
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorBox(message: String, onRetry: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onRetry) { Text("Tentar novamente") }
    }
}
