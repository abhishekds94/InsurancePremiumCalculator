package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.*

/**
 * Created by Abhishek on 26-Mar-17.
 */
class longterm_CC_car : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@longterm_CC_car)
        setContentView(R.layout.longterm_cc_car)
        //        Toast.makeText(this, "longterm_cc_motorcycle", Toast.LENGTH_SHORT).show();
        getSupportActionBar()!!.setTitle("Motorcycle")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.lt_car_upto1000).setOnClickListener(
            listener_upto1000
        )
        findViewById<View>(R.id.lt_car_upto1500).setOnClickListener(listener_upto1500)
        findViewById<View>(R.id.lt_car_above1500).setOnClickListener(listener_above1500)
    }

    var listener_upto1000 = View.OnClickListener {
        val intent = Intent(this@longterm_CC_car, longterm_pt_car_upto1000::class.java)
        startActivity(intent)
    }
    var listener_upto1500 = View.OnClickListener {
        val intent = Intent(this@longterm_CC_car, longterm_pt_car_upto1500::class.java)
        startActivity(intent)
    }
    var listener_above1500 = View.OnClickListener {
        val intent = Intent(this@longterm_CC_car, longterm_pt_car_above1500::class.java)
        startActivity(intent)
    }

    fun checkfunction(context: Context?) {
        val isConnected: Boolean = ConnectivityReceiver.Companion.isConnected
        checkingStatus!!.notification(isConnected, context!!)
    }

    protected override fun onResume() {
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