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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class lp_agri extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

        ConnectivityReceiver conn;

        CheckingStatus checkingStatus;

        private AdView mAdView;

        Button lp_agribtn;

        EditText lp_agri_act;
        EditText lp_agri_paod;
        EditText lp_agri_ll;
        EditText lp_agri_tax;
        EditText lp_agri_coolie;
        EditText lp_agri_trailer;
        RadioGroup lp_agri_lpgtype;

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
        checkfunction(lp_agri.this);

        setContentView(R.layout.lp_agri);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tractors & Trailers Liability Policy");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findViewById(R.id.lp_agribtn).setOnClickListener(listener_lp_agribtn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        lp_agribtn.setOnClickListener(this);
        //stop-passthevalues


        RadioButton pa_no=(RadioButton) findViewById(R.id.lp_agri_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_agri_paod);
                ed1.setText("0");
                ed1.setEnabled(false);
            }
        });
        RadioButton pa_yes=(RadioButton) findViewById(R.id.lp_agri_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_agri_paod);
                ed1.setText("320");
                ed1.setEnabled(true);
            }
        });

        RadioButton trailer_no = (RadioButton) findViewById(R.id.lp_agri_lpgtype_value_inbuilt);
        trailer_no.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                EditText ed1=(EditText)findViewById(R.id.lp_agri_trailer_value);
                ed1.setEnabled(false);
                ed1.setText("0");



            }
        });
        RadioButton trailer_yes = (RadioButton) findViewById(R.id.lp_agri_lpgtype_value_fixed);
        trailer_yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1 = (EditText) findViewById(R.id.lp_agri_trailer_value);
                ed1.setEnabled(false);
                ed1.setText("2091");
            }
        });
    }

    View.OnClickListener listener_lp_agribtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lp_agri.this, lpdisplay_agri.class);
            startActivity(intent);
        }

    };



    //start-passthevalues
    private void findAllViewsId() {
        lp_agribtn = (Button) findViewById(R.id.lp_agribtn);
        lp_agri_trailer = (EditText) findViewById(R.id.lp_agri_trailer_value);
        lp_agri_trailer.setEnabled(false);
        lp_agri_trailer.setText("2091");
        lp_agri_act = (EditText) findViewById(R.id.lp_agri_act);
        lp_agri_paod = (EditText) findViewById(R.id.lp_agri_paod);
        lp_agri_ll = (EditText) findViewById(R.id.lp_agri_ll);
        lp_agri_tax = (EditText) findViewById(R.id.lp_agri_tax);
        lp_agri_coolie = (EditText) findViewById(R.id.lp_agri_coolie);
        lp_agri_lpgtype = (RadioGroup) findViewById(R.id.pp_agri_lpgtype);



    }



    @Override
    public void onClick(View v) {

        if((lp_agri_coolie.getText().toString()).equals("")){
            Toast.makeText(getApplicationContext(),"Please enter Coolie Value",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(getApplicationContext(), lpdisplay_agri.class);
            //Create a bundle object
            Bundle b = new Bundle();

            //Inserts a String value into the mapping of this Bundle
            b.putString("lp_agri_act", lp_agri_act.getText().toString());
            b.putString("lp_agri_paod", lp_agri_paod.getText().toString());
            b.putString("lp_agri_ll", lp_agri_ll.getText().toString());
            b.putString("lp_agri_tax", lp_agri_tax.getText().toString());
            b.putString("lp_agri_coolie", lp_agri_coolie.getText().toString());

            int id1 = lp_agri_lpgtype.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(id1);
            b.putString("lp_agri_lpgtype", radioButton1.getText().toString());

            //Add the bundle to the intent.
            intent.putExtras(b);

            //start the DisplayActivity
            startActivity(intent);
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