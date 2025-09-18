package com.iboplus.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iboplus.app.data.model.AdItem
import com.iboplus.app.data.model.AppUserResponse
import com.iboplus.app.data.model.BackdropItem
import com.iboplus.app.data.model.BackgroundResponse
import com.iboplus.app.data.model.LogoResponse
import com.iboplus.app.data.model.NoteMessage
import com.iboplus.app.data.model.PlaylistItem
import com.iboplus.app.data.model.QrResponse
import com.iboplus.app.data.model.SettingsResponse
import com.iboplus.app.data.model.SportEvent
import com.iboplus.app.data.repository.PanelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel central que conecta UI ↔ Repositório (PanelRepository).
 * Exposição dos dados em StateFlow para telas Compose.
 */
@HiltViewModel
class PanelViewModel @Inject constructor(
    private val repository: PanelRepository
) : ViewModel() {

    private val _settings = MutableStateFlow<SettingsResponse?>(null)
    val settings: StateFlow<SettingsResponse?> = _settings.asStateFlow()

    private val _logo = MutableStateFlow<LogoResponse?>(null)
    val logo: StateFlow<LogoResponse?> = _logo.asStateFlow()

    private val _background = MutableStateFlow<BackgroundResponse?>(null)
    val background: StateFlow<BackgroundResponse?> = _background.asStateFlow()

    private val _qr = MutableStateFlow<QrResponse?>(null)
    val qr: StateFlow<QrResponse?> = _qr.asStateFlow()

    private val _notes = MutableStateFlow<List<NoteMessage>>(emptyList())
    val notes: StateFlow<List<NoteMessage>> = _notes.asStateFlow()

    private val _appUser = MutableStateFlow<AppUserResponse?>(null)
    val appUser: StateFlow<AppUserResponse?> = _appUser.asStateFlow()

    private val _playlists = MutableStateFlow<List<PlaylistItem>>(emptyList())
    val playlists: StateFlow<List<PlaylistItem>> = _playlists.asStateFlow()

    private val _ads = MutableStateFlow<List<AdItem>>(emptyList())
    val ads: StateFlow<List<AdItem>> = _ads.asStateFlow()

    private val _backdrops = MutableStateFlow<List<BackdropItem>>(emptyList())
    val backdrops: StateFlow<List<BackdropItem>> = _backdrops.asStateFlow()

    private val _sports = MutableStateFlow<List<SportEvent>>(emptyList())
    val sports: StateFlow<List<SportEvent>> = _sports.asStateFlow()

    /* ------------------ Funções de carregamento ------------------ */

    fun loadInitialData(mac: String, chave: String) {
        viewModelScope.launch {
            repository.fetchSettings()
                .let { if (it is PanelRepository.Result.Success) _settings.value = it.data }
            repository.fetchLogo()
                .let { if (it is PanelRepository.Result.Success) _logo.value = it.data }
            repository.fetchBackground()
                .let { if (it is PanelRepository.Result.Success) _background.value = it.data }
            repository.fetchQr()
                .let { if (it is PanelRepository.Result.Success) _qr.value = it.data }
            repository.fetchNotes()
                .let { if (it is PanelRepository.Result.Success) _notes.value = it.data ?: emptyList() }
            repository.fetchAppUser(mac, chave)
                .let { if (it is PanelRepository.Result.Success) _appUser.value = it.data }
            repository.fetchPlaylists(mac, chave)
                .let { if (it is PanelRepository.Result.Success) _playlists.value = it.data ?: emptyList() }
            repository.fetchAds()
                .let { if (it is PanelRepository.Result.Success) _ads.value = it.data ?: emptyList() }
            repository.fetchBackdrop()
                .let { if (it is PanelRepository.Result.Success) _backdrops.value = it.data.results ?: emptyList() }
            repository.fetchSports()
                .let { if (it is PanelRepository.Result.Success) _sports.value = it.data.events ?: emptyList() }
        }
    }

    fun refreshPlaylists(mac: String, chave: String) {
        viewModelScope.launch {
            repository.fetchPlaylists(mac, chave).let {
                if (it is PanelRepository.Result.Success) {
                    _playlists.value = it.data ?: emptyList()
                }
            }
        }
    }

    fun refreshUser(mac: String, chave: String) {
        viewModelScope.launch {
            repository.fetchAppUser(mac, chave).let {
                if (it is PanelRepository.Result.Success) _appUser.value = it.data
            }
        }
    }
}
