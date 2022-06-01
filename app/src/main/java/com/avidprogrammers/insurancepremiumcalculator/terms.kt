package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;

/**
 * Created by Abhishek on 04-Jan-18.
 */

public class terms extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private WebView myWebView;
    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "pt_agri";
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
        checkfunction(terms.this);

        setContentView(R.layout.terms);
        getSupportActionBar().setTitle("Terms & Conditions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String url = getIntent().getStringExtra("url");
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(url);


/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);

        interstitialAd.setAdUnitId(AD_UNIT_ID);
        AdRequest aDRequest = new AdRequest.Builder().build();

        interstitialAd.loadAd(aDRequest);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }

            }

            @Override
            public void onAdOpened() {


            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }
        });*/

    };


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


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
