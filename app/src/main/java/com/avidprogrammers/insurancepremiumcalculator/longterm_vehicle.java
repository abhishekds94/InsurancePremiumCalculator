package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class longterm_vehicle extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "longterm_vehicle";
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
        checkfunction(longterm_vehicle.this);

        setContentView(R.layout.longterm_vehicle);
        Toast.makeText(this, "longterm_vehicle", Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("Longterm Policy Type");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_longterm");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        findViewById(R.id.longterm_motorcycle).setOnClickListener(listener_longterm_motorcycle);
        findViewById(R.id.longterm_car).setOnClickListener(listener_longterm_car);
    };


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    View.OnClickListener listener_longterm_motorcycle = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(longterm_vehicle.this, longterm_CC_motorcycle.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_longterm_car = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(longterm_vehicle.this, longterm_CC_car.class);
            startActivity(intent);
        }
    };

    //checking connectivity
    public void checkfunction(Context context){
        boolean isConnected=ConnectivityReceiver.isConnected();
        //notification(isConnected,lp_taxi_upto18pass.this);
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

}
