package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.*
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import com.google.firebase.analytics.FirebaseAnalytics


/**
 * Created by Abhishek on 26-Mar-17.
 */
class CC_bus : AppCompatActivity(), ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@CC_bus)
        setContentView(R.layout.cc_bus)
        supportActionBar!!.title = "Passenger Vehicle"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Obtain the FirebaseAnalytics instance.
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_bus")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.bus_upto18).setOnClickListener(
            listener_bus_upto18
        )
        findViewById<View>(R.id.bus_upto36).setOnClickListener(listener_bus_upto36)
        findViewById<View>(R.id.bus_upto60).setOnClickListener(listener_bus_upto60)
        findViewById<View>(R.id.bus_above60).setOnClickListener(listener_bus_above60)
    }

    var listener_bus_upto18 = View.OnClickListener {
        val intent = Intent(this@CC_bus, pt_bus_upto18::class.java)
        startActivity(intent)
    }
    var listener_bus_upto36 = View.OnClickListener {
        val intent = Intent(this@CC_bus, pt_bus_upto36::class.java)
        startActivity(intent)
    }
    var listener_bus_upto60 = View.OnClickListener {
        val intent = Intent(this@CC_bus, pt_bus_upto60::class.java)
        startActivity(intent)
    }
    var listener_bus_above60 = View.OnClickListener {
        val intent = Intent(this@CC_bus, pt_bus_above60::class.java)
        startActivity(intent)
    }

    fun checkfunction(context: Context?) {
        val isConnected: Boolean = ConnectivityReceiver.Companion.isConnected
        checkingStatus!!.notification(isConnected, context!!)
    }

    override fun onResume() {
        super.onResume()
        MyApplication.Companion.instance!!.setConnectivityListener(this)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        checkfunction(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}