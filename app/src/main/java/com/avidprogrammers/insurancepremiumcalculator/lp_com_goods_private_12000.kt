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
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView

/**
 * Created by Abhishek on 26-Mar-17.
 */
class lp_com_goods_private_12000 : AppCompatActivity(), View.OnClickListener,
    ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var lp_com_goods_private_12000btn: Button? = null
    var lp_com_goods_private_12000_act: EditText? = null
    var lp_com_goods_private_12000_paod: EditText? = null
    var lp_com_goods_private_12000_ll: EditText? = null
    var lp_com_goods_private_12000_tax: EditText? = null
    var lp_com_goods_private_12000_coolie: EditText? = null
    var lp_com_goods_private_12000_nfpp: EditText? = null
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
        checkfunction(this@lp_com_goods_private_12000)
        setContentView(R.layout.lp_com_goods_private_12000)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Private Commercial Vehicle - Liability Policy")

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.lp_com_goods_private_12000btn).setOnClickListener(
            listener_lp_com_goods_private_12000btn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        lp_com_goods_private_12000btn!!.setOnClickListener(this)
        val pa_no = findViewById<View>(R.id.lp_com_goods_private_12000_paod_value_no) as RadioButton
        pa_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_com_goods_private_12000_paod_value) as EditText
            ed1.setText("0")
            ed1.isEnabled = false
        }
        val pa_yes =
            findViewById<View>(R.id.lp_com_goods_private_12000_paod_value_yes) as RadioButton
        pa_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_com_goods_private_12000_paod_value) as EditText
            ed1.setText("320")
            ed1.isEnabled = true
        }
        //stop-passthevalues
    }

    var listener_lp_com_goods_private_12000btn = View.OnClickListener {
        val intent =
            Intent(this@lp_com_goods_private_12000, lpdisplay_com_goods_private_12000::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        lp_com_goods_private_12000btn =
            findViewById<View>(R.id.lp_com_goods_private_12000btn) as Button?
        lp_com_goods_private_12000_act =
            findViewById<View>(R.id.lp_com_goods_private_12000_act) as EditText?
        lp_com_goods_private_12000_paod =
            findViewById<View>(R.id.lp_com_goods_private_12000_paod_value) as EditText?
        lp_com_goods_private_12000_ll =
            findViewById<View>(R.id.lp_com_goods_private_12000_ll) as EditText?
        lp_com_goods_private_12000_tax =
            findViewById<View>(R.id.lp_com_goods_private_12000_tax) as EditText?
        lp_com_goods_private_12000_coolie =
            findViewById<View>(R.id.lp_com_goods_private_12000_coolie) as EditText?
        lp_com_goods_private_12000_nfpp =
            findViewById<View>(R.id.lp_com_goods_private_12000_nfpp) as EditText?
    }

    override fun onClick(v: View) {
        val intent = Intent(getApplicationContext(), lpdisplay_com_goods_private_12000::class.java)
        val b = Bundle()
        b.putString(
            "lp_com_goods_private_12000_act",
            lp_com_goods_private_12000_act!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_12000_paod",
            lp_com_goods_private_12000_paod!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_12000_ll",
            lp_com_goods_private_12000_ll!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_12000_tax",
            lp_com_goods_private_12000_tax!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_12000_coolie",
            lp_com_goods_private_12000_coolie!!.text.toString()
        )
        b.putString(
            "lp_com_goods_private_12000_nfpp",
            lp_com_goods_private_12000_nfpp!!.text.toString()
        )
        intent.putExtras(b)
        val coolie_value = lp_com_goods_private_12000_coolie!!.text.toString()
        val nfpp_value = lp_com_goods_private_12000_nfpp!!.text.toString()
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