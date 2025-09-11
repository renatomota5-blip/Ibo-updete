
package com.iboplus.app.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.iboplus.app.R
import com.iboplus.app.data.Repo
import com.iboplus.app.ui.live.LiveActivity
import com.iboplus.app.ui.settings.SettingsActivity
import com.iboplus.app.ui.vod.MoviesActivity
import com.iboplus.app.ui.vod.SeriesActivity

class HomeActivity: AppCompatActivity() {
    private lateinit var repo: Repo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        repo = Repo(this)

        findViewById<Button>(R.id.btnLive).setOnClickListener {
            startActivity(Intent(this, LiveActivity::class.java))
        }
        findViewById<Button>(R.id.btnMovies).setOnClickListener {
            startActivity(Intent(this, MoviesActivity::class.java))
        }
        findViewById<Button>(R.id.btnSeries).setOnClickListener {
            startActivity(Intent(this, SeriesActivity::class.java))
        }
        findViewById<Button>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        findViewById<Button>(R.id.btnAccount).setOnClickListener {
            val mac = repo.mac()
            val key = repo.key()
            AlertDialog.Builder(this)
                .setTitle("conta")
                .setMessage("Endereço MAC\n$mac\n\nChave do dispositivo\n$key")
                .setPositiveButton("OK", null)
                .show()
        }
        findViewById<Button>(R.id.btnRefresh).setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Atualização solicitada (vincular ao painel).")
                .setPositiveButton("OK", null)
                .show()
        }
        findViewById<Button>(R.id.btnExit).setOnClickListener { finishAffinity() }
    }
}
