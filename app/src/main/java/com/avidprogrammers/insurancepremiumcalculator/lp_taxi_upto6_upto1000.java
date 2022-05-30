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

public class lp_taxi_upto6_upto1000 extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    Button lp_taxi_upto6_upto1000btn;

    EditText lp_taxi_upto6_upto1000_act;
    EditText lp_taxi_upto6_upto1000_paod;
    EditText lp_taxi_upto6_upto1000_ll;
    EditText lp_taxi_upto6_upto1000_tax;
    EditText lp_taxi_upto6_scpassengers_upto1000;
    RadioGroup lp_taxi_upto6_upto1000_lpgkit;

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
        checkfunction(lp_taxi_upto6_upto1000.this);

        setContentView(R.layout.lp_taxi_upto6_upto1000);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Taxi Liability Policy");

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        findViewById(R.id.lp_taxi_upto6_upto1000btn).setOnClickListener(listener_lp_taxi_upto6_upto1000btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        lp_taxi_upto6_upto1000btn.setOnClickListener(this);
        //stop-passthevalues
        RadioButton pa_no=(RadioButton) findViewById(R.id.lp_taxi_upto1000_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_taxi_upto1000_paod_value);
                ed1.setText("0");
                ed1.setEnabled(false);
            }
        });
        RadioButton pa_yes=(RadioButton) findViewById(R.id.lp_taxi_upto1000_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_taxi_upto1000_paod_value);
                ed1.setText("320");
                ed1.setEnabled(true);
            }
        });

    }

    View.OnClickListener listener_lp_taxi_upto6_upto1000btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lp_taxi_upto6_upto1000.this, lpdisplay_taxi_upto6_upto1000.class);
            startActivity(intent);
        }

    };



    //start-passthevalues
    private void findAllViewsId() {
        lp_taxi_upto6_upto1000btn = (Button) findViewById(R.id.lp_taxi_upto6_upto1000btn);
        lp_taxi_upto6_upto1000_act = (EditText) findViewById(R.id.lp_taxi_upto6_upto1000_act);
        lp_taxi_upto6_upto1000_paod = (EditText) findViewById(R.id.lp_taxi_upto1000_paod_value);
        lp_taxi_upto6_upto1000_ll = (EditText) findViewById(R.id.lp_taxi_upto6_upto1000_ll);
        lp_taxi_upto6_upto1000_tax = (EditText) findViewById(R.id.lp_taxi_upto6_upto1000_tax);
        lp_taxi_upto6_scpassengers_upto1000=(EditText)findViewById(R.id.lp_taxi_upto6_scpassengers_upto1000);
        lp_taxi_upto6_upto1000_lpgkit = (RadioGroup) findViewById(R.id.lp_taxi_upto6_upto1000_lpgkit);

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), lpdisplay_taxi_upto6_upto1000.class);
        //Create a bundle object
        Bundle b = new Bundle();
        if(lp_taxi_upto6_scpassengers_upto1000.getText().toString().isEmpty()){
            lp_taxi_upto6_scpassengers_upto1000.setError("Fill the capacity");
        }else {
            //Inserts a String value into the mapping of this Bundle
            b.putString("lp_taxi_upto6_upto1000_act", lp_taxi_upto6_upto1000_act.getText().toString());
            b.putString("lp_taxi_upto6_upto1000_paod", lp_taxi_upto6_upto1000_paod.getText().toString());
            b.putString("lp_taxi_upto6_upto1000_ll", lp_taxi_upto6_upto1000_ll.getText().toString());
            b.putString("lp_taxi_upto6_upto1000_tax", lp_taxi_upto6_upto1000_tax.getText().toString());
            b.putString("lp_taxi_upto6_scpassengers_upto1000",String.valueOf(Integer.parseInt(lp_taxi_upto6_scpassengers_upto1000.getText().toString()) * 1162));

            //Toast.makeText(lp_taxi_upto6_upto1000.this,String.valueOf(Integer.parseInt(lp_taxi_upto6_scpassengers_upto1000.getText().toString()) * 1035), Toast.LENGTH_SHORT).show();
            int id1 = lp_taxi_upto6_upto1000_lpgkit.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(id1);
            b.putString("lp_taxi_upto6_upto1000_lpgkit", radioButton1.getText().toString());
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