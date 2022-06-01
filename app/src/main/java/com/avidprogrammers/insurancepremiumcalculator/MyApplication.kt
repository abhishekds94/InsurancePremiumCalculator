package com.avidprogrammers.insurancepremiumcalculator

import android.app.Application
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.firebase.FirebaseApp


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MyApplication.Companion.mInstance = this
        FirebaseApp.initializeApp(this)
        }

    fun setConnectivityListener(listener: ConnectivityReceiverListener?) {
        ConnectivityReceiver.Companion.connectivityReceiverListener = listener
    }

    companion object {
        private var mInstance: MyApplication? = null

        @get:Synchronized
        val instance: MyApplication?
            get() = MyApplication.Companion.mInstance
    }
}