package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import android.os.Bundle
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.google.firebase.analytics.FirebaseAnalytics
import android.content.Intent
import com.avidprogrammers.insurancepremiumcalculatorimport.longterm_CC_car

/**
 * Created by Abhishek on 26-Mar-17.
 */
class longterm_vehicle : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@longterm_vehicle)
        setContentView(R.layout.longterm_vehicle)
        //        Toast.makeText(this, "longterm_vehicle", Toast.LENGTH_SHORT).show();
        getSupportActionBar()!!.setTitle("Longterm Policy Type")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        // Obtain the FirebaseAnalytics instance.
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_longterm")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.longterm_motorcycle).setOnClickListener(
            listener_longterm_motorcycle
        )
        findViewById<View>(R.id.longterm_car).setOnClickListener(listener_longterm_car)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    var listener_longterm_motorcycle = View.OnClickListener {
        val intent = Intent(this@longterm_vehicle, longterm_CC_motorcycle::class.java)
        startActivity(intent)
    }
    var listener_longterm_car = View.OnClickListener {
        val intent = Intent(this@longterm_vehicle, longterm_CC_car::class.java)
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
        private const val TAG = "longterm_vehicle"
    }
}