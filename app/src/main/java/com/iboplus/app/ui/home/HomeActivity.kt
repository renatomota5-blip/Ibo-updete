package com.iboplus.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.iboplus.app.R
import com.iboplus.app.ui.live.LiveActivity
import com.iboplus.app.ui.servers.ServersActivity
import com.iboplus.app.ui.settings.SettingsActivity
import com.iboplus.app.ui.vod.MoviesActivity
import com.iboplus.app.ui.vod.SeriesActivity
import com.iboplus.app.ui.web.PanelWebViewActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btnPanel).setOnClickListener {
            startActivity(Intent(this, PanelWebViewActivity::class.java))
        }
        findViewById<Button>(R.id.btnLive).setOnClickListener {
            startActivity(Intent(this, LiveActivity::class.java))
        }
        findViewById<Button>(R.id.btnMovies).setOnClickListener {
            startActivity(Intent(this, MoviesActivity::class.java))
        }
        findViewById<Button>(R.id.btnSeries).setOnClickListener {
            startActivity(Intent(this, SeriesActivity::class.java))
        }
        findViewById<Button>(R.id.btnServers).setOnClickListener {
            startActivity(Intent(this, ServersActivity::class.java))
        }
        findViewById<Button>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
