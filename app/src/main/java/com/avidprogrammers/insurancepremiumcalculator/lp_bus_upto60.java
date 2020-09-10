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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class lp_bus_upto60 extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;
    Button lp_bus_upto60btn;

    EditText lp_bus_upto60_act;
    EditText lp_bus_upto60_paod;
    EditText lp_bus_upto60_tax;

    EditText lp_bus_scpassengers_upto60;
    EditText lp_bus_driver_upto60;
    EditText lp_bus_conductor_upto60;

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
        checkfunction(lp_bus_upto60.this);

        setContentView(R.layout.lp_bus_upto60);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Passenger Vehicle Liability Policy");

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        findViewById(R.id.lp_bus_upto60btn).setOnClickListener(listener_lp_bus_upto60btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        lp_bus_upto60btn.setOnClickListener(this);
        //stop-passthevalues

        RadioButton pa_no=(RadioButton) findViewById(R.id.lp_bus_upto60_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_bus_upto60_paod);
                ed1.setText("0");
                ed1.setEnabled(false);
            }
        });
        RadioButton pa_yes=(RadioButton) findViewById(R.id.lp_bus_upto60_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_bus_upto60_paod);
                ed1.setText("320");
                ed1.setEnabled(true);
            }
        });

    }

    View.OnClickListener listener_lp_bus_upto60btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lp_bus_upto60.this, lpdisplay_bus_upto60.class);
            startActivity(intent);
        }

    };



    //start-passthevalues
    private void findAllViewsId() {
        lp_bus_upto60btn = (Button) findViewById(R.id.lp_bus_upto60btn);

        lp_bus_upto60_act = (EditText) findViewById(R.id.lp_bus_upto60_act);
        lp_bus_upto60_paod = (EditText) findViewById(R.id.lp_bus_upto60_paod);
        lp_bus_upto60_tax = (EditText) findViewById(R.id.lp_bus_upto60_tax);

        lp_bus_scpassengers_upto60 = (EditText) findViewById(R.id.lp_bus_scpassengers_upto60);
        lp_bus_driver_upto60 = (EditText) findViewById(R.id.lp_bus_driver_upto60);
        lp_bus_conductor_upto60 = (EditText) findViewById(R.id.lp_bus_conductor_upto60);


    }

    //calculation
    public int value(){
        double Totalcost=14494;
        if(!lp_bus_scpassengers_upto60.getText().toString().trim().isEmpty()){
            if(!lp_bus_driver_upto60.getText().toString().trim().isEmpty()){
                if(!lp_bus_conductor_upto60.getText().toString().trim().isEmpty()){
                    Totalcost=14494+(Integer.parseInt(lp_bus_upto60_paod.getText().toString()))+
                            (Integer.parseInt(lp_bus_conductor_upto60.getText().toString())*50)
                            +(Integer.parseInt(lp_bus_scpassengers_upto60.getText().toString())*886)
                            +(Integer.parseInt(lp_bus_driver_upto60.getText().toString())*50);
                    Totalcost=Totalcost*(1.18);
                    return  (int)Math.ceil(Totalcost);
                }else{
                    lp_bus_conductor_upto60.setError("Empty field");
                }
            }else{
                lp_bus_driver_upto60.setError("Empty Field");
            }
        }else{
            lp_bus_scpassengers_upto60.setError("Empty Field");
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        int x=value();
        if(x!=0) {
            Intent intent = new Intent(getApplicationContext(), lpdisplay_bus_upto60.class);
            //Create a bundle object
            Bundle b = new Bundle();

            //Inserts a String value into the mapping of this Bundle
            b.putString("lp_bus_upto60_act", lp_bus_upto60_act.getText().toString());
            b.putString("lp_bus_upto60_paod", lp_bus_upto60_paod.getText().toString());
            b.putString("lp_bus_upto60_tax", lp_bus_upto60_tax.getText().toString());

            b.putString("lp_bus_scpassengers_upto60", lp_bus_scpassengers_upto60.getText().toString());
            b.putString("lp_bus_driver_upto60", lp_bus_driver_upto60.getText().toString());
            b.putString("lp_bus_conductor_upto60", lp_bus_conductor_upto60.getText().toString());
            //final value with tax
            b.putString("lp_bus_total_premium",String.valueOf(x));
            //Add the bundle to the intent.
            intent.putExtras(b);

            //start the DisplayActivity
            startActivity(intent);
        }else{
            Toast.makeText(lp_bus_upto60.this, "Empty fields", Toast.LENGTH_SHORT).show();
        }
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