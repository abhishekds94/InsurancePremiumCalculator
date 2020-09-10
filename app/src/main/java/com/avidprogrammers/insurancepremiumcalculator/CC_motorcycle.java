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

public class CC_motorcycle extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "CC_motorcycle";
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
        checkfunction(CC_motorcycle.this);

        setContentView(R.layout.cc_motorcycle);
        getSupportActionBar().setTitle("Motorcycle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open_motorcycle");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        findViewById(R.id.motorcycle_upto75).setOnClickListener(listener_upto75);
        findViewById(R.id.motorcycle_upto150).setOnClickListener(listener_upto150);
        findViewById(R.id.motorcycle_upto350).setOnClickListener(listener_upto350);
        findViewById(R.id.motorcycle_above350).setOnClickListener(listener_above350);


    };

    View.OnClickListener listener_upto75 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_motorcycle.this, pt_motorcycle_upto75.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_upto150 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_motorcycle.this, pt_motorcycle_upto150.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_upto350 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_motorcycle.this, pt_motorcycle_upto350.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_above350 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_motorcycle.this, pt_motorcycle_350above.class);
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
