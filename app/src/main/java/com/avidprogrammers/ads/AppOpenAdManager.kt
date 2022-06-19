package com.avidprogrammers.ads

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.avidprogrammers.insurancepremiumcalculator.BuildConfig
import com.avidprogrammers.insurancepremiumcalculator.MyApplication
import com.google.android.gms.ads.*
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import java.util.*


class AppOpenManager(myApplication: MyApplication) : LifecycleObserver,
    ActivityLifecycleCallbacks {
    private val myApplication: MyApplication = myApplication
    private var appOpenAd: AppOpenAd? = null
    private var loadCallback: AppOpenAdLoadCallback? = null
    private var currentActivity: Activity? = null
    private var loadTime: Long = 0

    fun showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable) {
            Log.d(LOG_TAG, "Will show ad.")
            val fullScreenContentCallback: FullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        appOpenAd = null
                        isShowingAd = false
//                        fetchAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(LOG_TAG, "Can not show ad."+adError)
                        fetchAd()
                    }
                    override fun onAdShowedFullScreenContent() {
                        isShowingAd = true
                    }
                }
            appOpenAd!!.fullScreenContentCallback = fullScreenContentCallback
            appOpenAd!!.show(currentActivity!!)
        } else {
            Log.d(LOG_TAG, "Can not show ad.")
            fetchAd()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onStart() {
        showAdIfAvailable()
        Log.d(LOG_TAG, "onStart")
    }

    fun fetchAd() {
        if (isAdAvailable) {
            return
        }
        loadCallback = object : AppOpenAdLoadCallback() {

            override fun onAdLoaded(appOpenAd: AppOpenAd) {
                super.onAdLoaded(appOpenAd)
                this@AppOpenManager.appOpenAd = appOpenAd
                loadTime = Date().time
                Log.d(LOG_TAG, "App-Open Ad Loaded")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                Log.d("admobAd123","ad failed" +loadAdError?.message)
                Log.d("admobAd123","appOpen ad failed" +loadAdError)
                fetchAd()
            }
        }

        val request = adRequest
        AppOpenAd.load(
            myApplication,
            AD_UNIT_ID,
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            loadCallback!!
        )
    }
//    .setTestDeviceIds(Arrays.asList("8262955E063A02F1F3DA99CEE3B1AB67"))
    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    val isAdAvailable: Boolean
        get() = appOpenAd != null && wasLoadTimeLessThanNHoursAgo()

    private fun wasLoadTimeLessThanNHoursAgo(): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * 4.toLong()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }

    companion object {
        private const val LOG_TAG = "AppOpenManager"
        private const val AD_UNIT_ID = BuildConfig.ADMOB_APPOPEN
        private var isShowingAd = false
    }

    init {
        this.myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}