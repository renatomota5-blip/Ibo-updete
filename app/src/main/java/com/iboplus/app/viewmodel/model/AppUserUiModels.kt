package com.iboplus.app.viewmodel.model

/**
 * Modelos de UI relacionados ao usuário do aplicativo
 * (amarrado pelo MAC + Chave e ativado via painel).
 */

/** Representa o usuário do app retornado pelo painel. */
data class AppUserUi(
    val mac: String,
    val deviceKey: String,
    val isActive: Boolean,
    val expiryDate: String? = null, // data de expiração da assinatura
    val planName: String? = null    // nome do plano/servidor ativo
)

/** Estado exposto para telas de perfil/conta. */
data class AppUserUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val user: AppUserUi? = null
)
