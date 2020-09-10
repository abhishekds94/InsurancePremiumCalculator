package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class pt_commercialvehiclepublic_upto40000 extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "pt_commprivate";
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
        checkfunction(pt_commercialvehiclepublic_upto40000.this);

        setContentView(R.layout.pt_commercialvehiclepublic_upto40000);
        getSupportActionBar().setTitle("Public Commercial Vehicle Policy Type");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        findViewById(R.id.pp_commercialvehiclepublic_upto40000).setOnClickListener(listener_pp_commercialvehiclepublic_upto40000);
        findViewById(R.id.lp_commercialvehiclepublic_upto40000).setOnClickListener(listener_lp_commercialvehiclepublic_upto40000);
    };


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    View.OnClickListener listener_pp_commercialvehiclepublic_upto40000 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pt_commercialvehiclepublic_upto40000.this, pp_com_goods_public_40000.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_lp_commercialvehiclepublic_upto40000 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pt_commercialvehiclepublic_upto40000.this, lp_com_goods_public_40000.class);
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
