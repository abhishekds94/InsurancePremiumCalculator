package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.ads.InterstitialAdManager
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView

/**
 * Created by Abhishek on 26-Mar-17.
 */
class lp_goodsauto_private : AppCompatActivity(), View.OnClickListener,
    ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var lp_goodsauto_privatebtn: Button? = null
    var lp_goodsauto_private_act: EditText? = null
    var lp_goodsauto_private_paod: EditText? = null
    var lp_goodsauto_private_ll: EditText? = null
    var lp_goodsauto_private_tax: EditText? = null
    var lp_goodsauto_private_nfpp: EditText? = null
    var lp_goodsauto_private_coolie: EditText? = null
    var lp_goodsauto_private_lpgkit: RadioGroup? = null

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
        checkfunction(this@lp_goodsauto_private)
        setContentView(R.layout.lp_goodsauto_private)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Goods Auto Private Liability Policy")


        interstitialAdManager = InterstitialAdManager(this)

        findViewById<View>(R.id.lp_goodsauto_privatebtn).setOnClickListener(
            listener_lp_goodsauto_privatebtn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        lp_goodsauto_privatebtn!!.setOnClickListener(this)
        //stop-passthevalues
        val pa_no = findViewById<View>(R.id.lp_goodsauto_private_paod_value_no) as RadioButton
        pa_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_goodsauto_private_paod) as EditText
            ed1.setText("0")
            ed1.isEnabled = false
        }
        val pa_yes = findViewById<View>(R.id.lp_goodsauto_private_paod_value_yes) as RadioButton
        pa_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lp_goodsauto_private_paod) as EditText
            ed1.setText("320")
            ed1.isEnabled = true
        }
    }

    var listener_lp_goodsauto_privatebtn = View.OnClickListener {
        val intent = Intent(this@lp_goodsauto_private, lpdisplay_goodsauto_private::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        lp_goodsauto_privatebtn = findViewById<View>(R.id.lp_goodsauto_privatebtn) as Button?
        lp_goodsauto_private_act = findViewById<View>(R.id.lp_goodsauto_private_act) as EditText?
        lp_goodsauto_private_paod = findViewById<View>(R.id.lp_goodsauto_private_paod) as EditText?
        lp_goodsauto_private_ll = findViewById<View>(R.id.lp_goodsauto_private_ll) as EditText?
        lp_goodsauto_private_tax = findViewById<View>(R.id.lp_goodsauto_private_tax) as EditText?
        lp_goodsauto_private_nfpp = findViewById<View>(R.id.lp_goodsauto_private_nfpp) as EditText?
        lp_goodsauto_private_coolie =
            findViewById<View>(R.id.lp_goodsauto_private_coolie) as EditText?
        lp_goodsauto_private_lpgkit =
            findViewById<View>(R.id.lp_goodsauto_private_lpgkit) as RadioGroup?
    }

    fun validation(): Int {
        if (lp_goodsauto_private_nfpp!!.text.toString()
                .isEmpty() || lp_goodsauto_private_coolie!!.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "Enter Nfpp or Coolie First", Toast.LENGTH_SHORT).show()
            return 0
        }
        return 1
    }

    override fun onClick(v: View) {
        val intent = Intent(getApplicationContext(), lpdisplay_goodsauto_private::class.java)
        //Create a bundle object
        val b = Bundle()
        val valid = validation()
        if (valid == 1) {
            //Inserts a String value into the mapping of this Bundle
            b.putString("lp_goodsauto_private_act", lp_goodsauto_private_act!!.text.toString())
            b.putString("lp_goodsauto_private_paod", lp_goodsauto_private_paod!!.text.toString())
            b.putString("lp_goodsauto_private_ll", lp_goodsauto_private_ll!!.text.toString())
            b.putString("lp_goodsauto_private_tax", lp_goodsauto_private_tax!!.text.toString())
            b.putString("lp_goodsauto_private_nfpp", lp_goodsauto_private_nfpp!!.text.toString())
            b.putString(
                "lp_goodsauto_private_coolie",
                lp_goodsauto_private_coolie!!.text.toString()
            )
            val id1 = lp_goodsauto_private_lpgkit!!.checkedRadioButtonId
            val radioButton1 = findViewById<View>(id1) as RadioButton
            b.putString("lp_goodsauto_private_lpgkit", radioButton1.text.toString())


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