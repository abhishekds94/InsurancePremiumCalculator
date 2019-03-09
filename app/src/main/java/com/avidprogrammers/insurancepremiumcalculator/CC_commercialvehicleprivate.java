package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class CC_commercialvehicleprivate extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "CC_commercialvehicleprivate";
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
        checkfunction(CC_commercialvehicleprivate.this);

        setContentView(R.layout.cc_commercialvehicleprivate);
        getSupportActionBar().setTitle("Private Commercial Vehicle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        findViewById(R.id.comvehicle_private_upto7500).setOnClickListener(listener_comvehicle_private_upto7500);
        findViewById(R.id.comvehicle_private_upto12000).setOnClickListener(listener_comvehicle_private_upto12000);
        findViewById(R.id.comvehicle_private_upto20000).setOnClickListener(listener_comvehicle_private_upto20000);
        findViewById(R.id.comvehicle_private_upto40000).setOnClickListener(listener_comvehicle_private_upto40000);
        findViewById(R.id.comvehicle_private_above40000).setOnClickListener(listener_comvehicle_private_40000above);

    };

    View.OnClickListener listener_comvehicle_private_upto7500 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_commercialvehicleprivate.this, pt_commercialvehicleprivate_upto7500.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_comvehicle_private_upto12000 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_commercialvehicleprivate.this, pt_commercialvehicleprivate_upto12000.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_comvehicle_private_upto20000 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_commercialvehicleprivate.this, pt_commercialvehicleprivate_upto20000.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_comvehicle_private_upto40000 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_commercialvehicleprivate.this, pt_commercialvehicleprivate_upto40000.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_comvehicle_private_40000above = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CC_commercialvehicleprivate.this, pt_commercialvehicleprivate_40000above.class);
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
