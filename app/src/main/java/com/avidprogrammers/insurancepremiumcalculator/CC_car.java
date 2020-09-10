package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class CC_car extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "CC_car";
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
        checkfunction(CC_car.this);

        setContentView(R.layout.cc_car);
        getSupportActionBar().setTitle("Private Car");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_car");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        findViewById(R.id.car_upto1000).setOnClickListener(listener_car_upto1000);
        findViewById(R.id.car_upto1500).setOnClickListener(listener_car_upto1500);
        findViewById(R.id.car_above1500).setOnClickListener(listener_car_above1500);

    };

    View.OnClickListener listener_car_upto1000 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_car.this, pt_car_upto1000.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_car_upto1500 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_car.this, pt_car_upto1500.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_car_above1500 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_car.this, pt_car_above1500.class);
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
