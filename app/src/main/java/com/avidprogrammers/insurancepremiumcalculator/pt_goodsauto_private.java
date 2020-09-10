package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class pt_goodsauto_private extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "pt_goodsauto_private";
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
        checkfunction(pt_goodsauto_private.this);

        setContentView(R.layout.pt_goodsauto_private);
        getSupportActionBar().setTitle("Private Goods Auto Policy Type");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        findViewById(R.id.pp_goodsauto_private).setOnClickListener(listener_pp_goodsauto_private);
        findViewById(R.id.lp_goodsauto_private).setOnClickListener(listener_lp_goodsauto_private);

    };


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    View.OnClickListener listener_pp_goodsauto_private = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pt_goodsauto_private.this, pp_goodsauto_private.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_lp_goodsauto_private = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pt_goodsauto_private.this, lp_goodsauto_private.class);
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
