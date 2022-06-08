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
class lp_com_goods_private_40000above : AppCompatActivity(), View.OnClickListener,
    ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var lp_com_goods_private_40000abovebtn: Button? = null
    var lp_com_goods_private_40000above_act: EditText? = null
    var lp_com_goods_private_40000above_paod: EditText? = null
    var lp_com_goods_private_40000above_ll: EditText? = null
    var lp_com_goods_private_40000above_tax: EditText? = null
    var lp_com_goods_private_40000above_coolie: EditText? = null
    var lp_com_goods_private_40000above_nfpp: EditText? = null

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
        checkfunction(this@lp_com_goods_private_40000above)
        setContentView(R.layout.lp_com_goods_private_40000above)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Private Commercial Vehicle - Liability Policy")

        interstitialAdManager = InterstitialAdManager(this)

        findViewById<View>(R.id.lp_com_goods_private_40000abovebtn).setOnClickListener(
            listener_lp_com_goods_private_40000abovebtn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        lp_com_goods_private_40000abovebtn!!.setOnClickListener(this)
        val pa_no =
            findViewById<View>(R.id.lp_com_goods_private_40000above_paod_value_no) as RadioButton
        pa_no.setOnClickListener {
            val ed1 =
                findViewById<View>(R.id.lp_com_goods_private_40000above_paod_value) as EditText
            ed1.setText("0")
            ed1.isEnabled = false
        }
        val pa_yes =
            findViewById<View>(R.id.lp_com_goods_private_40000above_paod_value_yes) as RadioButton
        pa_yes.setOnClickListener {
            val ed1 =
                findViewById<View>(R.id.lp_com_goods_private_40000above_paod_value) as EditText
            ed1.setText("320")
            ed1.isEnabled = true
        }
        //stop-passthevalues
    }

    var listener_lp_com_goods_private_40000abovebtn = View.OnClickListener {
        val intent = Intent(
            this@lp_com_goods_private_40000above,
            lpdisplay_com_goods_private_40000above::class.java
        )
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        lp_com_goods_private_40000abovebtn =
            findViewById<View>(R.id.lp_com_goods_private_40000abovebtn) as Button?
        lp_com_goods_private_40000above_act =
            findViewById<View>(R.id.lp_com_goods_private_40000above_act) as EditText?
        lp_com_goods_private_40000above_paod =
            findViewById<View>(R.id.lp_com_goods_private_40000above_paod_value) as EditText?
        lp_com_goods_private_40000above_ll =
            findViewById<View>(R.id.lp_com_goods_private_40000above_ll) as EditText?
        lp_com_goods_private_40000above_tax =
            findViewById<View>(R.id.lp_com_goods_private_40000above_tax) as EditText?
        lp_com_goods_private_40000above_coolie =
            findViewById<View>(R.id.lp_com_goods_private_40000above_coolie) as EditText?
        lp_com_goods_private_40000above_nfpp =
            findViewById<View>(R.id.lp_com_goods_private_40000above_nfpp) as EditText?
    }

    override fun onClick(v: View) {
        val intent =
            Intent(getApplicationContext(), lpdisplay_com_goods_private_40000above::class.java)
        val b = Bundle()
        b.putString(
            "lp_com_goods_private_40000above_act",
            lp_com_goods_private_40000above_act!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_40000above_paod",
            lp_com_goods_private_40000above_paod!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_40000above_ll",
            lp_com_goods_private_40000above_ll!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_40000above_tax",
            lp_com_goods_private_40000above_tax!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_40000above_coolie",
            lp_com_goods_private_40000above_coolie!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_40000above_nfpp",
            lp_com_goods_private_40000above_nfpp!!.text.toString()
        )
        intent.putExtras(b)
        val coolie_value = lp_com_goods_private_40000above_coolie!!.text.toString()
        val nfpp_value = lp_com_goods_private_40000above_nfpp!!.text.toString()
        if (coolie_value == "" || nfpp_value == "") {
            Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT)
                .show()
        } else {
            startActivity(intent)
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