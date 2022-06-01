package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.avidprogrammers.insurancepremiumcalculatorimport.*
import com.google.android.gms.ads.AdView
import com.google.firebase.analytics.FirebaseAnalytics


/**
 * Created by Abhishek on 26-Mar-17.
 */
class CC_car : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@CC_car)
        setContentView(R.layout.cc_car)
        supportActionBar!!.title = "Private Car"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        // Obtain the FirebaseAnalytics instance.
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_car")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        findViewById<View>(R.id.car_upto1000).setOnClickListener(listener_car_upto1000)
        findViewById<View>(R.id.car_upto1500).setOnClickListener(listener_car_upto1500)
        findViewById<View>(R.id.car_above1500).setOnClickListener(listener_car_above1500)
    }

    var listener_car_upto1000 = View.OnClickListener {
        val intent = Intent(this@CC_car, pt_car_upto1000::class.java)
        startActivity(intent)
    }
    var listener_car_upto1500 = View.OnClickListener {
        val intent = Intent(this@CC_car, pt_car_upto1500::class.java)
        startActivity(intent)
    }
    var listener_car_above1500 = View.OnClickListener {
        val intent = Intent(this@CC_car, pt_car_above1500::class.java)
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
        private const val TAG = "CC_car"
    }
}