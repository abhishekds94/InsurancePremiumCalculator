package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.avidprogrammers.insurancepremiumcalculator.pp_agri
import com.google.android.gms.ads.AdView
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by Abhishek on 26-Mar-17.
 */
class pt_agri : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@pt_agri)
        setContentView(R.layout.pt_agri)
        getSupportActionBar()!!.setTitle("Tractors & Trailers Policy Type")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        // Obtain the FirebaseAnalytics instance.
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_agri")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.pp_agri).setOnClickListener(
            listener_pp_agri
        )
        findViewById<View>(R.id.lp_agri).setOnClickListener(listener_lp_agri)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    var listener_pp_agri = View.OnClickListener {
        val intent = Intent(this@pt_agri, pp_agri::class.java)
        startActivity(intent)
    }
    var listener_lp_agri = View.OnClickListener {
        val intent = Intent(this@pt_agri, lp_agri::class.java)
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
        private const val TAG = "pt_agri"
    }
}