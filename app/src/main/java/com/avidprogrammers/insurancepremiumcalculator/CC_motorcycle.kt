package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by Abhishek on 26-Mar-17.
 */
class CC_motorcycle : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@CC_motorcycle)
        setContentView(R.layout.cc_motorcycle)
        supportActionBar!!.title = "Motorcycle"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Obtain the FirebaseAnalytics instance.
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_motorcycle")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.motorcycle_upto75).setOnClickListener(
            listener_upto75
        )
        findViewById<View>(R.id.motorcycle_upto150).setOnClickListener(listener_upto150)
        findViewById<View>(R.id.motorcycle_upto350).setOnClickListener(listener_upto350)
        findViewById<View>(R.id.motorcycle_above350).setOnClickListener(listener_above350)
    }

    var listener_upto75 = View.OnClickListener {
        val intent = Intent(this@CC_motorcycle, pt_motorcycle_upto75::class.java)
        startActivity(intent)
    }
    var listener_upto150 = View.OnClickListener {
        val intent = Intent(this@CC_motorcycle, pt_motorcycle_upto150::class.java)
        startActivity(intent)
    }
    var listener_upto350 = View.OnClickListener {
        val intent = Intent(this@CC_motorcycle,
            pt_motorcycle_upto350::class.java)
        startActivity(intent)
    }
    var listener_above350 = View.OnClickListener {
        val intent = Intent(this@CC_motorcycle, pt_motorcycle_350above::class.java)
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

    companion object {
        private const val TAG = "CC_motorcycle"
    }
}