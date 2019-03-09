package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class lp_passauto_upto6 extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    Button lp_passauto_upto6btn;

    EditText lp_passauto_upto6_act;
    EditText lp_passauto_upto6_paod;
    EditText lp_passauto_upto6_ll;
    EditText lp_passauto_upto6_tax;

    RadioGroup lp_passauto_upto6_lpgkit;

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
        checkfunction(lp_passauto_upto6.this);

        setContentView(R.layout.lp_passauto_upto6);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Passenger Auto Liability Policy");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findViewById(R.id.lp_passauto_upto6btn).setOnClickListener(listener_lp_passauto_upto6btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        lp_passauto_upto6btn.setOnClickListener(this);
        //stop-passthevalues

        RadioButton pa_no=(RadioButton) findViewById(R.id.lp_passauto_upto6_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_passauto_upto6_paod);
                ed1.setText("0");
                ed1.setEnabled(false);
            }
        });
        RadioButton pa_yes=(RadioButton) findViewById(R.id.lp_passauto_upto6_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_passauto_upto6_paod);
                ed1.setText("320");
                ed1.setEnabled(true);
            }
        });

    }

    View.OnClickListener listener_lp_passauto_upto6btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lp_passauto_upto6.this, lpdisplay_passauto_upto6.class);
            startActivity(intent);
        }

    };



    //start-passthevalues
    private void findAllViewsId() {
        lp_passauto_upto6btn = (Button) findViewById(R.id.lp_passauto_upto6btn);

        lp_passauto_upto6_act = (EditText) findViewById(R.id.lp_passauto_upto6_act);
        lp_passauto_upto6_paod = (EditText) findViewById(R.id.lp_passauto_upto6_paod);
        lp_passauto_upto6_ll = (EditText) findViewById(R.id.lp_passauto_upto6_ll);
        lp_passauto_upto6_tax = (EditText) findViewById(R.id.lp_passauto_upto6_tax);

        lp_passauto_upto6_lpgkit = (RadioGroup) findViewById(R.id.lp_passauto_upto6_lpgkit);

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), lpdisplay_passauto_upto6.class);
        //Create a bundle object
        Bundle b = new Bundle();

        //Inserts a String value into the mapping of this Bundle
        b.putString("lp_passauto_upto6_act", lp_passauto_upto6_act.getText().toString());
        b.putString("lp_passauto_upto6_paod", lp_passauto_upto6_paod.getText().toString());
        b.putString("lp_passauto_upto6_ll", lp_passauto_upto6_ll.getText().toString());
        b.putString("lp_passauto_upto6_tax", lp_passauto_upto6_tax.getText().toString());


        int id1 = lp_passauto_upto6_lpgkit.getCheckedRadioButtonId();
        RadioButton radioButton1 = (RadioButton) findViewById(id1);
        b.putString("lp_passauto_upto6_lpgkit", radioButton1.getText().toString());


        //Add the bundle to the intent.
        intent.putExtras(b);

        //start the DisplayActivity
        startActivity(intent);
    }
    //stop-passthevalues

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

    //BackButton in title bar
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}