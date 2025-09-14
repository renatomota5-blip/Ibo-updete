package com.iboplus.app.data.local

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Inicializador leve chamado no App.onCreate().
 * Aqui você pode disparar pré-carregamentos (logo/bg/tema) se quiser.
 * Mantive simples para não travar o startup.
 */
object ConfigLoader {

    fun init(context: Context) {
        // Exemplo: operações assíncronas rápidas
        CoroutineScope(Dispatchers.IO).launch {
            // Placeholder: nada crítico aqui por enquanto.
            // Se quiser, pode ler DataStore ou disparar um warm-up.
        }
    }
}
