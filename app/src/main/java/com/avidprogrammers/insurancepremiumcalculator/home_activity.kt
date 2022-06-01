package com.avidprogrammers.insurancepremiumcalculator

import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import android.os.Bundle
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.google.firebase.analytics.FirebaseAnalytics
import android.content.Intent
import android.content.SharedPreferences
import com.avidprogrammers.database.DatabaseHelper
import android.graphics.drawable.LayerDrawable
import com.google.firebase.messaging.FirebaseMessaging
import android.content.Context
import android.util.Log
import android.view.*
import android.widget.*
import com.avidprogrammers.app.Config
import com.avidprogrammers.insurancepremiumcalculatorimport.*
import com.avidprogrammers.utils.BadgeDrawable

class home_activity : AppCompatActivity(), ConnectivityReceiverListener {
    //Create New Variable of type InterstitialAd
    private var btn_longterm: Button? = null
    private var btn_motorcycle: Button? = null
    private var btn_privatecar: Button? = null
    private var btn_taxi_upto6: Button? = null
    private var btn_bus: Button? = null
    private var btn_passauto: Button? = null
    private var btn_goodsauto_public: Button? = null
    private var btn_goodsauto_private: Button? = null
    private var btn_commercialvehiclepublic: Button? = null
    private var btn_commercialvehicleprivate: Button? = null
    private var btn_agri: Button? = null
    private var btn_terms: Button? = null
    private var btn_privacy: Button? = null
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var pref: SharedPreferences? = null
    var databaseHelper: DatabaseHelper? = null
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        pref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val count = pref!!.getInt("newNoti", 0)
        if (count != 0) {
            val itemCart = menu.findItem(R.id.notification_menu)
            val icon = itemCart.icon as LayerDrawable
            setBadgeCount(this, icon, "" + count)
            Log.e(TAG, "onCreateOptionsMenu: Setting Count")

            // Obtain the FirebaseAnalytics instance.
            val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open")
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notification_menu -> {
                databaseHelper = DatabaseHelper(this@home_activity)
                val count = databaseHelper!!.notificationCount
                if (count == 0) {
                    Toast.makeText(
                        this@home_activity,
                        "No Notification to display!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    startActivity(Intent(this@home_activity, NotificationActivity::class.java))
                    finish()
                }
            }
        }
        return true
    }

    fun setBadgeCount(context: Context?, icon: LayerDrawable, count: String?) {
        val badge: BadgeDrawable

        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
        badge = if (reuse != null && reuse is BadgeDrawable) {
            reuse
        } else {
            BadgeDrawable(context)
        }
        badge.setCount(count)
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_badge, badge)
    }

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
        checkfunction(this@home_activity)
        setContentView(R.layout.activity_home)
        val pref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        if (pref.getString("regID", "NA") == "NA");
        run {
            val editor = pref.edit()
            val refreshedToken = FirebaseMessaging.getInstance().token
            FirebaseMessaging.getInstance().subscribeToTopic("all")
            editor.putString("regId", refreshedToken.toString())
            editor.commit()
        }
        btn_longterm = findViewById<View>(R.id.longterm) as Button
        btn_longterm!!.setOnClickListener { showInterstitial_btn_longterm() }
        btn_motorcycle = findViewById<View>(R.id.motorcycle) as Button
        btn_motorcycle!!.setOnClickListener { showInterstitial_btn_motorcycle() }
        btn_privatecar = findViewById<View>(R.id.privatecar) as Button
        btn_privatecar!!.setOnClickListener { showInterstitial_btn_privatecar() }
        btn_taxi_upto6 = findViewById<View>(R.id.taxi_upto6) as Button
        btn_taxi_upto6!!.setOnClickListener { showInterstitial_btn_taxi_upto6() }
        btn_bus = findViewById<View>(R.id.bus) as Button
        btn_bus!!.setOnClickListener { showInterstitial_btn_bus() }
        btn_passauto = findViewById<View>(R.id.passauto) as Button
        btn_passauto!!.setOnClickListener { showInterstitial_btn_passauto() }
        btn_goodsauto_public = findViewById<View>(R.id.goodsauto_public) as Button
        btn_goodsauto_public!!.setOnClickListener { showInterstitial_btn_goodsauto_public() }
        btn_goodsauto_private = findViewById<View>(R.id.goodsauto_private) as Button
        btn_goodsauto_private!!.setOnClickListener { showInterstitial_btn_goodsauto_private() }
        btn_commercialvehiclepublic = findViewById<View>(R.id.commercialvehiclepublic) as Button
        btn_commercialvehiclepublic!!.setOnClickListener { showInterstitial_btn_commercialvehiclepublic() }
        btn_commercialvehicleprivate = findViewById<View>(R.id.commercialvehicleprivate) as Button
        btn_commercialvehicleprivate!!.setOnClickListener { showInterstitial_btn_commercialvehicleprivate() }
        btn_agri = findViewById<View>(R.id.agri) as Button
        btn_agri!!.setOnClickListener { showInterstitial_btn_agri() }
        btn_privacy = findViewById<View>(R.id.privacy) as Button
        btn_privacy!!.setOnClickListener { showInterstitial_btn_privacy() }
        btn_terms = findViewById<View>(R.id.terms) as Button
        btn_terms!!.setOnClickListener { showInterstitial_btn_terms() }
    }

    fun showInterstitial_btn_longterm() {
        val inte = Intent(this@home_activity, longterm_vehicle::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_motorcycle() {
        val inte = Intent(this@home_activity, CC_motorcycle::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_privatecar() {
        val inte = Intent(this@home_activity, CC_car::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_taxi_upto6() {
        val inte = Intent(this@home_activity, CC_taxi_upto6::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_bus() {
        val inte = Intent(this@home_activity, CC_bus::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_passauto() {
        val inte = Intent(this@home_activity, CC_passauto::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_goodsauto_public() {
        val inte = Intent(this@home_activity, pt_goodsauto_public::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_goodsauto_private() {
        val inte = Intent(this@home_activity, pt_goodsauto_private::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_commercialvehiclepublic() {
        val inte = Intent(this@home_activity, CC_commercialvehiclepublic::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_commercialvehicleprivate() {
        val inte = Intent(this@home_activity, CC_commercialvehicleprivate::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_agri() {
        val inte = Intent(this@home_activity, pt_agri::class.java)
        startActivity(inte)
    }

    fun showInterstitial_btn_terms() {
        val inte = Intent(this@home_activity, terms::class.java)
        inte.putExtra("url", "http://anugrahacomputers.co.in/avidprogrammers/terms.html")
        startActivity(inte)
    }

    fun showInterstitial_btn_privacy() {
        val inte = Intent(this@home_activity, privacy::class.java)
        inte.putExtra("url", "http://anugrahacomputers.co.in/avidprogrammers/privacy.html")
        startActivity(inte)
    }

    fun checkfunction(context: Context?) {
        val isConnected: Boolean = ConnectivityReceiver.Companion.isConnected
        //notification(isConnected,lp_taxi_upto18pass.this);
        checkingStatus!!.notification(isConnected, context!!)
    }

    override fun onResume() {
        super.onResume()
        MyApplication.Companion.instance!!.setConnectivityListener(this)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        checkingStatus!!.notification(isConnected, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    companion object {
        private const val TAG = "home_activity"
    }
}