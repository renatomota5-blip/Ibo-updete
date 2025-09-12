package com.iboplus.app.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iboplus.app.ui.web.PanelWebViewActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Opcional: setContentView(R.layout.activity_splash)
        // Partimos direto pro WebView do painel
        startActivity(Intent(this, PanelWebViewActivity::class.java))
        finish()
    }
}
