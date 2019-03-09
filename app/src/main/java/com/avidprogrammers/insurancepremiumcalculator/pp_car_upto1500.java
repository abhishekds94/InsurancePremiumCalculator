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

public class pp_car_upto1500 extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "pp_car_upto1500";
    private AdView mAdView;
    String selected;
    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    long diffInDays;

    Button pp_car_upto1500_btn;

    EditText pp_car_upto1500_idv_value;
    EditText pp_car_upto1500_date_value;
    EditText pp_car_upto1500_cc_value;
    EditText pp_car_upto1500_nd_value;
    EditText pp_car_upto1500_ndd_value;
    EditText pp_car_upto1500_uwd_value;
    EditText pp_car_scpassengers_upto1500;
    EditText pp_car_upto1500_lpgtype_value;
    EditText pp_car_upto1500_paod_value;

    RadioGroup pp_car_upto1500_zone;
    RadioGroup pp_car_upto1500_lpg;
    RadioGroup pp_car_upto1500_lpgtype;
    RadioGroup pp_car_upto1500_antitheft;
    RadioGroup pp_car_patooccupants_upto1500;
    RadioGroup pp_car_upto1500_nd;
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
        checkfunction(pp_car_upto1500.this);

        setContentView(R.layout.pp_car_upto1500);
        getSupportActionBar().setTitle("Car Package Policy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RadioButton pa_no =  findViewById(R.id.pp_car_upto1500_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_car_upto1500_paod_value.setText("0");
                pp_car_upto1500_paod_value.setEnabled(false);
            }
        });
        RadioButton pa_yes = findViewById(R.id.pp_car_upto1500_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_car_upto1500_paod_value.setText(R.string.paod_val_s);
                pp_car_upto1500_paod_value.setEnabled(true);
            }
        });

        RadioButton lpg_no = (RadioButton) findViewById(R.id.pp_car_upto1500_lpg_value_no);
        lpg_no.setOnClickListener(no_lpg_listener);
        RadioButton lpg_yes = (RadioButton) findViewById(R.id.pp_car_upto1500_lpg_value_yes);
        lpg_yes.setOnClickListener(yes_lpg_listener);

        RadioButton nd_no = (RadioButton) findViewById(R.id.pp_car_upto1500_nd_value_no);
        nd_no.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                EditText ed1=(EditText)findViewById(R.id.pp_car_upto1500_nd_value);
                ed1.setEnabled(false);
                ed1.setText("0");



            }
        });
        RadioButton nd_yes = (RadioButton) findViewById(R.id.pp_car_upto1500_nd_value_yes);
        nd_yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1=(EditText)findViewById(R.id.pp_car_upto1500_nd_value);
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
                TextView nd_sym= (TextView)findViewById(R.id.nd_sym);
                nd_sym.setVisibility(View.VISIBLE);

            }
        });
        RadioButton rb1 = (RadioButton) findViewById(R.id.pp_car_upto1500_lpgtype_value_fixed);
        RadioButton rb2 = (RadioButton) findViewById(R.id.pp_car_upto1500_lpgtype_value_inbuilt);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        rb2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1 = (EditText) findViewById(R.id.pp_car_upto1500_lpgtype_value);
                ed1.setVisibility(View.INVISIBLE);
                TextView lpg_sym= (TextView)findViewById(R.id.lpg_sym);
                lpg_sym.setVisibility(View.INVISIBLE);
            }});
        rb1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1 = (EditText) findViewById(R.id.pp_car_upto1500_lpgtype_value);
                ed1.setVisibility(View.VISIBLE);
                TextView lpg_sym= (TextView)findViewById(R.id.lpg_sym);
                lpg_sym.setVisibility(View.VISIBLE);
                ed1.setEnabled(true);

            }});
        //Date-start
        mDateDisplay = (TextView) findViewById(R.id.pp_car_upto1500_date_value);
        mPickDate = (Button) findViewById(R.id.pp_car_upto1500_date_btn);


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
        Spinner spin = (Spinner) findViewById(R.id.pp_car_upto1500_ncb_value);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,ncb);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        //spinner-end


        findViewById(R.id.pp_car_upto1500_btn).setOnClickListener(listener_pp_car_upto1500_btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        pp_car_upto1500_btn.setOnClickListener(this);
        //stop-passthevalues

        ndd= (EditText) findViewById(R.id.pp_car_upto1500_ndd_value);
        ndd.addTextChangedListener(textWatcher);

    };
    View.OnClickListener no_lpg_listener = new View.OnClickListener(){
        public void onClick(View v) {

            RadioButton rb1 = (RadioButton) findViewById(R.id.pp_car_upto1500_lpgtype_value_fixed);
            RadioButton rb2 = (RadioButton) findViewById(R.id.pp_car_upto1500_lpgtype_value_inbuilt);
            rb1.setClickable(false);
            rb2.setClickable(false);
            rb1.setAlpha((float)0.5);
            rb2.setAlpha((float)0.5);
            EditText ed1=(EditText)findViewById(R.id.pp_car_upto1500_lpgtype_value);
            ed1.setEnabled(false);
            ed1.setVisibility(View.INVISIBLE);
        }
    };
    View.OnClickListener yes_lpg_listener = new View.OnClickListener(){
        public void onClick(View v) {

            RadioButton rb1 = (RadioButton) findViewById(R.id.pp_car_upto1500_lpgtype_value_fixed);
            RadioButton rb2 = (RadioButton) findViewById(R.id.pp_car_upto1500_lpgtype_value_inbuilt);
            rb1.setEnabled(true);
            rb2.setEnabled(true);
            rb1.setClickable(true);
            rb2.setClickable(true);
            rb1.setAlpha((float)1);
            rb2.setAlpha((float)1);
            rb2.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    EditText ed1 = (EditText) findViewById(R.id.pp_car_upto1500_lpgtype_value);
                    ed1.setVisibility(View.INVISIBLE);
                    TextView lpg_sym= (TextView)findViewById(R.id.lpg_sym);
                    lpg_sym.setVisibility(View.INVISIBLE);
                }});
            rb1.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    EditText ed1 = (EditText) findViewById(R.id.pp_car_upto1500_lpgtype_value);
                    ed1.setVisibility(View.VISIBLE);
                    TextView lpg_sym= (TextView)findViewById(R.id.lpg_sym);
                    lpg_sym.setVisibility(View.VISIBLE);
                    ed1.setEnabled(true);

                }});

        }
    };



    View.OnClickListener yes_nd_listener = new View.OnClickListener(){
        public void onClick(View v) {


            EditText ed1=(EditText)findViewById(R.id.pp_car_upto1500_nd_value);
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
        }
    };



    View.OnClickListener listener_pp_car_upto1500_btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pp_car_upto1500.this, ppdisplay_car_upto1500.class);
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
        pp_car_upto1500_btn = (Button) findViewById(R.id.pp_car_upto1500_btn);

        pp_car_upto1500_idv_value = (EditText) findViewById(R.id.pp_car_upto1500_idv_value);
        pp_car_upto1500_date_value = (EditText) findViewById(R.id.pp_car_upto1500_date_value);
        pp_car_upto1500_cc_value = (EditText) findViewById(R.id.pp_car_upto1500_cc_value);
        pp_car_upto1500_nd_value = (EditText) findViewById(R.id.pp_car_upto1500_nd_value);
        pp_car_upto1500_ndd_value = (EditText) findViewById(R.id.pp_car_upto1500_ndd_value);
        pp_car_upto1500_uwd_value = (EditText) findViewById(R.id.pp_car_upto1500_uwd_value);
        pp_car_scpassengers_upto1500 = (EditText) findViewById(R.id.pp_car_scpassengers_upto1500);
        pp_car_upto1500_lpgtype_value = (EditText) findViewById(R.id.pp_car_upto1500_lpgtype_value);
        pp_car_upto1500_paod_value = findViewById(R.id.pp_car_upto1500_paod);

        final ScrollView scrollview = ((ScrollView) findViewById(R.id.pp_car_upto1500_sv));

        pp_car_upto1500_uwd_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_NEXT)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            pp_car_scpassengers_upto1500.requestFocus();
                        }
                    });
                return false;
            }
        });

        pp_car_scpassengers_upto1500.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_DONE)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            pp_car_upto1500_btn.requestFocus();
                        }
                    });
                return false;
            }
        });

        pp_car_upto1500_zone = (RadioGroup) findViewById(R.id.pp_car_upto1500_zone);
        pp_car_upto1500_lpg = (RadioGroup) findViewById(R.id.pp_car_upto1500_lpg);
        pp_car_upto1500_lpgtype = (RadioGroup) findViewById(R.id.pp_car_upto1500_lpgtype);
        pp_car_upto1500_antitheft = (RadioGroup) findViewById(R.id.pp_car_upto1500_antitheft);
        pp_car_upto1500_nd = (RadioGroup) findViewById(R.id.pp_car_upto1500_nd);
        pp_car_patooccupants_upto1500 = (RadioGroup) findViewById(R.id.pp_car_patooccupants_upto1500);


    }


    @Override
    public void onClick(View v) {
        //Check for any EditText being null
        String idv_value = pp_car_upto1500_idv_value.getText().toString();
        String nd_value = pp_car_upto1500_nd_value.getText().toString();
        String ndd_value = pp_car_upto1500_ndd_value.getText().toString();
        String uwd_value =pp_car_upto1500_uwd_value.getText().toString();
        String scpass_value =pp_car_scpassengers_upto1500.getText().toString();
        String pp_lpgtype_value=pp_car_upto1500_lpgtype_value.getText().toString();
        RadioButton rb2 = (RadioButton) findViewById(R.id.pp_car_upto1500_lpgtype_value_fixed);
        RadioButton rb1 = (RadioButton) findViewById(R.id.pp_car_upto1500_lpgtype_value_inbuilt);

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
            if (pp_car_upto1500_lpgtype_value.getVisibility() == View.VISIBLE && pp_lpgtype_value.equals("")) {

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

            } else {
                Intent intent = new Intent(getApplicationContext(), ppdisplay_car_upto1500.class);
                //Create a bundle object
                Bundle b = new Bundle();
                b.putString("pp_car_upto1500_idv_value", pp_car_upto1500_idv_value.getText().toString());
                b.putString("pp_car_upto1500_date_value", pp_car_upto1500_date_value.getText().toString());
                b.putString("pp_car_upto1500_cc_value", pp_car_upto1500_cc_value.getText().toString());
                b.putString("pp_car_upto1500_nd_value", pp_car_upto1500_nd_value.getText().toString());
                b.putString("pp_car_upto1500_ndd_value", pp_car_upto1500_ndd_value.getText().toString());
                b.putString("pp_car_upto1500_uwd_value", pp_car_upto1500_uwd_value.getText().toString());
                b.putString("pp_car_scpassengers_upto1500", pp_car_scpassengers_upto1500.getText().toString());
                b.putString("pp_car_upto1500_paod_value", pp_car_upto1500_paod_value.getText().toString());

                b.putString("pp_car_scpassengers_lpgtype_value", pp_car_upto1500_lpgtype_value.getText().toString());

                b.putString("pp_car_spinner_value", selected);

                int id1 = pp_car_upto1500_zone.getCheckedRadioButtonId();
                RadioButton radioButton1 = (RadioButton) findViewById(id1);
                b.putString("pp_car_upto1500_zone", radioButton1.getText().toString());


                int id2 = pp_car_upto1500_lpg.getCheckedRadioButtonId();
                radioButton2 = (RadioButton) findViewById(id2);
                b.putString("pp_car_upto1500_lpg", radioButton2.getText().toString());


                int id3 = pp_car_upto1500_lpgtype.getCheckedRadioButtonId();
                radioButton3 = (RadioButton) findViewById(id3);
                b.putString("pp_car_upto1500_lpgtype", radioButton3.getText().toString());


                int id6 = pp_car_upto1500_nd.getCheckedRadioButtonId();
                RadioButton radioButton6 = (RadioButton) findViewById(id6);
                b.putString("pp_car_upto1500_nd", radioButton6.getText().toString());

                int id4 = pp_car_upto1500_antitheft.getCheckedRadioButtonId();
                RadioButton radioButton4 = (RadioButton) findViewById(id4);
                b.putString("pp_car_upto1500_antitheft", radioButton4.getText().toString());


                int id5 = pp_car_patooccupants_upto1500.getCheckedRadioButtonId();
                RadioButton radioButton5 = (RadioButton) findViewById(id5);
                b.putString("pp_car_patooccupants_upto1500", radioButton5.getText().toString());

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
        EditText ed1=(EditText)findViewById(R.id.pp_car_upto1500_nd_value);
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
                    Toast.makeText(pp_car_upto1500.this,"NDD range should be of 1-35",Toast.LENGTH_SHORT).show();
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