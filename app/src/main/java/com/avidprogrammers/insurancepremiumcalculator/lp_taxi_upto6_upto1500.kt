package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

public class lp_taxi_upto6_upto1500 extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    Button lp_taxi_upto6_upto1500btn;

    EditText lp_taxi_upto6_upto1500_act;
    EditText lp_taxi_upto6_upto1500_paod;
    EditText lp_taxi_upto6_upto1500_ll;
    EditText lp_taxi_upto6_upto1500_tax;
    EditText lp_taxi_upto6_scpassengers_upto1500;
    RadioGroup lp_taxi_upto6_upto1500_lpgkit;

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
        checkfunction(lp_taxi_upto6_upto1500.this);

        setContentView(R.layout.lp_taxi_upto6_upto1500);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Taxi Liability Policy");

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        findViewById(R.id.lp_taxi_upto6_upto1500btn).setOnClickListener(listener_lp_taxi_upto6_upto1500btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        lp_taxi_upto6_upto1500btn.setOnClickListener(this);
        //stop-passthevalues

        RadioButton pa_no=(RadioButton) findViewById(R.id.lp_taxi_upto1500_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_taxi_upto1500_paod_value);
                ed1.setText("0");
                ed1.setEnabled(false);
            }
        });
        RadioButton pa_yes=(RadioButton) findViewById(R.id.lp_taxi_upto1500_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_taxi_upto1500_paod_value);
                ed1.setText("320");
                ed1.setEnabled(true);
            }
        });


    }

    View.OnClickListener listener_lp_taxi_upto6_upto1500btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lp_taxi_upto6_upto1500.this, lpdisplay_taxi_upto6_upto1500.class);
            startActivity(intent);
        }

    };



    //start-passthevalues
    private void findAllViewsId() {
        lp_taxi_upto6_upto1500btn = (Button) findViewById(R.id.lp_taxi_upto6_upto1500btn);
        lp_taxi_upto6_upto1500_act = (EditText) findViewById(R.id.lp_taxi_upto6_upto1500_act);
        lp_taxi_upto6_upto1500_paod = (EditText) findViewById(R.id.lp_taxi_upto1500_paod_value);
        lp_taxi_upto6_upto1500_ll = (EditText) findViewById(R.id.lp_taxi_upto6_upto1500_ll);
        lp_taxi_upto6_upto1500_tax = (EditText) findViewById(R.id.lp_taxi_upto6_upto1500_tax);
        lp_taxi_upto6_scpassengers_upto1500=(EditText)findViewById(R.id.lp_taxi_upto6_scpassengers_upto1500);
        lp_taxi_upto6_upto1500_lpgkit = (RadioGroup) findViewById(R.id.lp_taxi_upto6_upto1500_lpgkit);

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), lpdisplay_taxi_upto6_upto1500.class);
        //Create a bundle object
        Bundle b = new Bundle();
        if(lp_taxi_upto6_scpassengers_upto1500.getText().toString().isEmpty()){
            lp_taxi_upto6_scpassengers_upto1500.setError("Fill the capacity");
        }else {
            //Inserts a String value into the mapping of this Bundle
            b.putString("lp_taxi_upto6_upto1500_act", lp_taxi_upto6_upto1500_act.getText().toString());
            b.putString("lp_taxi_upto6_upto1500_paod", lp_taxi_upto6_upto1500_paod.getText().toString());
            b.putString("lp_taxi_upto6_upto1500_ll", lp_taxi_upto6_upto1500_ll.getText().toString());
            b.putString("lp_taxi_upto6_upto1500_tax", lp_taxi_upto6_upto1500_tax.getText().toString());
            b.putString("lp_taxi_upto6_scpassengers_upto1500",String.valueOf(Integer.parseInt(lp_taxi_upto6_scpassengers_upto1500.getText().toString()) * 978));

            //Toast.makeText(lp_taxi_upto6_upto1500.this,String.valueOf(Integer.parseInt(lp_taxi_upto6_scpassengers_upto1500.getText().toString()) * 1035), Toast.LENGTH_SHORT).show();
            int id1 = lp_taxi_upto6_upto1500_lpgkit.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(id1);
            b.putString("lp_taxi_upto6_upto1500_lpgkit", radioButton1.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
        }
    }

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