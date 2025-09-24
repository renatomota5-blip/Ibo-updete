package com.iboplus.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Home apenas com background (nenhum botão ainda)
        setContentView(R.layout.activity_home)
    }
}
