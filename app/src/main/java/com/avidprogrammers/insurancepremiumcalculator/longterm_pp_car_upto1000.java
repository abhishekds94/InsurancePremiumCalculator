package com.avidprogrammers.insurancepremiumcalculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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

import java.util.Calendar;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class longterm_pp_car_upto1000 extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "lt_bp_car_upto1000";
    private AdView mAdView;
    String selected;
    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    long diffInDays;

    Button lt_bp_car_upto1000_btn;

    EditText lt_bp_car_upto1000_idv_value;
    EditText lt_bp_car_upto1000_date_value;
    EditText lt_bp_car_upto1000_cc_value;
    EditText lt_bp_car_upto1000_nd_value;
    EditText lt_bp_car_upto1000_ndd_value;
    EditText lt_bp_car_upto1000_uwd_value;
    EditText lt_bp_car_scpassengers_upto1000;
    EditText lt_bp_car_upto1000_lpgtype_value;
    EditText lt_bp_car_upto1000_paod_value;

    RadioGroup lt_bp_car_upto1000_zone;
    RadioGroup lt_bp_car_upto1000_lpg;
    RadioGroup lt_bp_car_upto1000_lpgtype;
    RadioGroup lt_bp_car_upto1000_antitheft;
    RadioGroup lt_bp_car_patooccupants_upto1000;
    RadioGroup lt_bp_car_upto1000_nd;
    RadioButton radioButton2;
    RadioButton radioButton3;


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
        checkfunction(longterm_pp_car_upto1000.this);

        setContentView(R.layout.longterm_pp_car_upto1000);
        getSupportActionBar().setTitle("Car Package Policy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RadioButton pa_no =  findViewById(R.id.lt_bp_car_upto1000_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lt_bp_car_upto1000_paod_value.setText("0");
                lt_bp_car_upto1000_paod_value.setEnabled(false);
            }
        });
        RadioButton pa_yes = findViewById(R.id.lt_bp_car_upto1000_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lt_bp_car_upto1000_paod_value.setText("900");
                lt_bp_car_upto1000_paod_value.setEnabled(true);
            }
        });

        RadioButton lpg_no = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpg_value_no);
        lpg_no.setOnClickListener(no_lpg_listener);
        RadioButton lpg_yes = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpg_value_yes);
        lpg_yes.setOnClickListener(yes_lpg_listener);

        RadioButton nd_no = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_nd_value_no);
        nd_no.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                EditText ed1=(EditText)findViewById(R.id.lt_bp_car_upto1000_nd_value);
                ed1.setEnabled(false);
                ed1.setText("0");



            }
        });
        RadioButton nd_yes = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_nd_value_yes);
        nd_yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1=(EditText)findViewById(R.id.lt_bp_car_upto1000_nd_value);
                long diffInDays=CalculateDifferenceInDays();
                double nd_value1=0.00;
                if (diffInDays < 365) {
                    nd_value1=1.32;
                }else if (diffInDays >= 365 && diffInDays < 730 ) {
                    nd_value1 = 1.32;
                }else if (diffInDays >= 730 && diffInDays < 1825 ) {
                    nd_value1 = 1.32;
                }else if (diffInDays >= 1825 && diffInDays < 3650 ) {
                    nd_value1 = 1.32;
                }else if (diffInDays >= 3650 ){
                    nd_value1 = 0;
                }
                float  nd_value1_int =(float) nd_value1;
                ed1.setText(String.valueOf(nd_value1_int));

                ed1.setEnabled(false);
                ed1.setVisibility(View.VISIBLE);
                TextView nd_sym= (TextView)findViewById(R.id.nd_sym);
                nd_sym.setVisibility(View.VISIBLE);

            }
        });
        RadioButton rb1 = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value_fixed);
        RadioButton rb2 = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value_inbuilt);
        rb1.setClickable(false);
        rb2.setClickable(false);
        rb2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1 = (EditText) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value);
                ed1.setVisibility(View.INVISIBLE);
                TextView lpg_sym= (TextView)findViewById(R.id.lpg_sym);
                lpg_sym.setVisibility(View.INVISIBLE);
            }});
        rb1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1 = (EditText) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value);
                ed1.setVisibility(View.VISIBLE);
                TextView lpg_sym= (TextView)findViewById(R.id.lpg_sym);
                lpg_sym.setVisibility(View.VISIBLE);
                ed1.setEnabled(true);

            }});
        //Date-start
        mDateDisplay = (TextView) findViewById(R.id.lt_bp_car_upto1000_date_value);
        mPickDate = (Button) findViewById(R.id.lt_bp_car_upto1000_date_btn);


        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();
        //Date-end


        //spinner-start
        Spinner spin = (Spinner) findViewById(R.id.lt_bp_car_upto1000_ncb_value);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,ncb);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        //spinner-end


        findViewById(R.id.lt_bp_car_upto1000_btn).setOnClickListener(listener_lt_bp_car_upto1000_btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        lt_bp_car_upto1000_btn.setOnClickListener(this);
        //stop-passthevalues

        ndd= (EditText) findViewById(R.id.lt_bp_car_upto1000_ndd_value);
        ndd.addTextChangedListener(textWatcher);

    };
    View.OnClickListener no_lpg_listener = new View.OnClickListener(){
        public void onClick(View v) {

            RadioButton rb1 = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value_fixed);
            RadioButton rb2 = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value_inbuilt);
            rb1.setEnabled(false);
            rb2.setEnabled(false);
            EditText ed1=(EditText)findViewById(R.id.lt_bp_car_upto1000_lpgtype_value);
            ed1.setEnabled(false);
            ed1.setVisibility(View.INVISIBLE);
        }
    };
    View.OnClickListener yes_lpg_listener = new View.OnClickListener(){
        public void onClick(View v) {

            RadioButton rb1 = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value_fixed);
            RadioButton rb2 = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value_inbuilt);
            rb1.setEnabled(true);
            rb2.setEnabled(true);
            rb2.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    EditText ed1 = (EditText) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value);
                    ed1.setVisibility(View.INVISIBLE);
                    TextView lpg_sym= (TextView)findViewById(R.id.lpg_sym);
                    lpg_sym.setVisibility(View.INVISIBLE);
                }});
            rb1.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    EditText ed1 = (EditText) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value);
                    ed1.setVisibility(View.VISIBLE);
                    TextView lpg_sym= (TextView)findViewById(R.id.lpg_sym);
                    lpg_sym.setVisibility(View.VISIBLE);
                    ed1.setEnabled(true);

                }});

        }
    };



    View.OnClickListener yes_nd_listener = new View.OnClickListener(){
        public void onClick(View v) {


            EditText ed1=(EditText)findViewById(R.id.lt_bp_car_upto1000_nd_value);
            long diffInDays=CalculateDifferenceInDays();
            double nd_value1=0.00;
            if (diffInDays < 365) {
                nd_value1=1.32;
            }else if (diffInDays >= 365 && diffInDays < 730 ) {
                nd_value1 = 1.32;
            }else if (diffInDays >= 730 && diffInDays < 1825 ) {
                nd_value1 = 1.32;
            }else if (diffInDays >= 1825 && diffInDays < 3650 ) {
                nd_value1 = 1.32;
            }else if (diffInDays >= 3650 ){
                nd_value1 = 0;
            }
            float  nd_value1_int =(float) nd_value1;
            ed1.setText(String.valueOf(nd_value1_int));
            ed1.setEnabled(false);
        }
    };



    View.OnClickListener listener_lt_bp_car_upto1000_btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(longterm_pp_car_upto1000.this, longterm_ppdisplay_car_upto1000.class);
            startActivity(intent);
        }
    };



    public long  CalculateDifferenceInDays(){

        int mYear_now,mMonth_now,mDay_now;
        // Create Calendar instance
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        mYear_now = calendar1.get(Calendar.YEAR);
        mMonth_now = calendar1.get(Calendar.MONTH);
        mDay_now = calendar1.get(Calendar.DAY_OF_MONTH);

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

    //start-passthevalues
    private void findAllViewsId() {
        lt_bp_car_upto1000_btn = (Button) findViewById(R.id.lt_bp_car_upto1000_btn);

        lt_bp_car_upto1000_idv_value = (EditText) findViewById(R.id.lt_bp_car_upto1000_idv_value);
        lt_bp_car_upto1000_date_value = (EditText) findViewById(R.id.lt_bp_car_upto1000_date_value);
        lt_bp_car_upto1000_cc_value = (EditText) findViewById(R.id.lt_bp_car_upto1000_cc_value);
        lt_bp_car_upto1000_nd_value = (EditText) findViewById(R.id.lt_bp_car_upto1000_nd_value);
        lt_bp_car_upto1000_ndd_value = (EditText) findViewById(R.id.lt_bp_car_upto1000_ndd_value);
        lt_bp_car_upto1000_uwd_value = (EditText) findViewById(R.id.lt_bp_car_upto1000_uwd_value);
        lt_bp_car_scpassengers_upto1000 = (EditText) findViewById(R.id.lt_bp_car_scpassengers_upto1000);
        lt_bp_car_upto1000_lpgtype_value = (EditText) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value);
        lt_bp_car_upto1000_paod_value = findViewById(R.id.lt_bp_car_upto1000_paod);

        final ScrollView scrollview = ((ScrollView) findViewById(R.id.pp_car_upto1000_sv));

        lt_bp_car_upto1000_uwd_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_NEXT)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            lt_bp_car_scpassengers_upto1000.requestFocus();
                        }
                    });
                return false;
            }
        });

        lt_bp_car_scpassengers_upto1000.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_DONE)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            lt_bp_car_upto1000_btn.requestFocus();
                        }
                    });
                return false;
            }
        });

        lt_bp_car_upto1000_zone = (RadioGroup) findViewById(R.id.lt_bp_car_upto1000_zone);
        lt_bp_car_upto1000_lpg = (RadioGroup) findViewById(R.id.lt_bp_car_upto1000_lpg);
        lt_bp_car_upto1000_lpgtype = (RadioGroup) findViewById(R.id.lt_bp_car_upto1000_lpgtype);
        lt_bp_car_upto1000_antitheft = (RadioGroup) findViewById(R.id.lt_bp_car_upto1000_antitheft);
        lt_bp_car_upto1000_nd = (RadioGroup) findViewById(R.id.lt_bp_car_upto1000_nd);
        lt_bp_car_patooccupants_upto1000 = (RadioGroup) findViewById(R.id.lt_bp_car_patooccupants_upto1000);


    }


    @Override
    public void onClick(View v) {
        //Check for any EditText being null
        String idv_value = lt_bp_car_upto1000_idv_value.getText().toString();
        String nd_value = lt_bp_car_upto1000_nd_value.getText().toString();
        String ndd_value = lt_bp_car_upto1000_ndd_value.getText().toString();
        String uwd_value =lt_bp_car_upto1000_uwd_value.getText().toString();
        String scpass_value =lt_bp_car_scpassengers_upto1000.getText().toString();
        String pp_lpgtype_value=lt_bp_car_upto1000_lpgtype_value.getText().toString();
        RadioButton rb2 = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value_fixed);
        RadioButton rb1 = (RadioButton) findViewById(R.id.lt_bp_car_upto1000_lpgtype_value_inbuilt);


        if(idv_value.equals("")|| uwd_value.equals("")||scpass_value.equals("")||ndd_value.equals("")){

            Snackbar bar = Snackbar.make(v, "Please enter all fields to Calculate!", Snackbar.LENGTH_LONG);
            TextView mainTextView = (TextView) (bar.getView()).findViewById(android.support.design.R.id.snackbar_text);
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
            TextView tv = (TextView) bar.getView().findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.colorSnackBar));
            View view = bar.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorSnackBarBg));
            bar.show();

        }
        else {
            if (lt_bp_car_upto1000_lpgtype_value.getVisibility() == View.VISIBLE && pp_lpgtype_value.equals("")) {
                Toast.makeText(getApplicationContext(),"Please enter all fields",Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), longterm_ppdisplay_car_upto1000.class);
                //Create a bundle object
                Bundle b = new Bundle();
                b.putString("lt_bp_car_upto1000_idv_value", lt_bp_car_upto1000_idv_value.getText().toString());
                b.putString("lt_bp_car_upto1000_date_value", lt_bp_car_upto1000_date_value.getText().toString());
                b.putString("lt_bp_car_upto1000_cc_value", lt_bp_car_upto1000_cc_value.getText().toString());
                b.putString("lt_bp_car_upto1000_nd_value", lt_bp_car_upto1000_nd_value.getText().toString());
                b.putString("lt_bp_car_upto1000_ndd_value", lt_bp_car_upto1000_ndd_value.getText().toString());
                b.putString("lt_bp_car_upto1000_uwd_value", lt_bp_car_upto1000_uwd_value.getText().toString());
                b.putString("lt_bp_car_scpassengers_upto1000", lt_bp_car_scpassengers_upto1000.getText().toString());
                b.putString("lt_bp_car_upto1000_paod_value", lt_bp_car_upto1000_paod_value.getText().toString());

                b.putString("lt_bp_car_scpassengers_lpgtype_value", lt_bp_car_upto1000_lpgtype_value.getText().toString());

                b.putString("lt_bp_car_spinner_value", selected);

                int id1 = lt_bp_car_upto1000_zone.getCheckedRadioButtonId();
                RadioButton radioButton1 = (RadioButton) findViewById(id1);
                b.putString("lt_bp_car_upto1000_zone", radioButton1.getText().toString());


                int id2 = lt_bp_car_upto1000_lpg.getCheckedRadioButtonId();
                radioButton2 = (RadioButton) findViewById(id2);
                b.putString("lt_bp_car_upto1000_lpg", radioButton2.getText().toString());


                int id3 = lt_bp_car_upto1000_lpgtype.getCheckedRadioButtonId();
                radioButton3 = (RadioButton) findViewById(id3);
                b.putString("lt_bp_car_upto1000_lpgtype", radioButton3.getText().toString());


                int id6 = lt_bp_car_upto1000_nd.getCheckedRadioButtonId();
                RadioButton radioButton6 = (RadioButton) findViewById(id6);
                b.putString("lt_bp_car_upto1000_nd", radioButton6.getText().toString());

                int id4 = lt_bp_car_upto1000_antitheft.getCheckedRadioButtonId();
                RadioButton radioButton4 = (RadioButton) findViewById(id4);
                b.putString("lt_bp_car_upto1000_antitheft", radioButton4.getText().toString());


                int id5 = lt_bp_car_patooccupants_upto1000.getCheckedRadioButtonId();
                RadioButton radioButton5 = (RadioButton) findViewById(id5);
                b.putString("lt_bp_car_patooccupants_upto1000", radioButton5.getText().toString());

                b.putInt("year", mYear);
                b.putInt("month", mMonth);
                b.putInt("day", mDay);
                //Add the bundle to the intent.
                intent.putExtras(b);

                //start the DisplayActivity
                startActivity(intent);
            }
        }

    }

    //stop-passthevalues


    //Date-start
    private void updateDisplay() {
        mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
        EditText ed1=(EditText)findViewById(R.id.lt_bp_car_upto1000_nd_value);
        long diffInDays=CalculateDifferenceInDays();
        double nd_value1=0.00;
        if (diffInDays < 365) {
            nd_value1=1.32;
        }else if (diffInDays >= 365 && diffInDays < 730 ) {
            nd_value1 = 1.32;
        }else if (diffInDays >= 730 && diffInDays < 1825 ) {
            nd_value1 = 1.32;
        }else if (diffInDays >= 1825 && diffInDays < 3650 ) {
            nd_value1 = 1.32;
        }else if (diffInDays >= 3650 ){
            nd_value1 = 0;
        }
        float  nd_value1_int =(float) nd_value1;
        ed1.setText(String.valueOf(nd_value1_int));
        ed1.setEnabled(false);
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
                if (num>=0 && num<=50)
                {
                    //save the number
                    num1=num;
                }
                else{

                    Toast.makeText(longterm_pp_car_upto1000.this,"NDD range should be of 1-50",Toast.LENGTH_SHORT).show();
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