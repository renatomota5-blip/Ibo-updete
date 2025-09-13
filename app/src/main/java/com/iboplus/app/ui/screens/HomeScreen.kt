package com.iboplus.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iboplus.app.ui.navigation.Routes

/**
 * HomeScreen
 *
 * Tela principal com os blocos:
 * - TV ao Vivo, Filmes, Séries, Conta, Servidores, Configurar, Recarregar Lista, Sair do App
 *
 * Observações:
 * - O fundo (imagem/cor) e a logo são definidos pelo painel e aplicados no tema global.
 * - Ao integrar a camada de rede, este layout passa a exibir os destaques/banners (TMDB via backdrop.php/2).
 */
@Composable
fun HomeScreen(navController: NavController) {
    val menuItems = remember {
        listOf(
            HomeItem("TV ao Vivo") { /* TODO: nav para Live (Activity ou tela Compose) */ },
            HomeItem("Filmes") { /* TODO: nav para Movies */ },
            HomeItem("Séries") { /* TODO: nav para Series */ },
            HomeItem("Conta") { /* TODO: nav para Account */ },
            HomeItem("Servidores / Playlist") { /* TODO: nav para Servers */ },
            HomeItem("Configurar") { /* TODO: nav para Settings */ },
            HomeItem("Recarregar Lista") { /* TODO: acionar refresh da playlist do painel */ },
            HomeItem("Sair do app") { /* TODO: finishAffinity() via callback do Activity */ },
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Título da Home (pode ser substituído por logo/imagem do painel)
            Text(
                text = "IBO Plus",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
            )

            // Grid 2xN simples com espaçamento
            HomeGrid(
                items = menuItems,
                onClick = { it.action() }
            )

            // Mensagem opcional vinda do painel (note.json / setting.json)
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Mensagem do painel poderá aparecer aqui (ex.: título e texto do note.json / mensagens configuradas).",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

private data class HomeItem(
    val label: String,
    val action: () -> Unit
)

@Composable
private fun HomeGrid(
    items: List<HomeItem>,
    onClick: (HomeItem) -> Unit
) {
    // Layout 2 colunas: cada item como um Card com um botão.
    val chunked = items.chunked(2)
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        chunked.forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 120.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = { onClick(item) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Abrir")
                            }
                        }
                    }
                }
                // Em linha com 1 item (ímpar), completa com espaço
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
