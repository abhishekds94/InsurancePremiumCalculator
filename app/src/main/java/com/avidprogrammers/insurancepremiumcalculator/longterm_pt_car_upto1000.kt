package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import android.os.Bundle
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.content.Intent
import android.widget.Toast
/**
 * Created by Abhishek on 26-Mar-17.
 */
class longterm_pt_car_upto1000 : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@longterm_pt_car_upto1000)
        setContentView(R.layout.longterm_pt_car_upto1000)
        Toast.makeText(this, "longterm_pt_car_upto1000", Toast.LENGTH_SHORT).show()
        supportActionBar!!.title = "Car Policy Type"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.lt_car_upto1000_bp).setOnClickListener(
            listener_lt_car_upto1000_bp
        )
        findViewById<View>(R.id.lt_car_upto1000_pp).setOnClickListener(listener_lt_car_upto1000_pp)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    var listener_lt_car_upto1000_bp = View.OnClickListener {
        val intent = Intent(this@longterm_pt_car_upto1000, longterm_bp_car_upto1000::class.java)
        startActivity(intent)
    }
    var listener_lt_car_upto1000_pp = View.OnClickListener {
        val intent = Intent(this@longterm_pt_car_upto1000, longterm_pp_car_upto1000::class.java)
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
        private const val TAG = "pt_car_upto1000"
    }
}