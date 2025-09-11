
package com.iboplus.app.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iboplus.app.R
import com.iboplus.app.data.Repo
import com.iboplus.app.ui.home.HomeActivity

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val repo = Repo(this)
        repo.ensureIds()
        window.decorView.postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 800)
    }
}
