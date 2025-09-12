package com.iboplus.app.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.iboplus.app.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_IBOPlus) // garante que sai do tema de splash
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Toast.makeText(this, "Home carregada", Toast.LENGTH_SHORT).show()
    }
}
