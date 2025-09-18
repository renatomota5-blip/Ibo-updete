package com.iboplus.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iboplus.app.data.repo.LiveRepository
import com.iboplus.app.viewmodel.model.LiveUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de TV ao Vivo.
 * - Carrega categorias e canais via LiveRepository
 * - Permite busca e seleção de categoria
 */
@HiltViewModel
class LiveViewModel @Inject constructor(
    private val repo: LiveRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LiveUiState())
    val state: StateFlow<LiveUiState> = _state

    /** Carrega categorias e canais iniciais. */
    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching {
                repo.getCategories() to repo.getChannels()
            }.onSuccess { (cats, chans) ->
                _state.value = _state.value.copy(
                    loading = false,
                    categories = cats,
                    channels = chans
                )
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Erro desconhecido"
                )
            }
        }
    }

    /** Filtra canais por texto. */
    fun search(query: String) {
        viewModelScope.launch {
            val all = repo.getChannels()
            val filtered = if (query.isBlank()) all else {
                all.filter { it.name.contains(query, ignoreCase = true) }
            }
            _state.value = _state.value.copy(channels = filtered)
        }
    }

    /** Seleciona categoria e filtra canais. */
    fun selectCategory(id: String?) {
        viewModelScope.launch {
            val all = repo.getChannels()
            val filtered = if (id.isNullOrBlank() || id == "all") all else {
                all.filter { it.group == id }
            }
            _state.value = _state.value.copy(
                selectedCategoryId = id,
                channels = filtered
            )
        }
    }
}
