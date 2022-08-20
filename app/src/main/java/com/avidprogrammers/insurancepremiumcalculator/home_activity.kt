package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.drawable.LayerDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.avidprogrammers.ads.AppOpenManager
import com.avidprogrammers.app.Config
import com.avidprogrammers.database.DatabaseHelper
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.avidprogrammers.utils.BadgeDrawable
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging


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
    var ad_frame: FrameLayout? = null

    var appOpenManager: AppOpenManager? = null
    var currentNativeAd: NativeAd? = null

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

        appOpenManager = AppOpenManager(applicationContext as MyApplication)
        appOpenManager!!.fetchAd()

        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("8262955E063A02F1F3DA99CEE3B1AB67"))
                .build()
        )

        refreshAd()


        FirebaseApp.initializeApp(this)
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
        ad_frame = findViewById<FrameLayout>(R.id.ad_frame)
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
        inte.putExtra("url", "https://abhishekds.site/personal/avid-terms.html")
        startActivity(inte)
    }

    fun showInterstitial_btn_privacy() {
        val inte = Intent(this@home_activity, privacy::class.java)
        inte.putExtra("url", "https://abhishekds.site/personal/avid-privacy.html")
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
        currentNativeAd?.destroy()
    }

    private fun refreshAd() {

        val builder = AdLoader.Builder(this, BuildConfig.ADMOB_NATIVE)

        builder.forNativeAd { nativeAd ->
            var activityDestroyed = false

            if (activityDestroyed ) {
                nativeAd.destroy()
                return@forNativeAd
            }
            currentNativeAd?.destroy()
            currentNativeAd = nativeAd
            val adView = layoutInflater
                .inflate(R.layout.native_ad, null) as NativeAdView
            populateNativeAdView(nativeAd, adView)

            ad_frame!!.visibility = View.VISIBLE

            ad_frame!!.removeAllViews()
            ad_frame!!.addView(adView)
        }

        val videoOptions = VideoOptions.Builder()
            .build()

        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                Log.d("admobAd123","ad failed" +loadAdError?.message)
                Log.d("admobAd123"," native ad failed" +loadAdError)
                val error =
                    """
           domain: ${loadAdError.domain}, code: ${loadAdError.code}, message: ${loadAdError.message}
          """"
            }

            override fun onAdImpression() {
                super.onAdImpression()
            }

        }).build()

        adLoader.loadAd(AdRequest.Builder().build())

    }

    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        // Set the media view.
        adView.mediaView = adView.findViewById<MediaView>(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
//        adView.iconView = adView.findViewById(R.id.ad_app_icon)
//        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
//        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView!!.setMediaContent(nativeAd.mediaContent!!)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView!!.visibility = View.INVISIBLE
        } else {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView!!.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView!!.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView!!.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView!!.visibility = View.VISIBLE
        }

        adView.setNativeAd(nativeAd)

        val vc = nativeAd.mediaContent!!.videoController

        if (vc.hasVideoContent()) {
            vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
                override fun onVideoEnd() {
                    super.onVideoEnd()
                }
            }
        } else {

        }
    }

    companion object {
        private const val TAG = "home_activity"
    }
}