package com.iboplus.app.data

import android.content.Context

class Repo(ctx: Context, painelBaseUrl: String) {
    private val prefs = Prefs(ctx)
    private val endpoints = PanelEndpoints(painelBaseUrl)
    private val api = ApiClient(endpoints)

    fun savePainel(url: String) { prefs.painelUrl = url }
    fun getPainel(): String? = prefs.painelUrl

    fun checkPing() = api.ping()
}
