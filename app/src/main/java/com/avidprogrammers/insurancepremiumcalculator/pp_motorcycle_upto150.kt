package com.avidprogrammers.insurancepremiumcalculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class pp_motorcycle_upto150 extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "pp_motorcycle_upto150";
    private AdView mAdView;

    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;

    long diffInDays;
    String selected;

    Button pp_motorcycle_upto150;

    EditText pp_motorcycle_upto150_idv_value;
    EditText pp_motorcycle_upto150_date_value;
    EditText pp_motorcycle_upto150_cc_value;
    EditText pp_motorcycle_upto150_nd_value;
    EditText pp_motorcycle_upto150_ndd_value;
    EditText pp_motorcycle_upto150_uwd_value;
    EditText pp_motorcycle_upto150_paod_value;

    RadioGroup zoneRadioGroup;
    RadioGroup ndRadioGroup;

    static final int DATE_DIALOG_ID = 0;

    EditText ndd;
    int num1;

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
        checkfunction(pp_motorcycle_upto150.this);

        setContentView(R.layout.pp_motorcycle_upto150);
        getSupportActionBar().setTitle("Motorcycle Package Policy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        RadioButton nd_no = (RadioButton) findViewById(R.id.pp_motorcycle_upto150_nd_no);
        nd_no.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                EditText ed1=(EditText)findViewById(R.id.pp_motorcycle_upto150_nd_value);
                ed1.setEnabled(false);
                ed1.setText("0");



            }
        });
        RadioButton nd_yes = (RadioButton) findViewById(R.id.pp_motorcycle_upto150_nd_yes);
        nd_yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1=(EditText)findViewById(R.id.pp_motorcycle_upto150_nd_value);
                long diffInDays=CalculateDifferenceInDays();
                double nd_value1=0.00;
                if (diffInDays < 365) {
                    nd_value1=15;
                }else if (diffInDays >= 365 && diffInDays < 730 ) {
                    nd_value1 = 25;
                }else if (diffInDays >= 730 && diffInDays < 1825 ) {
                    nd_value1 = 35;
                }else if (diffInDays >= 1825 && diffInDays < 3650 ) {
                    nd_value1 = 40;
                }else if (diffInDays >= 3650 ){
                    nd_value1 = 0;
                }
                int  nd_value1_int =(int) nd_value1;
                ed1.setText(String.valueOf(nd_value1_int));

                ed1.setEnabled(false);
                ed1.setVisibility(View.VISIBLE);
            }
        });

        RadioButton pa_no =  findViewById(R.id.pp_motorcycle_upto150_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_motorcycle_upto150_paod_value.setText("0");
                pp_motorcycle_upto150_paod_value.setEnabled(false);
            }
        });
        RadioButton pa_yes = findViewById(R.id.pp_motorcycle_upto150_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_motorcycle_upto150_paod_value.setText("320");
                pp_motorcycle_upto150_paod_value.setEnabled(true);
            }
        });


        //Date-start
        mDateDisplay = (TextView) findViewById(R.id.pp_motorcycle_upto150_date_value);
        mPickDate = (Button) findViewById(R.id.pp_motorcycle_upto150_date_btn);


        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });


        java.util.Calendar c = java.util.Calendar.getInstance();
        mYear = c.get(java.util.Calendar.YEAR);
        mMonth = c.get(java.util.Calendar.MONTH);
        mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

        updateDisplay();
        //Date-end


        //spinner-start
        Spinner spin = (Spinner) findViewById(R.id.pp_motorcycle_upto150_ncb);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,ncb);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        //spinner-end


        findViewById(R.id.pp_motorcycle_upto150_btn).setOnClickListener(listener_pp_motorcycle_upto150);


        //start-passvalues
        //Get the ids of view objects
        findAllViewsId();

        pp_motorcycle_upto150.setOnClickListener(this);
        //stop-passvalues

        ndd= (EditText) findViewById(R.id.pp_motorcycle_upto150_ndd_value);
        ndd.addTextChangedListener(textWatcher);

    };


    View.OnClickListener listener_pp_motorcycle_upto150 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pp_motorcycle_upto150.this, ppdisplay_motorcycle_upto150.class);
            startActivity(intent);
        }
    };


    //start-passvalues
    private void findAllViewsId() {
        pp_motorcycle_upto150 = (Button) findViewById(R.id.pp_motorcycle_upto150_btn);

        pp_motorcycle_upto150_idv_value = (EditText) findViewById(R.id.pp_motorcycle_upto150_idv_value);
        pp_motorcycle_upto150_date_value = (EditText) findViewById(R.id.pp_motorcycle_upto150_date_value);
        pp_motorcycle_upto150_cc_value = (EditText) findViewById(R.id.pp_motorcycle_upto150_cc_value);
        pp_motorcycle_upto150_nd_value = (EditText) findViewById(R.id.pp_motorcycle_upto150_nd_value);
        pp_motorcycle_upto150_ndd_value = (EditText) findViewById(R.id.pp_motorcycle_upto150_ndd_value);
        pp_motorcycle_upto150_uwd_value = (EditText) findViewById(R.id.pp_motorcycle_upto150_uwd_value);
        pp_motorcycle_upto150_paod_value = findViewById(R.id.pp_motorcycle_upto150_paod);

        zoneRadioGroup = (RadioGroup) findViewById(R.id.pp_motorcycle_upto150_zone);
        ndRadioGroup = (RadioGroup) findViewById(R.id.pp_motorcycle_upto150_nd);

    }


    @Override
    public void onClick(View v) {
        //Check for any EditText being null
        String idv_value = pp_motorcycle_upto150_idv_value.getText().toString();

        String uwd_value = pp_motorcycle_upto150_uwd_value.getText().toString();

        String ndd_value = pp_motorcycle_upto150_ndd_value.getText().toString();


        if (idv_value.equals("") || uwd_value.equals("") || ndd_value.equals("")) {

            Snackbar bar = Snackbar.make(v, "Please enter all fields to Calculate!", Snackbar.LENGTH_LONG);
            TextView mainTextView = (TextView) (bar.getView()).findViewById(com.google.android.material.R.id.snackbar_text);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                mainTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else
                mainTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            mainTextView.setGravity(Gravity.CENTER_HORIZONTAL);

 /*                   .setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Handle user action
                        }
                    });*/
            bar.setActionTextColor(getResources().getColor(R.color.colorSnackBarDismiss));
            TextView tv = (TextView) bar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.colorSnackBar));
            View view = bar.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorSnackBarBg));
            bar.show();

        } else {
            Intent intent = new Intent(getApplicationContext(), ppdisplay_motorcycle_upto150.class);
            //Create a bundle object
            Bundle b = new Bundle();

            //Inserts a String value into the mapping of this Bundle
            b.putString("pp_motorcycle_upto150_idv_value", pp_motorcycle_upto150_idv_value.getText().toString());
            b.putString("pp_motorcycle_upto150_date_value", pp_motorcycle_upto150_date_value.getText().toString());
            b.putString("pp_motorcycle_upto150_cc_value", pp_motorcycle_upto150_cc_value.getText().toString());
            b.putString("pp_motorcycle_upto150_nd_value", pp_motorcycle_upto150_nd_value.getText().toString());
            b.putString("pp_motorcycle_upto150_ndd_value", pp_motorcycle_upto150_ndd_value.getText().toString());
            b.putString("pp_motorcycle_upto150_uwd_value", pp_motorcycle_upto150_uwd_value.getText().toString());
            b.putString("pp_motorcycle_upto150_paod_value", pp_motorcycle_upto150_paod_value.getText().toString());

            int id1 = zoneRadioGroup.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(id1);
            b.putString("pp_motorcycle_upto150_zone", radioButton1.getText().toString());

            int id2 = ndRadioGroup.getCheckedRadioButtonId();
            RadioButton radioButton2 = (RadioButton) findViewById(id2);
            b.putString("pp_motorcycle_upto150_nd", radioButton2.getText().toString());

            //Spinner value selected
            b.putString("pp_motorcycle_upto150_spinner_value", selected);

            //
            b.putInt("year", mYear);
            b.putInt("month", mMonth);
            b.putInt("day", mDay);
            //Add the bundle to the intent.
            intent.putExtras(b);

            //start the DisplayActivity
            startActivity(intent);
        }
    }
    //stop-passvalues


    //Date-start
    private void updateDisplay() {
        mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
        EditText ed1=(EditText)findViewById(R.id.pp_motorcycle_upto150_nd_value);
        long diffInDays=CalculateDifferenceInDays();
        double nd_value1=0.00;
        if (diffInDays < 365) {
            nd_value1=15;
        }else if (diffInDays >= 365 && diffInDays < 730 ) {
            nd_value1 = 25;
        }else if (diffInDays >= 730 && diffInDays < 1825 ) {
            nd_value1 = 35;
        }else if (diffInDays >= 1825 && diffInDays < 3650 ) {
            nd_value1 = 40;
        }else if (diffInDays >= 3650 ){
            nd_value1 = 0;
        }
        int  nd_value1_int =(int) nd_value1;
        ed1.setText(String.valueOf(nd_value1_int));
    }

    public long  CalculateDifferenceInDays(){

        int mYear_now,mMonth_now,mDay_now;
        // Create Calendar instance
        java.util.Calendar calendar1 = java.util.Calendar.getInstance();
        java.util.Calendar calendar2 = java.util.Calendar.getInstance();
        mYear_now = calendar1.get(java.util.Calendar.YEAR);
        mMonth_now = calendar1.get(java.util.Calendar.MONTH);
        mDay_now = calendar1.get(java.util.Calendar.DAY_OF_MONTH);

        // Set the values for the calendar fields YEAR, MONTH, and DAY_OF_MONTH.
        calendar2.set(mYear, mMonth, mDay);
        calendar1.set(mYear_now, mMonth_now, mDay_now);

            /*
            * Use getTimeInMillis() method to get the Calendar's time value in
            * milliseconds. This method returns the current time as UTC
            * milliseconds from the epoch
            */
        long miliSecondForDate1 = calendar1.getTimeInMillis();
        long miliSecondForDate2 = calendar2.getTimeInMillis();

        // Calculate the difference in millisecond between two dates
        long diffInMilis = miliSecondForDate1 - miliSecondForDate2;

             /*
              * Now we have difference between two date in form of millsecond we can
              * easily convert it Minute / Hour / Days by dividing the difference
              * with appropriate value. 1 Second : 1000 milisecond 1 Hour : 60 * 1000
              * millisecond 1 Day : 24 * 60 * 1000 milisecond
              */

        long diffInSecond = diffInMilis / 1000;
        long diffInMinute = diffInMilis / (60 * 1000);
        long diffInHour = diffInMilis / (60 * 60 * 1000);
        diffInDays = diffInMilis / ( 24 * 60 * 60 * 1000);

        return diffInDays;


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
    public void onItemSelected(AdapterView<?> parent, View arg1, int position,long id) {

        Toast.makeText(getApplicationContext(), ncb[position], Toast.LENGTH_LONG);
        selected=parent.getItemAtPosition(position).toString();
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
                    Toast.makeText(pp_motorcycle_upto150.this,"NDD range should be of 1-35",Toast.LENGTH_SHORT).show();
                    ndd.setText("");
                    num1=-1;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

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
        checkingStatus.notification(isConnected,this);
    }

}
