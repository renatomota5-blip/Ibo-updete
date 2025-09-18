package com.iboplus.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iboplus.app.data.repo.SeriesRepository
import com.iboplus.app.viewmodel.model.SeriesUi
import com.iboplus.app.viewmodel.model.SeriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para Séries.
 * Usa repo.refresh() e mantém as listas no estado local.
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
                repo.refresh()
            }.onSuccess {
                // Depois você pode alimentar `items` e `categories` com dados reais do painel.
                _state.value = _state.value.copy(loading = false)
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Erro desconhecido"
                )
            }
        }
    }

    /** Permite setar os dados reais vindos do painel. */
    fun setSeries(list: List<SeriesUi>) {
        _state.value = _state.value.copy(items = list)
    }

    fun search(query: String) {
        val all = _state.value.items
        val filtered =
            if (query.isBlank()) all
            else all.filter { it.title.contains(query, ignoreCase = true) }
        _state.value = _state.value.copy(items = filtered)
    }

    fun selectCategory(id: String?) {
        val all = _state.value.items
        val filtered =
            if (id.isNullOrBlank() || id == "all") all
            else all.filter { it.id.startsWith(id) || it.title.contains(id, ignoreCase = true) }

        _state.value = _state.value.copy(
            selectedCategoryId = id,
            items = filtered
        )
    }
}
