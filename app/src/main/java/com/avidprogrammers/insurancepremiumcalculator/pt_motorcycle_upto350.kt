package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.avidprogrammers.insurancepremiumcalculator.pp_motorcycle_upto350
import com.google.android.gms.ads.AdView

/**
 * Created by Abhishek on 26-Mar-17.
 */
class pt_motorcycle_upto350 : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@pt_motorcycle_upto350)
        setContentView(R.layout.pt_motorcycle_upto350)
        getSupportActionBar()!!.setTitle("Motorcycle Policy Type")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.motorcycle_upto350_pp).setOnClickListener(
            listener_motorcycle_upto350_pp
        )
        findViewById<View>(R.id.motorcycle_upto350_lp).setOnClickListener(
            listener_motorcycle_upto350_lp
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    var listener_motorcycle_upto350_pp = View.OnClickListener {
        val intent = Intent(this@pt_motorcycle_upto350, pp_motorcycle_upto350::class.java)
        startActivity(intent)
    }
    var listener_motorcycle_upto350_lp = View.OnClickListener {
        val intent = Intent(this@pt_motorcycle_upto350, lp_motorcycle_calc_upto350::class.java)
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
        private const val TAG = "pt_motorcycle_upto350"
    }
}