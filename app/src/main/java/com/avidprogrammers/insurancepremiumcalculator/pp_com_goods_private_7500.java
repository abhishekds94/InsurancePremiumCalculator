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
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
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

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class pp_com_goods_private_7500 extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "pp_com_goods_private_7500";
    private AdView mAdView;

    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    long diffInDays;

    Button pp_com_goods_private_7500_btn;

    EditText pp_com_goods_private_7500_idv_value;
    EditText pp_com_goods_private_7500_gvw_value;
    EditText pp_com_goods_private_7500_date_value;
    EditText pp_com_goods_private_7500_paod_value;
    EditText pp_com_goods_private_7500_nd_value;
    EditText pp_com_goods_private_7500_uwd_value;
    EditText pp_com_goods_private_7500_nfpp;
    EditText pp_com_goods_private_7500_coolie;


    RadioGroup pp_com_goods_private_7500_zone;
    RadioGroup pp_com_goods_private_7500_lpg;
    RadioGroup pp_com_goods_private_7500_lpgtype;


    String selected;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(conn);
    }

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        checkingStatus=new CheckingStatus();
        conn=new ConnectivityReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(conn, intentFilter);
        checkfunction(pp_com_goods_private_7500.this);

        setContentView(R.layout.pp_com_goods_private_7500);
        getSupportActionBar().setTitle("Private Commercial Vehicle - Package Policy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        //Date-start
        mDateDisplay = (TextView) findViewById(R.id.pp_com_goods_private_7500_date_value);
        mPickDate = (Button) findViewById(R.id.pp_com_goods_private_7500_date_btn);


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
        Spinner spin = (Spinner) findViewById(R.id.pp_com_goods_private_7500_ncb_value);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,ncb);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        //spinner-end


        findViewById(R.id.pp_com_goods_private_7500_btn).setOnClickListener(listener_pp_com_goods_private_7500_btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();

        pp_com_goods_private_7500_btn.setOnClickListener(this);
        //stop-passthevalues


        RadioButton nd_no = (RadioButton) findViewById(R.id.pp_com_goods_private_7500_nd_value_no);
        nd_no.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                EditText ed1=(EditText)findViewById(R.id.pp_com_goods_private_7500_nd_value);
                ed1.setEnabled(false);
                ed1.setText("0");

                ed1.setVisibility(View.VISIBLE);
                TextView tv=(TextView) findViewById(R.id.percen);
                tv.setVisibility(View.VISIBLE);



            }
        });
        RadioButton nd_yes = (RadioButton) findViewById(R.id.pp_com_goods_private_7500_nd_value_yes);
        nd_yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1=(EditText)findViewById(R.id.pp_com_goods_private_7500_nd_value);
                long diffInDays=CalculateDifferenceInDays();
                double nd_value1=0.00;
                if (diffInDays < 365) {
                    nd_value1=15;
                }else if (diffInDays >= 365 ) {
                    if(diffInDays < 1825){
                        nd_value1 = 25;
                    }
                    else if (diffInDays >= 1825 ){
                        nd_value1 = 0;
                    }
                }

                int  nd_value1_int =(int) nd_value1;
                ed1.setText(String.valueOf(nd_value1_int));
                ed1.setEnabled(false);
                ed1.setVisibility(View.VISIBLE);
                TextView tv=(TextView) findViewById(R.id.percen);
                tv.setVisibility(View.VISIBLE);
                display_nd();

            }
        });

        RadioButton pa_no=(RadioButton) findViewById(R.id.pp_com_goods_private_7500_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.pp_com_goods_private_7500_paod_value);
                ed1.setText("0");
                ed1.setEnabled(false);
            }
        });
        RadioButton pa_yes=(RadioButton) findViewById(R.id.pp_com_goods_private_7500_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed1= (EditText) findViewById(R.id.pp_com_goods_private_7500_paod_value);
                ed1.setText("320");
                ed1.setEnabled(true);
            }
        });
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

    void display_nd(){
        EditText ed1=(EditText)findViewById(R.id.pp_com_goods_private_7500_nd_value);
        long diffInDays=CalculateDifferenceInDays();
        double nd_value1=0.00;
        if (diffInDays < 365) {
            nd_value1=15;
        }else if (diffInDays >= 365 ) {
            if(diffInDays < 1825){
                nd_value1 = 25;
            }
            else if (diffInDays >= 1825 ){
                nd_value1 = 0;
            }
        }
        int  nd_value1_int =(int) nd_value1;
        ed1.setText(String.valueOf(nd_value1_int));
        ed1.setEnabled(false);
    }

    View.OnClickListener listener_pp_com_goods_private_7500_btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pp_com_goods_private_7500.this, ppdisplay_com_goods_private_7500.class);
            startActivity(intent);
        }
    };


    //start-passthevalues
    private void findAllViewsId() {
        pp_com_goods_private_7500_btn = (Button) findViewById(R.id.pp_com_goods_private_7500_btn);

        pp_com_goods_private_7500_idv_value = (EditText) findViewById(R.id.pp_com_goods_private_7500_act);
        pp_com_goods_private_7500_gvw_value = (EditText) findViewById(R.id.pp_com_goods_private_7500_gvw_value);
        pp_com_goods_private_7500_date_value = (EditText) findViewById(R.id.pp_com_goods_private_7500_date_value);
        pp_com_goods_private_7500_nd_value = (EditText) findViewById(R.id.pp_com_goods_private_7500_nd_value);
        pp_com_goods_private_7500_uwd_value = (EditText) findViewById(R.id.pp_com_goods_private_7500_uwd_value);
        pp_com_goods_private_7500_coolie = (EditText) findViewById(R.id.pp_com_goods_private_7500_coolie);
        pp_com_goods_private_7500_nfpp = (EditText) findViewById(R.id.pp_com_goods_private_7500_nfpp);

        final ScrollView scrollview = ((ScrollView) findViewById(R.id.pp_com_goods_private_7500_sv));

        pp_com_goods_private_7500_uwd_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_NEXT)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            pp_com_goods_private_7500_coolie.requestFocus();
                        }
                    });
                return false;
            }
        });

        pp_com_goods_private_7500_nfpp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_DONE)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            pp_com_goods_private_7500_btn.requestFocus();
                        }
                    });
                return false;
            }
        });

        pp_com_goods_private_7500_zone = (RadioGroup) findViewById(R.id.pp_com_goods_private_7500_zone);
        pp_com_goods_private_7500_paod_value=(EditText) findViewById(R.id.pp_com_goods_private_7500_paod_value);


    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ppdisplay_com_goods_private_7500.class);
        //Create a bundle object
        Bundle b = new Bundle();

        //Inserts a String value into the mapping of this Bundle
        b.putString("pp_com_goods_private_7500_idv_value", pp_com_goods_private_7500_idv_value.getText().toString());
        b.putString("pp_com_goods_private_7500_gvw_value", pp_com_goods_private_7500_gvw_value.getText().toString());
        b.putString("pp_com_goods_private_7500_date_value", pp_com_goods_private_7500_date_value.getText().toString());
        b.putString("pp_com_goods_private_7500_nd_value", pp_com_goods_private_7500_nd_value.getText().toString());
        b.putString("pp_com_goods_private_7500_uwd_value", pp_com_goods_private_7500_uwd_value.getText().toString());
        b.putString("pp_com_goods_private_7500_coolie", pp_com_goods_private_7500_coolie.getText().toString());
        b.putString("pp_com_goods_private_7500_nfpp", pp_com_goods_private_7500_nfpp.getText().toString());
        b.putString("pp_com_goods_private_7500_paod_value",pp_com_goods_private_7500_paod_value.getText().toString());
        b.putString("pp_com_goods_private_7500_ncb_value", selected);


        int id1 = pp_com_goods_private_7500_zone.getCheckedRadioButtonId();
        RadioButton radioButton1 = (RadioButton) findViewById(id1);
        b.putString("pp_com_goods_private_7500_zone", radioButton1.getText().toString());

        b.putInt("year", mYear);
        b.putInt("month", mMonth);
        b.putInt("day", mDay);
        //Add the bundle to the intent.
        intent.putExtras(b);
        String idv_value = pp_com_goods_private_7500_idv_value.getText().toString();
        String gvw_value_val = pp_com_goods_private_7500_gvw_value.getText().toString();
        String uwd_value = pp_com_goods_private_7500_uwd_value.getText().toString();
        String coolie_value = pp_com_goods_private_7500_coolie.getText().toString();
        String nfpp_value = pp_com_goods_private_7500_nfpp.getText().toString();
        String date_value=pp_com_goods_private_7500_date_value.getText().toString();

        if(idv_value.equals("")||gvw_value_val.equals("")|| date_value.equals("")||uwd_value.equals("")||coolie_value.equals("")||nfpp_value.equals("")){

            Snackbar bar = Snackbar.make(v, "Please enter all fields to Calculate!", Snackbar.LENGTH_LONG);
            TextView mainTextView = (TextView) (bar.getView()).findViewById(R.id.snackbar_text);
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
            TextView tv = (TextView) bar.getView().findViewById(R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.colorSnackBar));
            View view = bar.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorSnackBarBg));
            bar.show();

        }
        else {

            int gvw_value = Integer.valueOf(pp_com_goods_private_7500_gvw_value.getText().toString());
            if (gvw_value > 7501 || gvw_value < 7499) {
                Toast.makeText(getApplicationContext(), "PLease enter correct GVW value", Toast.LENGTH_LONG).show();
            } else {
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
