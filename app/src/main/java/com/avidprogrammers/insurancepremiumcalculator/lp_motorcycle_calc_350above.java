package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class lp_motorcycle_calc_350above extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;
    Button lp_motocyc_calc_350above_btn;

    EditText lp_motocyc_calc_350above_act;
    EditText lp_motocyc_calc_350above_paod;
    EditText lp_motocyc_calc_350above_tax;

    RadioGroup lp_motocyc_calc_350above_lpgkit;

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
        checkfunction(lp_motorcycle_calc_350above.this);

        setContentView(R.layout.lp_motorcycle_calc_350above);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Motorcycle Above 350CC Liability Policy");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findViewById(R.id.lp_motocyc_calc_350above_btn).setOnClickListener(listener_lp_motocyc_calc_350above_btn);

        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        lp_motocyc_calc_350above_btn.setOnClickListener(this);


        RadioButton pa_no = findViewById(R.id.lp_motocyc_calc_350above_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1 = findViewById(R.id.lp_motocyc_calc_350above_paod);
                ed1.setText("0");
                ed1.setEnabled(false);
            }
        });
        RadioButton pa_yes = findViewById(R.id.lp_motocyc_calc_350above_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1 = findViewById(R.id.lp_motocyc_calc_350above_paod);
                ed1.setText("320");
                ed1.setEnabled(true);
            }
        });
    }
    public void checkfunction(Context context){
        boolean isConnected=ConnectivityReceiver.isConnected();
        checkingStatus.notification(isConnected,context);

    }

    View.OnClickListener listener_lp_motocyc_calc_350above_btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lp_motorcycle_calc_350above.this, lp_motorcycle_350above.class);
            startActivity(intent);
        }

    };



    //start-passthevalues
    private void findAllViewsId() {
        lp_motocyc_calc_350above_btn = (Button) findViewById(R.id.lp_motocyc_calc_350above_btn);

        lp_motocyc_calc_350above_act = (EditText) findViewById(R.id.lp_motocyc_calc_350above_act);
        lp_motocyc_calc_350above_paod = (EditText) findViewById(R.id.lp_motocyc_calc_350above_paod);
        lp_motocyc_calc_350above_tax = (EditText) findViewById(R.id.lp_motocyc_calc_350above_tax);

        //lp_goodsauto_private_lpgkit = (RadioGroup) findViewById(R.id.lp_goodsauto_private_lpgkit);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), lp_motorcycle_350above.class);
        //Create a bundle object
        Bundle b = new Bundle();

        //Inserts a String value into the mapping of this Bundle
        b.putString("lp_motocyc_calc_350above_act", lp_motocyc_calc_350above_act.getText().toString());
        b.putString("lp_motocyc_calc_350above_paod", lp_motocyc_calc_350above_paod.getText().toString());
        b.putString("lp_motocyc_calc_350above_tax", lp_motocyc_calc_350above_tax.getText().toString());


            /*int id1 = lp_goodsauto_private_lpgkit.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(id1);
            b.putString("lp_goodsauto_private_lpgkit", radioButton1.getText().toString());*/


        //Add the bundle to the intent.
        intent.putExtras(b);

        //start the DisplayActivity
        startActivity(intent);

    }
    //stop-passthevalues

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
