package com.avidprogrammers.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.avidprogrammers.insurancepremiumcalculator.BuildConfig
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class InterstitialAdManager(
    val context: Context
) {

    private var mInterstitialAd: InterstitialAd? = null

    init {
        MobileAds.initialize(context) {}
        loadAd()
    }

    fun loadAd() {
        val adRequest: AdRequest = AdRequest.Builder().build()
        InterstitialAd.load(context,BuildConfig.ADMOB_INTERSTITIAL, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("admobTest123", adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("admobTest123", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

    fun showInterstitial(activity: Activity,) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    super.onAdDismissedFullScreenContent()
                    Log.d("admobTest123", "Ad was dismissed.")
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    mInterstitialAd = null
                    super.onAdFailedToShowFullScreenContent(p0)
                    Log.d("admobTest123", "Ad failed to show.")
                    loadAd()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    Log.d("admobTest123", "Ad showed fullscreen content.")
                }
            }
            mInterstitialAd?.show(activity)
        } else {
            loadAd()
            Log.d("admobTest123", "Ad did not load")
        }
    }
}