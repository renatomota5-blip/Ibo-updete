package com.iboplus.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iboplus.app.data.repo.LiveRepository
import com.iboplus.app.viewmodel.model.LiveCategoryUi
import com.iboplus.app.viewmodel.model.LiveChannelUi
import com.iboplus.app.viewmodel.model.LiveUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de TV ao Vivo.
 * Atualmente o repositório só possui refresh(); as listas são mantidas no estado.
 */
@HiltViewModel
class LiveViewModel @Inject constructor(
    private val repo: LiveRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LiveUiState())
    val state: StateFlow<LiveUiState> = _state

    /** Dispara o refresh do repositório e mantém (ou limpa) o estado local. */
    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching {
                repo.refresh()
            }.onSuccess {
                // Aqui você pode mapear dados reais do PanelRepository para categorias/canais.
                _state.value = _state.value.copy(
                    loading = false
                )
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Erro desconhecido"
                )
            }
        }
    }

    /** Atualiza a lista de canais (use ao injetar dados reais do painel). */
    fun setChannels(channels: List<LiveChannelUi>, categories: List<LiveCategoryUi> = emptyList()) {
        _state.value = _state.value.copy(
            channels = channels,
            categories = categories
        )
    }

    /** Busca em cima da lista já presente no estado. */
    fun search(query: String) {
        val all = _state.value.channels
        val filtered = if (query.isBlank()) all else {
            all.filter { it.name.contains(query, ignoreCase = true) }
        }
        _state.value = _state.value.copy(channels = filtered)
    }

    /** Filtro por categoria usando a lista já presente no estado. */
    fun selectCategory(id: String?) {
        val all = _state.value.channels
        val filtered = if (id.isNullOrBlank() || id == "all") all else {
            all.filter { it.group == id }
        }
        _state.value = _state.value.copy(
            selectedCategoryId = id,
            channels = filtered
        )
    }
}
