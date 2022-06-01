package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView

/**
 * Created by Abhishek on 04-Jan-18.
 */
class terms : AppCompatActivity(), ConnectivityReceiverListener {
    private var myWebView: WebView? = null
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    protected override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@terms)
        setContentView(R.layout.terms)
        getSupportActionBar()!!.setTitle("Terms & Conditions")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        val url: String = getIntent().getStringExtra("url")!!
        myWebView = findViewById<View>(R.id.webview) as WebView?
        myWebView!!.webViewClient = WebViewClient()
        val webSettings = myWebView!!.settings
        webSettings.javaScriptEnabled = true
        myWebView!!.loadUrl(url)


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);

        interstitialAd.setAdUnitId(AD_UNIT_ID);
        AdRequest aDRequest = new AdRequest.Builder().build();

        interstitialAd.loadAd(aDRequest);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }

            }

            @Override
            public void onAdOpened() {


            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }
        });*/
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    //checking connectivity
    fun checkfunction(context: Context?) {
        val isConnected: Boolean = ConnectivityReceiver.Companion.isConnected
        //notification(isConnected,lp_taxi_upto18pass.this);
        checkingStatus!!.notification(isConnected, context!!)
    }

    protected override fun onResume() {
        super.onResume()
        MyApplication.Companion.instance!!.setConnectivityListener(this)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        checkfunction(this)
    }

    companion object {
        private const val TAG = "pt_agri"
    }
}