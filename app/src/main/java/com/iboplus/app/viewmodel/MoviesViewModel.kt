package com.iboplus.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iboplus.app.data.repo.MoviesRepository
import com.iboplus.app.viewmodel.model.MoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de Filmes.
 * - Carrega categorias e filmes
 * - Permite busca e filtro por categoria
 */
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesUiState())
    val state: StateFlow<MoviesUiState> = _state

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching {
                repo.getCategories() to repo.getMovies()
            }.onSuccess { (cats, movies) ->
                _state.value = _state.value.copy(
                    loading = false,
                    categories = cats,
                    items = movies
                )
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Erro desconhecido"
                )
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            val all = repo.getMovies()
            val filtered = if (query.isBlank()) all else {
                all.filter { it.title.contains(query, ignoreCase = true) }
            }
            _state.value = _state.value.copy(items = filtered)
        }
    }

    fun selectCategory(id: String?) {
        viewModelScope.launch {
            val all = repo.getMovies()
            val filtered = if (id.isNullOrBlank() || id == "all") all else {
                all.filter { it.id.startsWith(id) || it.title.contains(id, ignoreCase = true) }
            }
            _state.value = _state.value.copy(
                selectedCategoryId = id,
                items = filtered
            )
        }
    }
}
