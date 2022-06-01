package com.avidprogrammers.insurancepremiumcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener

class WebViewActivity : AppCompatActivity(), ConnectivityReceiverListener {
    var webView: WebView? = null
    var bundle: Bundle? = null
    var URL: String? = null
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    @SuppressLint("JavascriptInterface")
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById<WebView>(R.id.webview)
        bundle = getIntent().getExtras()
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@WebViewActivity)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        if (bundle != null) {
            URL = bundle!!.getString("url")
            webView!!.loadUrl(URL!!)
            webView!!.settings.javaScriptEnabled = true
            webView!!.settings.loadWithOverviewMode = true
            webView!!.settings.useWideViewPort = true
            webView!!.settings.pluginState = WebSettings.PluginState.ON
            webView!!.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
            webView!!.settings.cacheMode = WebSettings.LOAD_DEFAULT
            webView!!.settings.savePassword = false
            webView!!.settings.domStorageEnabled = true
            webView!!.isHorizontalScrollBarEnabled = false
            webView!!.addJavascriptInterface(this, "Android")
            webView!!.webChromeClient = WebChromeClient()
            webView!!.webViewClient = WebViewClient()
        } else {
            Toast.makeText(this@WebViewActivity, "Please Try Again", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun checkfunction(context: Context?) {
        val isConnected: Boolean = ConnectivityReceiver.Companion.isConnected
        //notification(isConnected,lp_taxi_upto18pass.this);
        checkingStatus!!.notification(isConnected, context!!)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        checkingStatus!!.notification(isConnected, this)
    }

    protected override fun onResume() {
        super.onResume()
        MyApplication.Companion.instance!!.setConnectivityListener(this)
    }

    protected override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}