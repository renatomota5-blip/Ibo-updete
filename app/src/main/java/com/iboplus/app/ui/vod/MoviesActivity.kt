package com.iboplus.app.ui.vod

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.iboplus.app.R

class MoviesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_placeholder)
        title = "Filmes (VOD)"
        findViewById<Button>(R.id.btnAction).setOnClickListener { finish() }
    }
}
