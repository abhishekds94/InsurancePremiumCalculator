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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class lp_com_goods_private_40000 extends AppCompatActivity implements View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    Button lp_com_goods_private_40000btn;

    EditText lp_com_goods_private_40000_act;
    EditText lp_com_goods_private_40000_paod;
    EditText lp_com_goods_private_40000_ll;
    EditText lp_com_goods_private_40000_tax;
    EditText lp_com_goods_private_40000_coolie;
    EditText lp_com_goods_private_40000_nfpp;

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
        checkfunction(lp_com_goods_private_40000.this);

        setContentView(R.layout.lp_com_goods_private_40000);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Private Commercial Vehicle - Liability Policy");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findViewById(R.id.lp_com_goods_private_40000btn).setOnClickListener(listener_lp_com_goods_private_40000btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        lp_com_goods_private_40000btn.setOnClickListener(this);

        RadioButton pa_no=(RadioButton) findViewById(R.id.lp_com_goods_private_40000_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_com_goods_private_40000_paod_value);
                ed1.setText("0");
                ed1.setEnabled(false);
            }
        });
        RadioButton pa_yes=(RadioButton) findViewById(R.id.lp_com_goods_private_40000_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_com_goods_private_40000_paod_value);
                ed1.setText("320");
                ed1.setEnabled(true);
            }
        });
        //stop-passthevalues

    }

    View.OnClickListener listener_lp_com_goods_private_40000btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lp_com_goods_private_40000.this, lpdisplay_com_goods_private_40000.class);
            startActivity(intent);
        }

    };



    //start-passthevalues
    private void findAllViewsId() {
        lp_com_goods_private_40000btn = (Button) findViewById(R.id.lp_com_goods_private_40000btn);

        lp_com_goods_private_40000_act = (EditText) findViewById(R.id.lp_com_goods_private_40000_act);
        lp_com_goods_private_40000_paod = (EditText) findViewById(R.id.lp_com_goods_private_40000_paod_value);
        lp_com_goods_private_40000_ll = (EditText) findViewById(R.id.lp_com_goods_private_40000_ll);
        lp_com_goods_private_40000_tax = (EditText) findViewById(R.id.lp_com_goods_private_40000_tax);
        lp_com_goods_private_40000_coolie = (EditText) findViewById(R.id.lp_com_goods_private_40000_coolie);
        lp_com_goods_private_40000_nfpp= (EditText) findViewById(R.id.lp_com_goods_private_40000_nfpp);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), lpdisplay_com_goods_private_40000.class);
        Bundle b = new Bundle();
        b.putString("lp_com_goods_private_40000_act", this.lp_com_goods_private_40000_act.getText().toString());
        b.putString("lp_com_goods_private_40000_paod", this.lp_com_goods_private_40000_paod.getText().toString());
        b.putString("lp_com_goods_private_40000_ll", this.lp_com_goods_private_40000_ll.getText().toString());
        b.putString("lp_com_goods_private_40000_tax", this.lp_com_goods_private_40000_tax.getText().toString());
        b.putString("lp_com_goods_private_40000_coolie", this.lp_com_goods_private_40000_coolie.getText().toString());
        b.putString("lp_com_goods_private_40000_nfpp", this.lp_com_goods_private_40000_nfpp.getText().toString());
        intent.putExtras(b);
        String coolie_value = this.lp_com_goods_private_40000_coolie.getText().toString();
        String nfpp_value = this.lp_com_goods_private_40000_nfpp.getText().toString();
        if (coolie_value.equals("") || nfpp_value.equals("")) {

            Toast.makeText(getApplicationContext(),"Please enter all fields",Toast.LENGTH_SHORT).show();

        } else {

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