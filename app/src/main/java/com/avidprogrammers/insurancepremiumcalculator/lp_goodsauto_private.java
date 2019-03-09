package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

public class lp_goodsauto_private extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;
    Button lp_goodsauto_privatebtn;

    EditText lp_goodsauto_private_act;
    EditText lp_goodsauto_private_paod;
    EditText lp_goodsauto_private_ll;
    EditText lp_goodsauto_private_tax;
    EditText lp_goodsauto_private_nfpp;
    EditText lp_goodsauto_private_coolie;

    RadioGroup lp_goodsauto_private_lpgkit;

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
        checkfunction(lp_goodsauto_private.this);

        setContentView(R.layout.lp_goodsauto_private);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Goods Auto Private Liability Policy");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findViewById(R.id.lp_goodsauto_privatebtn).setOnClickListener(listener_lp_goodsauto_privatebtn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        lp_goodsauto_privatebtn.setOnClickListener(this);
        //stop-passthevalues

        RadioButton pa_no=(RadioButton) findViewById(R.id.lp_goodsauto_private_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_goodsauto_private_paod);
                ed1.setText("0");
                ed1.setEnabled(false);
            }
        });
        RadioButton pa_yes=(RadioButton) findViewById(R.id.lp_goodsauto_private_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.lp_goodsauto_private_paod);
                ed1.setText("320");
                ed1.setEnabled(true);
            }
        });

    }

    View.OnClickListener listener_lp_goodsauto_privatebtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lp_goodsauto_private.this, lpdisplay_goodsauto_private.class);
            startActivity(intent);
        }

    };



    //start-passthevalues
    private void findAllViewsId() {
        lp_goodsauto_privatebtn = (Button) findViewById(R.id.lp_goodsauto_privatebtn);

        lp_goodsauto_private_act = (EditText) findViewById(R.id.lp_goodsauto_private_act);
        lp_goodsauto_private_paod = (EditText) findViewById(R.id.lp_goodsauto_private_paod);
        lp_goodsauto_private_ll = (EditText) findViewById(R.id.lp_goodsauto_private_ll);
        lp_goodsauto_private_tax = (EditText) findViewById(R.id.lp_goodsauto_private_tax);
        lp_goodsauto_private_nfpp = (EditText) findViewById(R.id.lp_goodsauto_private_nfpp);
        lp_goodsauto_private_coolie = (EditText) findViewById(R.id.lp_goodsauto_private_coolie);

        lp_goodsauto_private_lpgkit = (RadioGroup) findViewById(R.id.lp_goodsauto_private_lpgkit);

    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    int validation(){
        if(lp_goodsauto_private_nfpp.getText().toString().isEmpty() || lp_goodsauto_private_coolie.getText().toString().isEmpty() )
        {
            Toast.makeText(this,"Enter Nfpp or Coolie First",Toast.LENGTH_SHORT).show();

            return 0;
        }
        return 1;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), lpdisplay_goodsauto_private.class);
        //Create a bundle object
        Bundle b = new Bundle();

        int valid=validation();
        if(valid==1) {
            //Inserts a String value into the mapping of this Bundle
            b.putString("lp_goodsauto_private_act", lp_goodsauto_private_act.getText().toString());
            b.putString("lp_goodsauto_private_paod", lp_goodsauto_private_paod.getText().toString());
            b.putString("lp_goodsauto_private_ll", lp_goodsauto_private_ll.getText().toString());
            b.putString("lp_goodsauto_private_tax", lp_goodsauto_private_tax.getText().toString());
            b.putString("lp_goodsauto_private_nfpp", lp_goodsauto_private_nfpp.getText().toString());
            b.putString("lp_goodsauto_private_coolie", lp_goodsauto_private_coolie.getText().toString());


            int id1 = lp_goodsauto_private_lpgkit.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(id1);
            b.putString("lp_goodsauto_private_lpgkit", radioButton1.getText().toString());


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