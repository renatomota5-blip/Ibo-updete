
package com.iboplus.app.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.iboplus.app.data.ApiClient
import com.iboplus.app.data.PanelEndpoints

class PanelWebViewActivity: AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val wv = WebView(this)
        setContentView(wv)
        val s: WebSettings = wv.settings
        s.javaScriptEnabled = true
        s.domStorageEnabled = true
        s.mediaPlaybackRequiresUserGesture = false
        wv.settings.userAgentString = ApiClient.defaultUA()
        wv.loadUrl(PanelEndpoints.BASE)
    }
}
