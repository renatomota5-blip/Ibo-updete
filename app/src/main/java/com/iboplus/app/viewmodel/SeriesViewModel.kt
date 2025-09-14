package com.iboplus.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iboplus.app.data.repo.SeriesRepository
import com.iboplus.app.viewmodel.model.SeriesCategoryUi
import com.iboplus.app.viewmodel.model.SeriesUi
import com.iboplus.app.viewmodel.model.SeriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de Séries.
 * - Carrega categorias e séries
 * - Permite busca e filtro
 */
@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val repo: SeriesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SeriesUiState())
    val state: StateFlow<SeriesUiState> = _state

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching {
                repo.getCategories() to repo.getSeries()
            }.onSuccess { (cats, series) ->
                _state.value = _state.value.copy(
                    loading = false,
                    categories = cats,
                    items = series
                )
            }.onFailure {
                _state.value = _state.value.copy(
                    loading = false,
                    error = it.message ?: "Erro desconhecido"
                )
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            val all = repo.getSeries()
            val filtered = if (query.isBlank()) all else {
                all.filter { it.title.contains(query, ignoreCase = true) }
            }
            _state.value = _state.value.copy(items = filtered)
        }
    }

    fun selectCategory(id: String?) {
        viewModelScope.launch {
            val all = repo.getSeries()
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
