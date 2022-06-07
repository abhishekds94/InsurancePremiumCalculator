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
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.ads.InterstitialAdManager
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView

/**
 * Created by Abhishek on 26-Mar-17.
 */
class lp_car_above1500 : AppCompatActivity(), View.OnClickListener, ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var lp_car_above1500btn: Button? = null
    var lp_car_above1500_act: EditText? = null
    var lp_car_above1500_paod: EditText? = null
    var lp_car_above1500_ll: EditText? = null
    var lp_car_above1500_tax: EditText? = null
    var lp_car_above1500_lpgkit: RadioGroup? = null

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
        checkfunction(this@lp_car_above1500)
        setContentView(R.layout.lp_car_above1500)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Car Liability Policy")

        interstitialAdManager = InterstitialAdManager(this)

        findViewById<View>(R.id.lp_car_above1500btn).setOnClickListener(
            listener_lp_car_above1500btn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        lp_car_above1500btn!!.setOnClickListener(this)
        //stop-passthevalues
        val pa_no = findViewById<View>(R.id.lp_car_above1500_paod_value_no) as RadioButton
        pa_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_car_above1500_paod) as EditText
            ed1.setText("0")
            ed1.isEnabled = false
        }
        val pa_yes = findViewById<View>(R.id.lp_car_above1500_paod_value_yes) as RadioButton
        pa_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_car_above1500_paod) as EditText
            ed1.setText("320")
            ed1.isEnabled = true
        }
    }

    var listener_lp_car_above1500btn = View.OnClickListener {
        val intent = Intent(this@lp_car_above1500, lpdisplay_car_above1500::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        lp_car_above1500btn = findViewById<View>(R.id.lp_car_above1500btn) as Button?
        lp_car_above1500_act = findViewById<View>(R.id.lp_car_above1500_act) as EditText?
        lp_car_above1500_paod = findViewById<View>(R.id.lp_car_above1500_paod) as EditText?
        lp_car_above1500_ll = findViewById<View>(R.id.lp_car_above1500_ll) as EditText?
        lp_car_above1500_tax = findViewById<View>(R.id.lp_car_above1500_tax) as EditText?
        lp_car_above1500_lpgkit = findViewById<View>(R.id.lp_car_above1500_lpgkit) as RadioGroup?
    }

    override fun onClick(v: View) {
        val intent = Intent(getApplicationContext(), lpdisplay_car_above1500::class.java)
        //Create a bundle object
        val b = Bundle()

        //Inserts a String value into the mapping of this Bundle
        b.putString("lp_car_above1500_act", lp_car_above1500_act!!.text.toString())
        b.putString("lp_car_above1500_paod", lp_car_above1500_paod!!.text.toString())
        b.putString("lp_car_above1500_ll", lp_car_above1500_ll!!.text.toString())
        b.putString("lp_car_above1500_tax", lp_car_above1500_tax!!.text.toString())
        val id1 = lp_car_above1500_lpgkit!!.checkedRadioButtonId
        val radioButton1 = findViewById<View>(id1) as RadioButton
        b.putString("lp_car_above1500_lpgkit", radioButton1.text.toString())


        //Add the bundle to the intent.
        intent.putExtras(b)

        //start the DisplayActivity
        startActivity(intent)
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