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

/**
 * Created by Abhishek on 26-Mar-17.
 */
class longterm_CC_motorcycle : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@longterm_CC_motorcycle)
        setContentView(R.layout.longterm_cc_motorcycle)
        //        Toast.makeText(this, "longterm_cc_motorcycle", Toast.LENGTH_SHORT).show();
        getSupportActionBar()!!.setTitle("Motorcycle")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.lt_motorcycle_upto75).setOnClickListener(
            listener_upto75
        )
        findViewById<View>(R.id.lt_motorcycle_upto150).setOnClickListener(listener_upto150)
        findViewById<View>(R.id.lt_motorcycle_upto350).setOnClickListener(listener_upto350)
        findViewById<View>(R.id.lt_motorcycle_above350).setOnClickListener(listener_above350)
    }

    var listener_upto75 = View.OnClickListener {
        val intent = Intent(this@longterm_CC_motorcycle, longterm_pt_motorcycle_upto75::class.java)
        startActivity(intent)
    }
    var listener_upto150 = View.OnClickListener {
        val intent = Intent(this@longterm_CC_motorcycle, longterm_pt_motorcycle_upto150::class.java)
        startActivity(intent)
    }
    var listener_upto350 = View.OnClickListener {
        val intent = Intent(this@longterm_CC_motorcycle, longterm_pt_motorcycle_upto350::class.java)
        startActivity(intent)
    }
    var listener_above350 = View.OnClickListener {
        val intent =
            Intent(this@longterm_CC_motorcycle, longterm_pt_motorcycle_350above::class.java)
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