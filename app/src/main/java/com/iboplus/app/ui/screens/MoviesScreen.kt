package com.iboplus.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.iboplus.app.viewmodel.MoviesViewModel
import com.iboplus.app.viewmodel.model.MovieCategoryUi
import com.iboplus.app.viewmodel.model.MovieUi

@Composable
fun MoviesScreen(
    vm: MoviesViewModel,
    onOpen: (MovieUi) -> Unit
) {
    val ui by vm.state.collectAsState()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { vm.load() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Filmes",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                vm.search(it)
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("procurar filme...") }
        )

        when {
            ui.loading -> LoadingBox()

            ui.error != null -> ErrorBox(
                message = ui.error ?: "Erro ao carregar filmes",
                onRetry = { vm.load() }
            )

            else -> {
                CategoryRow(
                    categories = ui.categories,
                    selected = ui.selectedCategoryId,
                    onClick = { id -> vm.selectCategory(id) }
                )

                MovieGrid(
                    movies = ui.items,
                    onClick = onOpen,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/* ---------------- Components ---------------- */

@Composable
private fun LoadingBox() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CategoryRow(
    categories: List<MovieCategoryUi>,
    selected: String?,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AssistChip(
            onClick = { onClick("all") },
            label = { Text("Tudo") },
            colors = if (selected == null || selected == "all")
                AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) else AssistChipDefaults.assistChipColors()
        )
        categories.forEach { cat ->
            AssistChip(
                onClick = { onClick(cat.id) },
                label = { Text("${cat.name} (${cat.count})") },
                colors = if (selected == cat.id)
                    AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ) else AssistChipDefaults.assistChipColors()
            )
        }
    }
}

@Composable
private fun MovieGrid(
    movies: List<MovieUi>,
    onClick: (MovieUi) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieCard(movie = movie, onClick = { onClick(movie) })
        }
    }
}

@Composable
private fun MovieCard(
    movie: MovieUi,
    onClick: () -> Unit
) {
    Surface(
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(movie.posterUrl),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(10.dp)) {
                Text(
                    text = movie.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
                movie.year?.takeIf { it.isNotBlank() }?.let {
                    Spacer(Modifier.height(2.dp))
                    Text(it, style = MaterialTheme.typography.bodySmall)
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
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onRetry) { Text("Tentar novamente") }
    }
}
