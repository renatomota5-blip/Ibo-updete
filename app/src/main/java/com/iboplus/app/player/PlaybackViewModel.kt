package com.iboplus.app.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para controlar o estado de reprodução de mídia.
 * Centraliza informações do player (url atual, se está tocando, erros, etc.)
 * e expõe para a UI via StateFlow.
 */
@HiltViewModel
class PlaybackViewModel @Inject constructor() : ViewModel() {

    data class PlaybackState(
        val url: String? = null,
        val isPlaying: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(PlaybackState())
    val state: StateFlow<PlaybackState> = _state

    /**
     * Inicia a reprodução de uma nova URL.
     */
    fun play(url: String) {
        viewModelScope.launch {
            _state.value = PlaybackState(
                url = url,
                isPlaying = true,
                error = null
            )
        }
    }

    /**
     * Atualiza flag de play/pause.
     */
    fun setPlaying(playing: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isPlaying = playing)
        }
    }

    /**
     * Sinaliza erro.
     */
    fun setError(message: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(error = message)
        }
    }

    /**
     * Para e limpa a URL atual.
     */
    fun stop() {
        viewModelScope.launch {
            _state.value = PlaybackState()
        }
    }
}
