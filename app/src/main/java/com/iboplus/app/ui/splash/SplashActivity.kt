package com.iboplus.app.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.iboplus.app.R
import com.iboplus.app.ui.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Usa tema de splash na criação; depois troca para o tema normal
        setTheme(R.style.Theme_IBOPlus_Splash)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Simples: espera 500ms e navega. Troca para o tema normal.
        lifecycleScope.launch {
            delay(500)
            setTheme(R.style.Theme_IBOPlus) // aplica o tema “real”
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            finish()
        }
    }
}
