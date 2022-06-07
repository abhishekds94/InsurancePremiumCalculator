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

class lp_motorcycle_calc_upto150 : AppCompatActivity(), View.OnClickListener,
    ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var lp_motocyc_calc_upto150_btn: Button? = null
    var lp_motocyc_calc_upto150_act: EditText? = null
    var lp_motocyc_calc_upto150_paod: EditText? = null
    var lp_motocyc_calc_upto150_tax: EditText? = null
    var lp_motocyc_calc_upto150_lpgkit: RadioGroup? = null
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
        checkfunction(this@lp_motorcycle_calc_upto150)
        setContentView(R.layout.lp_motorcycle_calc_upto150)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Motorcycle Upto 150CC Liability Policy")

        interstitialAdManager = InterstitialAdManager(this)

        findViewById<View>(R.id.lp_motocyc_calc_upto150_btn).setOnClickListener(
            listener_lp_motocyc_calc_upto150_btn
        )

        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        lp_motocyc_calc_upto150_btn!!.setOnClickListener(this)
        val pa_no: RadioButton =
            findViewById<RadioButton>(R.id.lp_motocyc_calc_upto150_paod_value_no)
        pa_no.setOnClickListener {
            val ed1: EditText = findViewById<EditText>(R.id.lp_motocyc_calc_upto150_paod)
            ed1.setText("0")
            ed1.isEnabled = false
        }
        val pa_yes: RadioButton =
            findViewById<RadioButton>(R.id.lp_motocyc_calc_upto150_paod_value_yes)
        pa_yes.setOnClickListener {
            val ed1: EditText = findViewById<EditText>(R.id.lp_motocyc_calc_upto150_paod)
            ed1.setText("320")
            ed1.isEnabled = true
        }
    }

    fun checkfunction(context: Context?) {
        val isConnected: Boolean = ConnectivityReceiver.Companion.isConnected
        checkingStatus!!.notification(isConnected, context!!)
    }

    var listener_lp_motocyc_calc_upto150_btn = View.OnClickListener {
        val intent = Intent(this@lp_motorcycle_calc_upto150, lp_motorcycle_upto150::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        lp_motocyc_calc_upto150_btn =
            findViewById<View>(R.id.lp_motocyc_calc_upto150_btn) as Button?
        lp_motocyc_calc_upto150_act =
            findViewById<View>(R.id.lp_motocyc_calc_upto150_act) as EditText?
        lp_motocyc_calc_upto150_paod =
            findViewById<View>(R.id.lp_motocyc_calc_upto150_paod) as EditText?
        lp_motocyc_calc_upto150_tax =
            findViewById<View>(R.id.lp_motocyc_calc_upto150_tax) as EditText?

        //lp_goodsauto_private_lpgkit = (RadioGroup) findViewById(R.id.lp_goodsauto_private_lpgkit);
    }

    protected override fun onResume() {
        super.onResume()
        MyApplication.Companion.instance!!.setConnectivityListener(this)
    }

    override fun onClick(v: View) {
        val intent = Intent(getApplicationContext(), lp_motorcycle_upto150::class.java)
        //Create a bundle object
        val b = Bundle()

        //Inserts a String value into the mapping of this Bundle
        b.putString("lp_motocyc_calc_upto150_act", lp_motocyc_calc_upto150_act!!.text.toString())
        b.putString("lp_motocyc_calc_upto150_paod", lp_motocyc_calc_upto150_paod!!.text.toString())
        b.putString("lp_motocyc_calc_upto150_tax", lp_motocyc_calc_upto150_tax!!.text.toString())


        /*int id1 = lp_goodsauto_private_lpgkit.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(id1);
            b.putString("lp_goodsauto_private_lpgkit", radioButton1.getText().toString());*/


        //Add the bundle to the intent.
        intent.putExtras(b)

        //start the DisplayActivity
        startActivity(intent)
    }

    //stop-passthevalues
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        checkfunction(this)
    }

    //BackButton in title bar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}