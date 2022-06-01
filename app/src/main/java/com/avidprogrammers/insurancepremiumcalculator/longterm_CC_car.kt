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

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class longterm_CC_car extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

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
        checkfunction(longterm_CC_car.this);

        setContentView(R.layout.longterm_cc_car);
//        Toast.makeText(this, "longterm_cc_motorcycle", Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("Motorcycle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        findViewById(R.id.lt_car_upto1000).setOnClickListener(listener_upto1000);
        findViewById(R.id.lt_car_upto1500).setOnClickListener(listener_upto1500);
        findViewById(R.id.lt_car_above1500).setOnClickListener(listener_above1500);


    };

    View.OnClickListener listener_upto1000 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(longterm_CC_car.this, longterm_pt_car_upto1000.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_upto1500 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(longterm_CC_car.this, longterm_pt_car_upto1500.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_above1500 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(longterm_CC_car.this, longterm_pt_car_above1500.class);
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
