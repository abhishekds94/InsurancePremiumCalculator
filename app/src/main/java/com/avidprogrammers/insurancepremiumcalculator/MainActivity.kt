package com.avidprogrammers.insurancepremiumcalculator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.avidprogrammers.ads.AppOpenManager
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener


/**
 * Created by Abhishek on 25-Aug-17.
 */
class MainActivity : Activity(), ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    var appOpenManager: AppOpenManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.getBooleanExtra("EXIT", false)) {
            finishAndRemoveTask()
        }
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@MainActivity)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        appOpenManager = AppOpenManager(applicationContext as MyApplication)

        splash()
    }

    fun checkfunction(context: Context?) {
        val isConnected: Boolean = ConnectivityReceiver.Companion.isConnected
        checkingStatus!!.notification(isConnected, context!!)
        if (isConnected) {
            MainActivity.Companion.status = 1
        } else {
            MainActivity.Companion.status = 0
        }
    }

    fun splash() {
        Handler().postDelayed({ // This method will be executed once the timer is over
            // Start your app main activity
            if (MainActivity.Companion.status == 1) {
                val i = Intent(this@MainActivity, home_activity::class.java)
                startActivity(i)
                finish()
                appOpenManager!!.showAdIfAvailable()
            } else {
                Toast.makeText(this@MainActivity, "Check Internet Connection !", Toast.LENGTH_SHORT)
                    .show()
            }
        }, MainActivity.Companion.SPLASH_TIME_OUT.toLong())
    }

    override fun onResume() {
        super.onResume()
        MyApplication.Companion.instance!!.setConnectivityListener(this)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        checkingStatus!!.notification(isConnected, this)
        if (isConnected) {
            MainActivity.Companion.status = 1
        } else {
            MainActivity.Companion.status = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    companion object {
        var status = 1

        // Splash screen timer
        private const val SPLASH_TIME_OUT = 4000
    }
}