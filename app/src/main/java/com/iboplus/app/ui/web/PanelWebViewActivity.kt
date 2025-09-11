package com.iboplus.app.ui.web
class PanelWebViewActivity: androidx.appcompat.app.AppCompatActivity(){override fun onCreate(b:android.os.Bundle?){super.onCreate(b);val wv=android.webkit.WebView(this);setContentView(wv);wv.settings.javaScriptEnabled=true;wv.settings.domStorageEnabled=true}}
