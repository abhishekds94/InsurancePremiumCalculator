package com.reachmobi.rocketl.ads

import android.app.Application
import android.os.AsyncTask
import com.avidprogrammers.insurancepremiumcalculator.BuildConfig
import com.avidprogrammers.insurancepremiumcalculator.MyApplication
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AdMobNativeManager(val context: MyApplication) {

    private var adLoader: AdLoader? = null

    fun getAd() {

        adLoader?.let {
            if (it.isLoading) {
                adLoader = null
            }
        }

    }

    fun getAdUnitId(): String {
        return BuildConfig.ADMOB_NATIVE
    }


    private fun getAdObserver() {

        adLoader = AdLoader.Builder(context, getAdUnitId())
            .forNativeAd { ad: NativeAd ->
                AsyncTask.execute {
                    Timber.i("Fetched %s", ad.headline)
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdImpression() {
                    super.onAdImpression()
                    Timber.i("Recording Admob Impression")
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    Timber.i("Admob ad clicked")
                    Timber.d("[ad testing123] admob clicked ADmobNative")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)

                    val message = "AdLoader failed with error code $adError"

                    Timber.e(message)
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder().setVideoOptions(
                    VideoOptions.Builder()
                        .setStartMuted(true)
//                        .setClickToExpandRequested(true)
                        .build()
                ).build()
            ).build()

        adLoader?.loadAd(
            AdRequest.Builder()
                .build()
        )

    }

}