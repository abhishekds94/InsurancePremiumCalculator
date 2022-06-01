package com.avidprogrammers.insurancepremiumcalculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.concurrent.TimeUnit;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class pp_bus_upto36 extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "pp_bus_upto36";
    private AdView mAdView;

    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;

    Button pp_bus_upto36_btn;

    EditText pp_bus_upto36_idv_value;
    EditText pp_bus_upto36_date_value;
    EditText pp_bus_upto36_cc_value;
    EditText pp_bus_upto36_nd_value;
    EditText pp_bus_upto36_ndd_value;
    EditText pp_bus_upto36_uwd_value;
    EditText pp_bus_scpassengers_upto36;
    EditText pp_bus_driver_upto36;
    EditText pp_bus_conductor_upto36;
    EditText pp_bus_upto36_paod_value;

    RadioGroup pp_bus_upto36_zone;
    //Radio Button Declaring
    RadioButton pp_bus_upto36_nd_value_no,pp_bus_upto36_nd_value_yes;
    RadioButton pp_bus_upto36_idv_value_c,pp_bus_upto36_idv_value_b,pp_bus_upto36_idv_value_a;

    double nild_value = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(conn);
    }

    static final int DATE_DIALOG_ID = 0;

    EditText ndd;
    int num1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkingStatus=new CheckingStatus();
        conn=new ConnectivityReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(conn, intentFilter);
        checkfunction(pp_bus_upto36.this);

        setContentView(R.layout.pp_bus_upto36);
        getSupportActionBar().setTitle("Passenger Vehicle Package Policy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        RadioButton pa_no =  findViewById(R.id.pp_bus_upto36_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_bus_upto36_paod_value.setText("0");
                pp_bus_upto36_paod_value.setEnabled(false);
            }
        });
        RadioButton pa_yes = findViewById(R.id.pp_bus_upto36_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_bus_upto36_paod_value.setText("320");
                pp_bus_upto36_paod_value.setEnabled(true);
            }
        });


        //Date-start
        mDateDisplay = (TextView) findViewById(R.id.pp_bus_upto36_date_value);
        mPickDate = (Button) findViewById(R.id.txtbtn);


        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });


        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //Date-end


        //spinner-start
        Spinner spin = (Spinner) findViewById(R.id.pp_bus_upto36_ncb_value);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,ncb);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        //spinner-end


        findViewById(R.id.pp_bus_upto36_btn).setOnClickListener(listener_pp_bus_upto36_btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();
        updateDisplay();
        radio_listener();
        pp_bus_upto36_btn.setOnClickListener(this);
        //stop-passthevalues

        ndd= (EditText) findViewById(R.id.pp_bus_upto36_ndd_value);
        ndd.addTextChangedListener(textWatcher);

    };


    View.OnClickListener listener_pp_bus_upto36_btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pp_bus_upto36.this, ppdisplay_bus_upto36.class);
            startActivity(intent);
        }
    };


    //start-passthevalues
    private void findAllViewsId() {
        pp_bus_upto36_btn = (Button) findViewById(R.id.pp_bus_upto36_btn);

        pp_bus_upto36_idv_value = (EditText) findViewById(R.id.pp_bus_upto36_idv_value);
        pp_bus_upto36_date_value = (EditText) findViewById(R.id.pp_bus_upto36_date_value);
        pp_bus_upto36_cc_value = (EditText) findViewById(R.id.pp_bus_upto36_cc_value);
        pp_bus_upto36_nd_value = (EditText) findViewById(R.id.pp_bus_upto36_nd_value);
        pp_bus_upto36_ndd_value = (EditText) findViewById(R.id.pp_bus_upto36_ndd_value);
        pp_bus_upto36_uwd_value = (EditText) findViewById(R.id.pp_bus_upto36_uwd_value);
        pp_bus_scpassengers_upto36 = (EditText) findViewById(R.id.pp_bus_scpassengers_upto36);
        pp_bus_driver_upto36 = (EditText) findViewById(R.id.pp_bus_driver_upto36);
        pp_bus_conductor_upto36 = (EditText) findViewById(R.id.pp_bus_conductor_upto36);
        pp_bus_upto36_paod_value = findViewById(R.id.pp_bus_upto36_paod);

        final ScrollView scrollview = ((ScrollView) findViewById(R.id.pp_bus_upto36_sv));
        pp_bus_conductor_upto36.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_DONE)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            pp_bus_upto36_btn.requestFocus();
                        }
                    });
                return false;
            }
        });

        pp_bus_upto36_zone = (RadioGroup) findViewById(R.id.pp_bus_upto36_zone);

        //radiobutton
        // zones a,b,c
        pp_bus_upto36_idv_value_a=(RadioButton)findViewById(R.id.pp_bus_upto36_idv_value_a);
        pp_bus_upto36_idv_value_b=(RadioButton)findViewById(R.id.pp_bus_upto36_idv_value_b);
        pp_bus_upto36_idv_value_c=(RadioButton)findViewById(R.id.pp_bus_upto36_idv_value_c);
        // ND Yes or No
        pp_bus_upto36_nd_value_yes=(RadioButton)findViewById(R.id.pp_bus_upto36_nd_value_yes);
        pp_bus_upto36_nd_value_no=(RadioButton)findViewById(R.id.pp_bus_upto36_nd_value_no);

    }

    public void radio_listener(){
        pp_bus_upto36_nd_value_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nd();
            }
        });
        pp_bus_upto36_nd_value_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp_bus_upto36_nd_value.setText("0", TextView.BufferType.EDITABLE);
            }
        });
    }
    int ncb_value=0;
    double zone[][]=new double[][]{{1.680,1.722,1.764},{1.672,1.714,1.756},{1.656,1.697,1.739}};
    public double value(){
        double Totalcost=450;
        if(!pp_bus_upto36_idv_value.getText().toString().trim().isEmpty()){
            if(!pp_bus_upto36_uwd_value.getText().toString().trim().isEmpty()){
                if(!pp_bus_scpassengers_upto36.getText().toString().trim().isEmpty()){
                    if(!pp_bus_driver_upto36.getText().toString().trim().isEmpty()){
                        if(!pp_bus_upto36_ndd_value.getText().toString().trim().isEmpty()) {
                            if (!pp_bus_conductor_upto36.getText().toString().isEmpty()) {
                                Totalcost += (Integer.parseInt(pp_bus_upto36_idv_value.getText().toString()) * zone_checking()) / 100;
                                if (pp_bus_upto36_nd_value_yes.isChecked()) {
                                    Totalcost *= (1.18);
                                }
                                Totalcost = Math.ceil(Totalcost);
                                double temp = (Totalcost * (Integer.parseInt(pp_bus_upto36_nd_value.getText().toString())) / 100);
                                if (pp_bus_upto36_nd_value_yes.isChecked()) {
                                    nild_value = temp;
                                }
                                double temp2 = (temp * (Integer.parseInt(pp_bus_upto36_ndd_value.getText().toString())) / 100);
                                Totalcost += (temp - temp2);
                                //Totalcost += (Totalcost * (Integer.parseInt(pp_bus_upto36_nd_value.getText().toString())) / 100);
                                Totalcost -= (Totalcost * (Integer.parseInt(pp_bus_upto36_uwd_value.getText().toString())) / 100);
                                Totalcost -= (Totalcost * (ncb_value) / 100);
                                return Math.floor(Totalcost);
                            } else {
                                pp_bus_conductor_upto36.setError("Empty Field");
                            }
                        }
                        else
                        {
                            pp_bus_upto36_ndd_value.setError("Empty Field");
                        }
                    }else{
                        pp_bus_driver_upto36.setError("Empty Field");
                    }}else{
                    pp_bus_scpassengers_upto36.setError("Empty Field");
                }}else{
                pp_bus_upto36_uwd_value.setError("Empty Field");
            }}else {
            pp_bus_upto36_idv_value.setError("Empty Field");
        }
        return 0;
    }
    @Override
    public void onClick(View v) {
        double x = value();
        if (x != 0) {
            Intent intent = new Intent(getApplicationContext(), ppdisplay_bus_upto36.class);
            //Create a bundle object
            Bundle b = new Bundle();

            //Inserts a String value into the mapping of this Bundle
            b.putString("pp_bus_upto36_idv_value", pp_bus_upto36_idv_value.getText().toString());
            b.putString("pp_bus_upto36_date_value", pp_bus_upto36_date_value.getText().toString());
            b.putString("pp_bus_upto36_cc_value", pp_bus_upto36_cc_value.getText().toString());
            b.putString("pp_bus_upto36_nd_value", pp_bus_upto36_nd_value.getText().toString());
            b.putString("pp_bus_upto36_ndd_value", pp_bus_upto36_ndd_value.getText().toString());
            b.putString("pp_bus_upto36_uwd_value", pp_bus_upto36_uwd_value.getText().toString());
            b.putString("pp_bus_upto36_paod_value", pp_bus_upto36_paod_value.getText().toString());
            b.putString("pp_bus_scpassengers_upto36", pp_bus_scpassengers_upto36.getText().toString());
            int z=Integer.parseInt(pp_bus_driver_upto36.getText().toString())*50;
            int z1=Integer.parseInt(pp_bus_conductor_upto36.getText().toString())*50;
            b.putString("pp_bus_driver_upto36",String.valueOf(z) );
            b.putString("pp_bus_conductor_upto36",String.valueOf(z1));

            //external inputs
            if(pp_bus_upto36_nd_value_yes.isChecked()) {
                b.putString("pp_bus_imt_value",String.valueOf(15));
            }else{
                b.putString("pp_bus_imt_value",String.valueOf(0));
            }
            b.putString("pp_bus_ncb_value",String.valueOf(ncb_value));
            b.putString("pp_bus_total_value",String.valueOf(x));
            int id1 = pp_bus_upto36_zone.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(id1);
            b.putString("pp_bus_upto36_zone", radioButton1.getText().toString());


            //Add the bundle to the intent.
            intent.putExtras(b);

            //start the DisplayActivity
            startActivity(intent);
        }

    }
    //stop-passthevalues

    public void nd(){
        long x=score_time_update(mDay, mMonth + 1, mYear);
        if(pp_bus_upto36_nd_value_yes.isChecked()){
            if(x<1) {
                pp_bus_upto36_nd_value.setText("15", TextView.BufferType.EDITABLE);
            }else if(x>=1&&x<5){
                pp_bus_upto36_nd_value.setText("25", TextView.BufferType.EDITABLE);
            }else if(x>=5){
                pp_bus_upto36_nd_value.setText("0", TextView.BufferType.EDITABLE);
            }
        }else{
            pp_bus_upto36_nd_value.setText("0", TextView.BufferType.EDITABLE);
        }
    }
    public double zone_checking(){
        long l=score_time_update(mDay,mMonth+1,mYear);
        String x = null;
        if(pp_bus_upto36_idv_value_a.isChecked()){
            x="A";
        }else if(pp_bus_upto36_idv_value_b.isChecked()){
            x="B";
        }else if (pp_bus_upto36_idv_value_c.isChecked()){
            x="C";
        }
        if (l < 5) {
            assert x != null;
            if(x.equals("A")) {
                return zone[0][0];
            } else if (x.equals("B")) {
                return zone[1][0];
            }else if (x.equals("C")){
                return zone[2][0];
            }
        } else if (l >= 5 && l < 7) {
            if (x.equals("A")) {
                return zone[0][1];
            } else if (x.equals("B")) {
                return zone[1][1];
            }else if (x.equals("C")){
                return zone[2][1];
            }
        } else if (l >7) {
            if (x.equals("A")) {
                return zone[0][2];
            } else if (x.equals("B")) {
                return zone[1][2];
            }else if (x.equals("C")){
                return zone[2][2];
            }
        }
        return 0;
    }
    public long score_time_update(int x, int y, int z){
        //Toast.makeText(pp_bus_upto1000.this, ""+String.valueOf(z)+"-"+String.valueOf(x)+"-"+String.valueOf(y), Toast.LENGTH_SHORT).show();
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        Calendar c=Calendar.getInstance();
        date1.clear();
        date1.set(z, y, x);
        date2.clear();
        date2.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        long msdiff=-date2.getTimeInMillis()+date1.getTimeInMillis();
        long daydiff= TimeUnit.MILLISECONDS.toDays(msdiff);
        daydiff=Math.abs((daydiff));
        return daydiff/365;

    }
    //Date-start
    private void updateDisplay() {
        mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
        nd();
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }
    //Date-end


    //BackButton in title bar
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    //Spinner
    String[] ncb={"0","20","25","35","45","50"};

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        ncb_value=Integer.parseInt(ncb[position]);
        Toast.makeText(getApplicationContext(), ncb[position], Toast.LENGTH_LONG);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }



    //To check NDD value
    TextWatcher textWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(ndd.getText().length()>0) {
                int num = Integer.parseInt(ndd.getText().toString());
                if (num>=0 && num<=35)
                {
                    //save the number
                    num1=num;
                }
                else{
                    Toast.makeText(pp_bus_upto36.this,"NDD range should be of 1-35",Toast.LENGTH_SHORT).show();
                    ndd.setText("");
                    num1=-1;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


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
        checkingStatus.notification(isConnected,this);
    }

}