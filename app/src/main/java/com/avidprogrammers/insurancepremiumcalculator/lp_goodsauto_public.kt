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
class lp_goodsauto_public : AppCompatActivity(), View.OnClickListener,
    ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var lp_goodsauto_publicbtn: Button? = null
    var lp_goodsauto_public_act: EditText? = null
    var lp_goodsauto_public_paod: EditText? = null
    var lp_goodsauto_public_ll: EditText? = null
    var lp_goodsauto_public_tax: EditText? = null
    var lp_goodsauto_public_nfpp: EditText? = null
    var lp_goodsauto_public_coolie: EditText? = null
    var lp_goodsauto_public_lpgkit: RadioGroup? = null
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
        checkfunction(this@lp_goodsauto_public)
        setContentView(R.layout.lp_goodsauto_public)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Goods Auto Public Liability Policy")

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/findViewById<View>(R.id.lp_goodsauto_publicbtn).setOnClickListener(
            listener_lp_goodsauto_publicbtn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        lp_goodsauto_publicbtn!!.setOnClickListener(this)
        //stop-passthevalues
        val pa_no = findViewById<View>(R.id.lp_goodsauto_public_paod_value_no) as RadioButton
        pa_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_goodsauto_public_paod) as EditText
            ed1.setText("0")
            ed1.isEnabled = false
        }
        val pa_yes = findViewById<View>(R.id.lp_goodsauto_public_paod_value_yes) as RadioButton
        pa_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_goodsauto_public_paod) as EditText
            ed1.setText("320")
            ed1.isEnabled = true
        }
    }

    var listener_lp_goodsauto_publicbtn = View.OnClickListener {
        val intent = Intent(this@lp_goodsauto_public, lpdisplay_goodsauto_public::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    fun findAllViewsId() {
        lp_goodsauto_publicbtn = findViewById<View>(R.id.lp_goodsauto_publicbtn) as Button?
        lp_goodsauto_public_act = findViewById<View>(R.id.lp_goodsauto_public_act) as EditText?
        lp_goodsauto_public_paod = findViewById<View>(R.id.lp_goodsauto_public_paod) as EditText?
        lp_goodsauto_public_ll = findViewById<View>(R.id.lp_goodsauto_public_ll) as EditText?
        lp_goodsauto_public_tax = findViewById<View>(R.id.lp_goodsauto_public_tax) as EditText?
        lp_goodsauto_public_nfpp = findViewById<View>(R.id.lp_goodsauto_public_nfpp) as EditText?
        lp_goodsauto_public_coolie =
            findViewById<View>(R.id.lp_goodsauto_public_coolie) as EditText?
        lp_goodsauto_public_lpgkit =
            findViewById<View>(R.id.lp_goodsauto_public_lpgkit) as RadioGroup?
    }

    fun validation(): Int {
        if (lp_goodsauto_public_nfpp!!.text.toString()
                .isEmpty() || lp_goodsauto_public_coolie!!.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "Enter Nfpp or Coolie First", Toast.LENGTH_SHORT).show()
            return 0
        }
        return 1
    }

    override fun onClick(v: View) {
        val intent = Intent(getApplicationContext(), lpdisplay_goodsauto_public::class.java)
        //Create a bundle object
        val b = Bundle()
        val valid = validation()
        if (valid == 1) {
            //Inserts a String value into the mapping of this Bundle
            b.putString("lp_goodsauto_public_act", lp_goodsauto_public_act!!.text.toString())
            b.putString("lp_goodsauto_public_paod", lp_goodsauto_public_paod!!.text.toString())
            b.putString("lp_goodsauto_public_ll", lp_goodsauto_public_ll!!.text.toString())
            b.putString("lp_goodsauto_public_tax", lp_goodsauto_public_tax!!.text.toString())
            b.putString("lp_goodsauto_public_nfpp", lp_goodsauto_public_nfpp!!.text.toString())
            b.putString("lp_goodsauto_public_coolie", lp_goodsauto_public_coolie!!.text.toString())
            val id1 = lp_goodsauto_public_lpgkit!!.checkedRadioButtonId
            val radioButton1 = findViewById<View>(id1) as RadioButton
            b.putString("lp_goodsauto_public_lpgkit", radioButton1.text.toString())


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