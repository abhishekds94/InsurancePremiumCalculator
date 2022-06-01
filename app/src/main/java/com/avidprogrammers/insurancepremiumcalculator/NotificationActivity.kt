package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avidprogrammers.app.Config
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.avidprogrammers.utils.NotifiationAdapter
import com.google.android.gms.ads.AdView

class NotificationActivity : AppCompatActivity(), ConnectivityReceiverListener {
    private val mAdView: AdView? = null
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: NotifiationAdapter? = null
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        val pref: SharedPreferences =
            getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0)
        val editor = pref.edit()
        editor.putInt("newNoti", 0)
        editor.commit()
        //setTitle("Notifications");
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@NotificationActivity)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/recyclerView =
            findViewById<RecyclerView>(R.id.notification_recyclerview)
        layoutManager = LinearLayoutManager(this)
        adapter = NotifiationAdapter(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = adapter
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        checkingStatus!!.notification(isConnected, this)
    }

    protected override fun onResume() {
        super.onResume()
        MyApplication.Companion.instance!!.setConnectivityListener(this)
    }

    protected override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    fun checkfunction(context: Context?) {
        val isConnected: Boolean = ConnectivityReceiver.Companion.isConnected
        //notification(isConnected,lp_taxi_upto18pass.this);
        checkingStatus!!.notification(isConnected, context!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this@NotificationActivity, home_activity::class.java))
                finish()
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@NotificationActivity, home_activity::class.java))
        finish()
    }
}