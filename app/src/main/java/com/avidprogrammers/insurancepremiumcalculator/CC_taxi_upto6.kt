package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class CC_taxi_upto6 extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "CC_taxi_upto6";
    private AdView mAdView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(conn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkingStatus=new CheckingStatus();
        conn=new ConnectivityReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(conn, intentFilter);
        checkfunction(CC_taxi_upto6.this);

        setContentView(R.layout.cc_taxi_upto6);
        getSupportActionBar().setTitle("Taxi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_taxi");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        findViewById(R.id.taxi_upto6_upto1000).setOnClickListener(listener_taxi_upto6_upto1000);
        findViewById(R.id.taxi_upto6_upto1500).setOnClickListener(listener_taxi_upto6_upto1500);
        findViewById(R.id.taxi_upto6_above1500).setOnClickListener(listener_taxi_upto6_above1500);

    };

    View.OnClickListener listener_taxi_upto6_upto1000 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_taxi_upto6.this, pt_taxi_upto6_upto1000.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_taxi_upto6_upto1500 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_taxi_upto6.this, pt_taxi_upto6_upto1500.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_taxi_upto6_above1500 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_taxi_upto6.this, pt_taxi_upto6_above1500.class);
            startActivity(intent);
        }
    };

    public void checkfunction(Context context){
        boolean isConnected=ConnectivityReceiver.isConnected();
        checkingStatus.notification(isConnected,context);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkfunction(this);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
