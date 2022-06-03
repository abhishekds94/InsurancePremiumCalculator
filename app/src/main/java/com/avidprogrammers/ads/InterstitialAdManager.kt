package com.avidprogrammers.ads

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.avidprogrammers.insurancepremiumcalculator.BuildConfig
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import timber.log.Timber

class InterstitialAdManager(
    val context: Context,
    val activity: Activity
) {

//
//    var interstitialAd: InterstitialAd? = null
//    private var listener: InterstitialListener? = null
//    private var interstitialLoadedListener: InterstitialLoadedListener? = null
//
//    init {
//        MobileAds.initialize(context) {}
//        loadAd()
//    }
//
//
//    fun setInterstitialListener(listener: InterstitialListener) {
//        this.listener = listener
//    }
//
//    fun setInterstitialLoadedListener(listener: InterstitialLoadedListener) {
//        this.interstitialLoadedListener = listener
//    }
//
//    fun show() {
//
//        if (interstitialAd != null) {
//            interstitialAd!!.show(activity)
//        } else {
//            Timber.d("[interstitialAdTesting123] The interstitial ad wasn't ready yet.");
//            loadAd()
//        }
//
//    }
//
//
//    fun loadAd() {
//        val adRequest = AdRequest.Builder().build()
//        Timber.d("[interstitialAdTesting123] in load")
//
//        InterstitialAd.load(
//            context, BuildConfig.ADMOB_INTERSTITIAL, adRequest,
//            object : InterstitialAdLoadCallback() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    interstitialAd = null
//                    loadAd()
//                    Timber.d("[interstitialAdTesting123] in onAdFailedToLoad %s -- %s", adError.code, adError.message)
//                }
//
//                override fun onAdLoaded(InterstitialAd: InterstitialAd) {
//                    interstitialAd = InterstitialAd
//                    Timber.d("[interstitialAdTesting123] in onAdLoaded")
//                }
//            }
//        )
//
//        MobileAds.setRequestConfiguration(
//            RequestConfiguration.Builder()
//                .setTestDeviceIds(
//                    listOf(
//                        AdRequest.DEVICE_ID_EMULATOR
//                    )
//                )
//                .build()
//        )
//
//    }
//
//    private fun showInterstitial() {
//        if (interstitialAd != null) {
//            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
//                override fun onAdDismissedFullScreenContent() {
//                    interstitialAd = null
//                    loadAd()
//                }
//
//                override fun onAdImpression() {
//                    super.onAdImpression()
//                }
//
//                override fun onAdClicked() {
//                    super.onAdClicked()
//                }
//
//                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
//                    interstitialAd = null
//                    loadAd()
//                    Timber.d("[interstitialAdTesting123] in onAdFailedToShowFullScreenContent %s -- %s", p0.code, p0.message)
//                }
//
//                override fun onAdShowedFullScreenContent() {
//                }
//            }
//            try {
//                interstitialAd?.show(context as Activity)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        } else {
//        }
//    }
//
//    interface InterstitialListener {
//        fun onInterstitialClosed()
//    }
//
//    interface InterstitialLoadedListener {
//        fun onInterstitialLoaded()
//    }

    var mInterstitialAd: InterstitialAd? = null
    private var mAdIsLoading: Boolean = false

    init {
        MobileAds.initialize(context) {}

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("8262955E063A02F1F3DA99CEE3B1AB67"))
                .build()
        )

        if (!mAdIsLoading && mInterstitialAd == null) {
            mAdIsLoading = true
            loadAd()
        }

    }

    fun loadAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context, "ca-app-pub-3940256099942544/1033173712", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.d("[admobAd123] ad failed ${adError.message}")
                    Timber.d("[admobAd123] inter ad failed $adError")
                    mInterstitialAd = null
                    mAdIsLoading = false
                    val error = "domain: ${adError.domain}, code: ${adError.code}, " +
                            "message: ${adError.message}"
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Timber.d("[admobAd123] inter ad loaded")
                    mInterstitialAd = interstitialAd
                    mAdIsLoading = false
                }
            }
        )
    }

    fun showInterstitial() {
        Timber.d("[admobAd123] show ad")
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Timber.d("[admobAd123] Ad was dismissed")
                    mInterstitialAd = null
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    Timber.d("[admobAd123] ad failed ${p0.message}")
                    Timber.d("[admobAd123] inter ad failed $p0")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Timber.d("[admobAd123] Ad showed fullscreen content")
                }
            }
            mInterstitialAd?.show(activity)
        } else {
            loadAd()
            Timber.d("[admobAd123] ad wasn't ready yet")
        }
    }

}
