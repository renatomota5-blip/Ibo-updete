package com.iboplus.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iboplus.app.data.repo.ServersRepository
import com.iboplus.app.viewmodel.model.ServersUiState
import com.iboplus.app.viewmodel.model.ServerItemUi
import com.iboplus.app.viewmodel.model.QrUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de Servidores / Playlist.
 * - Carrega listas e QR
 * - Permite conectar/desconectar
 */
@HiltViewModel
class ServersViewModel @Inject constructor(
    private val repo: ServersRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ServersUiState())
    val state: StateFlow<ServersUiState> = _state

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching {
                repo.getServers() to repo.getQr()
            }.onSuccess { (servers, qr) ->
                _state.value = _state.value.copy(
                    loading = false,
                    items = servers,
                    qr = qr,
                    mac = repo.getMac(),
                    deviceKey = repo.getDeviceKey(),
                    statusText = repo.getStatusText()
                )
            }.onFailure {
                _state.value = _state.value.copy(
                    loading = false,
                    error = it.message ?: "Erro ao carregar servidores"
                )
            }
        }
    }

    fun connect(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = runCatching { repo.connect(id) }
            onResult(result.isSuccess)
            if (result.isSuccess) {
                load()
            } else {
                _state.value = _state.value.copy(error = result.exceptionOrNull()?.message)
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            runCatching { repo.disconnect() }
            load()
        }
    }

    fun reloadFromPanel() {
        load()
    }
}
