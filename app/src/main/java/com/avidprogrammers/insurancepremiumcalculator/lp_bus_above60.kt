package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.ads.InterstitialAdManager
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView

/**
 * Created by Abhishek on 26-Mar-17.
 */
class lp_bus_above60 : AppCompatActivity(), View.OnClickListener, ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var lp_bus_above60btn: Button? = null
    var lp_bus_above60_act: EditText? = null
    var lp_bus_above60_paod: EditText? = null
    var lp_bus_above60_tax: EditText? = null
    var lp_bus_scpassengers_above60: EditText? = null
    var lp_bus_driver_above60: EditText? = null
    var lp_bus_conductor_above60: EditText? = null

    private var interstitialAdManager: InterstitialAdManager? = null

    protected override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    override fun onPause() {
        super.onPause()
        interstitialAdManager!!.showInterstitial(this)
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@lp_bus_above60)
        setContentView(R.layout.lp_bus_above60)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Passenger Vehicle Liability Policy")

        interstitialAdManager = InterstitialAdManager(this)

        findViewById<View>(R.id.lp_bus_above60btn).setOnClickListener(
            listener_lp_bus_above60btn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        lp_bus_above60btn!!.setOnClickListener(this)
        //stop-passthevalues
        val pa_no = findViewById<View>(R.id.lp_bus_above60_paod_value_no) as RadioButton
        pa_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_bus_above60_paod) as EditText
            ed1.setText("0")
            ed1.isEnabled = false
        }
        val pa_yes = findViewById<View>(R.id.lp_bus_above60_paod_value_yes) as RadioButton
        pa_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_bus_above60_paod) as EditText
            ed1.setText("320")
            ed1.isEnabled = true
        }
    }

    var listener_lp_bus_above60btn = View.OnClickListener {
        val intent = Intent(this@lp_bus_above60, lpdisplay_bus_above60::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        lp_bus_above60btn = findViewById<View>(R.id.lp_bus_above60btn) as Button?
        lp_bus_above60_act = findViewById<View>(R.id.lp_bus_above60_act) as EditText?
        lp_bus_above60_paod = findViewById<View>(R.id.lp_bus_above60_paod) as EditText?
        lp_bus_above60_tax = findViewById<View>(R.id.lp_bus_above60_tax) as EditText?
        lp_bus_scpassengers_above60 =
            findViewById<View>(R.id.lp_bus_scpassengers_above60) as EditText?
        lp_bus_driver_above60 = findViewById<View>(R.id.lp_bus_driver_above60) as EditText?
        lp_bus_conductor_above60 = findViewById<View>(R.id.lp_bus_conductor_above60) as EditText?
    }

    //calculation
    fun value(): Int {
        var Totalcost = 14343.0
        if (!lp_bus_scpassengers_above60!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            if (!lp_bus_driver_above60!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                if (!lp_bus_conductor_above60!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                    Totalcost = (14343 + lp_bus_above60_paod!!.text.toString().toInt() +
                            lp_bus_conductor_above60!!.text.toString().toInt() * 50
                            + lp_bus_scpassengers_above60!!.text.toString().toInt() * 877
                            + lp_bus_driver_above60!!.text.toString().toInt() * 50).toDouble()
                    Totalcost = Totalcost * 1.18
                    return Math.ceil(Totalcost).toInt()
                } else {
                    lp_bus_conductor_above60!!.error = "Empty field"
                }
            } else {
                lp_bus_driver_above60!!.error = "Empty Field"
            }
        } else {
            lp_bus_scpassengers_above60!!.error = "Empty Field"
        }
        return 0
    }

    override fun onClick(v: View) {
        val x = value()
        if (x != 0) {
            val intent = Intent(getApplicationContext(), lpdisplay_bus_above60::class.java)
            //Create a bundle object
            val b = Bundle()

            //Inserts a String value into the mapping of this Bundle
            b.putString("lp_bus_above60_act", lp_bus_above60_act!!.text.toString())
            b.putString("lp_bus_above60_paod", lp_bus_above60_paod!!.text.toString())
            b.putString("lp_bus_above60_tax", lp_bus_above60_tax!!.text.toString())
            b.putString(
                "lp_bus_scpassengers_above60",
                lp_bus_scpassengers_above60!!.text.toString()
            )
            b.putString("lp_bus_driver_above60", lp_bus_driver_above60!!.text.toString())
            b.putString("lp_bus_conductor_above60", lp_bus_conductor_above60!!.text.toString())
            //final value with tax
            b.putString("lp_bus_total_premium", x.toString())
            //Add the bundle to the intent.
            intent.putExtras(b)

            //start the DisplayActivity
            startActivity(intent)
        } else {
            Toast.makeText(this@lp_bus_above60, "Empty fields", Toast.LENGTH_SHORT).show()
        }
    }

    //stop-passthevalues
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

    //BackButton in title bar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}