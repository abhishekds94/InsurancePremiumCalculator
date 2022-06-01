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
class CC_commercialvehicleprivate : AppCompatActivity(), ConnectivityReceiverListener {
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
        checkfunction(this@CC_commercialvehicleprivate)
        setContentView(R.layout.cc_commercialvehicleprivate)
        supportActionBar!!.title = "Private Commercial Vehicle"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Obtain the FirebaseAnalytics instance.
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_comvehicle_private")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.comvehicle_private_upto7500).setOnClickListener(
            listener_comvehicle_private_upto7500
        )
        findViewById<View>(R.id.comvehicle_private_upto12000).setOnClickListener(
            listener_comvehicle_private_upto12000
        )
        findViewById<View>(R.id.comvehicle_private_upto20000).setOnClickListener(
            listener_comvehicle_private_upto20000
        )
        findViewById<View>(R.id.comvehicle_private_upto40000).setOnClickListener(
            listener_comvehicle_private_upto40000
        )
        findViewById<View>(R.id.comvehicle_private_above40000).setOnClickListener(
            listener_comvehicle_private_40000above
        )
    }

    var listener_comvehicle_private_upto7500 = View.OnClickListener {
        val intent = Intent(
            this@CC_commercialvehicleprivate,
            pt_commercialvehicleprivate_upto7500::class.java
        )
        startActivity(intent)
    }
    var listener_comvehicle_private_upto12000 = View.OnClickListener {
        val intent = Intent(
            this@CC_commercialvehicleprivate,
            pt_commercialvehicleprivate_upto12000::class.java
        )
        startActivity(intent)
    }
    var listener_comvehicle_private_upto20000 = View.OnClickListener {
        val intent = Intent(
            this@CC_commercialvehicleprivate,
            pt_commercialvehicleprivate_upto20000::class.java
        )
        startActivity(intent)
    }
    var listener_comvehicle_private_upto40000 = View.OnClickListener {
        val intent = Intent(
            this@CC_commercialvehicleprivate,
            pt_commercialvehicleprivate_upto40000::class.java
        )
        startActivity(intent)
    }
    var listener_comvehicle_private_40000above = View.OnClickListener {
        val intent = Intent(
            this@CC_commercialvehicleprivate,
            pt_commercialvehicleprivate_40000above::class.java
        )
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
        private const val TAG = "CC_commercialvehicleprivate"
    }
}