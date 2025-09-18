package com.iboplus.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iboplus.app.viewmodel.model.ServerItem
import com.iboplus.app.viewmodel.model.ServersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServersViewModel : ViewModel() {

    private val _state = MutableStateFlow(ServersUiState())
    val state: StateFlow<ServersUiState> = _state

    fun loadServers() {
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                // TODO: troque pela sua fonte real (repo/DB/rede)
                val data = listOf(
                    ServerItem(id = "1", name = "Servidor Padrão", url = "https://example.com")
                )
                _state.value = _state.value.copy(isLoading = false, items = data)
            } catch (t: Throwable) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = t.message ?: "Erro desconhecido"
                )
            }
        }
    }
}
