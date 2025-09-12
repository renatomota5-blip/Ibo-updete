package com.iboplus.app.data

import android.content.Context
import android.net.Uri

/**
 * Centraliza as URLs do painel/servidor usadas pelo app.
 *
 * Regras:
 * - Se o usuário já salvou uma URL nas Preferências (Prefs), usa a salva.
 * - Caso contrário, usa o valor padrão DEFAULT_BASE_URL.
 *
 * Para trocar o domínio padrão pré-embutido no APK, altere apenas DEFAULT_BASE_URL.
 */
object PanelEndpoints {

    /** <<< AJUSTE AQUI se quiser embutir uma URL padrão no APK >>> */
    private const val DEFAULT_BASE_URL: String =
        "https://iboplus.motanetplay.top"  // exemplo; troque pelo seu domínio do painel

    /**
     * Retorna a base URL efetiva (preferências do usuário, senão a padrão).
     */
    fun baseUrl(context: Context): String {
        val saved = Prefs.getString(context, Prefs.KEY_PAINEL_URL)?.trim().orEmpty()
        return (saved.ifEmpty { DEFAULT_BASE_URL }).removeSuffix("/")
    }

    /**
     * Constrói uma URL absoluta garantindo as barras corretas.
     * @param context Context para ler preferências
     * @param path caminho relativo começando SEM barra (ex.: "api/check.php")
     * @param query pares de query string opcionais
     */
    fun url(context: Context, path: String, query: Map<String, String> = emptyMap()): String {
        val base = baseUrl(context)
        val builder = Uri.parse("$base/$path").buildUpon()
        query.forEach { (k, v) -> builder.appendQueryParameter(k, v) }
        return builder.build().toString()
    }

    // ---- Endpoints comuns do IBO Plus (ajuste nomes/rotas conforme a sua API) ----

    /** Tela de login por MAC/Chave ou inicialização */
    fun init(context: Context) = url(context, "api/init.php")

    /** Verifica/ativa dispositivo (envia mac + device_key) */
    fun activate(context: Context, mac: String, key: String) =
        url(context, "api/activate.php", mapOf("mac" to mac, "key" to key))

    /** Busca playlists/servidores liberados para o dispositivo */
    fun playlists(context: Context, mac: String) =
        url(context, "api/playlists.php", mapOf("mac" to mac))

    /** Carrega canais ao vivo */
    fun live(context: Context) = url(context, "api/live.php")

    /** Carrega catálogo de filmes */
    fun movies(context: Context, page: Int = 1) =
        url(context, "api/movies.php", mapOf("page" to page.toString()))

    /** Carrega catálogo de séries */
    fun series(context: Context, page: Int = 1) =
        url(context, "api/series.php", mapOf("page" to page.toString()))

    /** Busca genérica */
    fun search(context: Context, q: String) =
        url(context, "api/search.php", mapOf("q" to q))

    /** URL para abrir no WebView do painel (ex.: login via QR) */
    fun panelWeb(context: Context) = url(context, "loginqr.php")
}
