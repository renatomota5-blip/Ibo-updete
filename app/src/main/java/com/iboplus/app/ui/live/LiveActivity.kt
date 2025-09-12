package com.iboplus.app.ui.live

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.iboplus.app.R

class LiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_placeholder)
        title = "Canais (Live)"

        // Exemplo: botão “voltar”
        findViewById<Button>(R.id.btnAction).apply {
            text = "Voltar"
            setOnClickListener { finish() }
        }
    }
}
