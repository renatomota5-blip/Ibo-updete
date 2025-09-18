package com.iboplus.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iboplus.app.viewmodel.model.QrUi
import com.iboplus.app.viewmodel.model.ServerItemUi
import com.iboplus.app.viewmodel.model.ServersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServersViewModel : ViewModel() {

    private val _state = MutableStateFlow(ServersUiState())
    val state: StateFlow<ServersUiState> = _state

    /** Exemplo de carga inicial (troque pela sua fonte real). */
    fun loadServers() {
        _state.value = _state.value.copy(loading = true, error = null)

        viewModelScope.launch {
            try {
                val items = listOf(
                    ServerItemUi(
                        id = "1",
                        title = "Servidor Padrão",
                        subtitle = "https://example.com",
                        connected = false
                    )
                )
                _state.value = _state.value.copy(
                    loading = false,
                    items = items,
                    statusText = "${items.size} servidor(es) encontrado(s)"
                )
            } catch (t: Throwable) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = t.message ?: "Erro desconhecido"
                )
            }
        }
    }

    fun setQr(qr: QrUi?) {
        _state.value = _state.value.copy(qr = qr)
    }

    fun setConnectionState(id: String, connected: Boolean) {
        val updated = _state.value.items.map {
            if (it.id == id) it.copy(connected = connected) else it
        }
        _state.value = _state.value.copy(items = updated)
    }

    fun setMacAndDeviceKey(mac: String, deviceKey: String) {
        _state.value = _state.value.copy(mac = mac, deviceKey = deviceKey)
    }
}
