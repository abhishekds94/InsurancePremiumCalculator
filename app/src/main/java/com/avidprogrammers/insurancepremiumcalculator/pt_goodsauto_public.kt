package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.avidprogrammers.insurancepremiumcalculator.pp_goodsauto_public
import com.google.android.gms.ads.AdView
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by Abhishek on 26-Mar-17.
 */
class pt_goodsauto_public : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@pt_goodsauto_public)
        setContentView(R.layout.pt_goodsauto_public)
        getSupportActionBar()!!.setTitle("Public Goods Auto Policy Type")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        // Obtain the FirebaseAnalytics instance.
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_goodsauto_public")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.pp_goodsauto_public).setOnClickListener(
            listener_pp_goodsauto_public
        )
        findViewById<View>(R.id.lp_goodsauto_public).setOnClickListener(listener_lp_goodsauto_public)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    var listener_pp_goodsauto_public = View.OnClickListener {
        val intent = Intent(this@pt_goodsauto_public, pp_goodsauto_public::class.java)
        startActivity(intent)
    }
    var listener_lp_goodsauto_public = View.OnClickListener {
        val intent = Intent(this@pt_goodsauto_public, lp_goodsauto_public::class.java)
        startActivity(intent)
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
        private const val TAG = "pt_goodsauto_public"
    }
}