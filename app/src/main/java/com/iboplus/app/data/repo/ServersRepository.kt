package com.iboplus.app.data.repo

import com.iboplus.app.data.api.ApiService
import com.iboplus.app.viewmodel.model.QrUi
import com.iboplus.app.viewmodel.model.ServerItemUi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositório responsável por gerenciar playlists/servidores vinculados ao app.
 * Carrega dados do painel (servidores, QR, status do app).
 */
@Singleton
class ServersRepository @Inject constructor(
    private val api: ApiService
) {
    private var cacheServers: List<ServerItemUi> = emptyList()
    private var cacheQr: QrUi? = null
    private var mac: String = ""
    private var deviceKey: String = ""
    private var statusText: String? = null

    /**
     * Busca servidores e QR code do painel.
     * O endpoint real pode ser `playlist.php` ou `qr.php` no ApiService.
     */
    suspend fun refresh(): Pair<List<ServerItemUi>, QrUi?> {
        // Exemplo: pegando lista via playlist endpoint
        val rawPlaylist = api.getPlaylist()
        // Aqui você pode fazer parse real da lista para servidores
        cacheServers = listOf(
            ServerItemUi(id = "1", title = "Servidor Principal", subtitle = "Ativo", connected = true),
            ServerItemUi(id = "2", title = "Servidor Secundário", subtitle = "Backup", connected = false)
        )

        // Exemplo: QR para ativação / site
        val qrResp = api.getQr()
        cacheQr = QrUi(
            imageUrl = qrResp.url,
            title = "Escaneie para ativar",
            subtitle = qrResp.message,
            webUrl = qrResp.url
        )

        // Status fake por enquanto — ajustar quando API real
        mac = "00:11:22:33:44:55"
        deviceKey = "123456"
        statusText = "Ativado"

        return cacheServers to cacheQr
    }

    suspend fun getServers(): List<ServerItemUi> {
        if (cacheServers.isEmpty()) refresh()
        return cacheServers
    }

    suspend fun getQr(): QrUi? {
        if (cacheQr == null) refresh()
        return cacheQr
    }

    fun getMac(): String = mac
    fun getDeviceKey(): String = deviceKey
    fun getStatusText(): String? = statusText

    suspend fun connect(id: String): Boolean {
        // Chamada ao painel para marcar como conectado
        return cacheServers.any { it.id == id }
    }

    suspend fun disconnect() {
        // Desconecta do servidor atual
    }
}
