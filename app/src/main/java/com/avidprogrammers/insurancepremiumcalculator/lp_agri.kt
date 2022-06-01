package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView

/**
 * Created by Abhishek on 26-Mar-17.
 */
class lp_agri : AppCompatActivity(), View.OnClickListener, ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var lp_agribtn: Button? = null
    var lp_agri_act: EditText? = null
    var lp_agri_paod: EditText? = null
    var lp_agri_ll: EditText? = null
    var lp_agri_tax: EditText? = null
    var lp_agri_coolie: EditText? = null
    var lp_agri_trailer: EditText? = null
    var lp_agri_lpgtype: RadioGroup? = null
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
        checkfunction(this@lp_agri)
        setContentView(R.layout.lp_agri)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Tractors & Trailers Liability Policy")

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.lp_agribtn).setOnClickListener(
            listener_lp_agribtn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        lp_agribtn!!.setOnClickListener(this)
        //stop-passthevalues
        val pa_no = findViewById<View>(R.id.lp_agri_paod_value_no) as RadioButton
        pa_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_agri_paod) as EditText
            ed1.setText("0")
            ed1.isEnabled = false
        }
        val pa_yes = findViewById<View>(R.id.lp_agri_paod_value_yes) as RadioButton
        pa_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_agri_paod) as EditText
            ed1.setText("320")
            ed1.isEnabled = true
        }
        val trailer_no = findViewById<View>(R.id.lp_agri_lpgtype_value_inbuilt) as RadioButton
        trailer_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_agri_trailer_value) as EditText
            ed1.isEnabled = false
            ed1.setText("0")
        }
        val trailer_yes = findViewById<View>(R.id.lp_agri_lpgtype_value_fixed) as RadioButton
        trailer_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_agri_trailer_value) as EditText
            ed1.isEnabled = false
            ed1.setText("2485")
        }
    }

    var listener_lp_agribtn = View.OnClickListener {
        val intent = Intent(this@lp_agri, lpdisplay_agri::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        lp_agribtn = findViewById<View>(R.id.lp_agribtn) as Button?
        lp_agri_trailer = findViewById<View>(R.id.lp_agri_trailer_value) as EditText?
        lp_agri_trailer!!.isEnabled = false
        lp_agri_trailer!!.setText("2485")
        lp_agri_act = findViewById<View>(R.id.lp_agri_act) as EditText?
        lp_agri_paod = findViewById<View>(R.id.lp_agri_paod) as EditText?
        lp_agri_ll = findViewById<View>(R.id.lp_agri_ll) as EditText?
        lp_agri_tax = findViewById<View>(R.id.lp_agri_tax) as EditText?
        lp_agri_coolie = findViewById<View>(R.id.lp_agri_coolie) as EditText?
        lp_agri_lpgtype = findViewById<View>(R.id.pp_agri_lpgtype) as RadioGroup?
    }

    override fun onClick(v: View) {
        if (lp_agri_coolie!!.text.toString() == "") {
            Toast.makeText(getApplicationContext(), "Please enter Coolie Value", Toast.LENGTH_SHORT)
                .show()
        } else {
            val intent = Intent(getApplicationContext(), lpdisplay_agri::class.java)
            //Create a bundle object
            val b = Bundle()

            //Inserts a String value into the mapping of this Bundle
            b.putString("lp_agri_act", lp_agri_act!!.text.toString())
            b.putString("lp_agri_paod", lp_agri_paod!!.text.toString())
            b.putString("lp_agri_ll", lp_agri_ll!!.text.toString())
            b.putString("lp_agri_tax", lp_agri_tax!!.text.toString())
            b.putString("lp_agri_coolie", lp_agri_coolie!!.text.toString())
            val id1 = lp_agri_lpgtype!!.checkedRadioButtonId
            val radioButton1 = findViewById<View>(id1) as RadioButton
            b.putString("lp_agri_lpgtype", radioButton1.text.toString())

            //Add the bundle to the intent.
            intent.putExtras(b)

            //start the DisplayActivity
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