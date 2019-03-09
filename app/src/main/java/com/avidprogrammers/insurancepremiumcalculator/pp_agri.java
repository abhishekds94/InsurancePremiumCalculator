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
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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

public class pp_agri extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "pp_agri";
    private AdView mAdView;

    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    long diffInDays;

    Button pp_agri_btn;

    EditText pp_agri_idv_tractor_value;
    EditText pp_agri_idv_trailer_value;
    EditText pp_agri_date_value;
    EditText pp_agri_cc_value;
    EditText pp_agri_nd_value;
    EditText pp_agri_uwd_value;
    EditText pp_agri_nfpp;
    EditText pp_agri_coolie;
    EditText pp_agri_gvw_value;
    EditText pp_agri_paod_value;

    RadioGroup pp_agri_zone;
    RadioGroup pp_agri_nd;
    RadioGroup pp_agri_trailer;


    String selected;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(conn);
    }

    static final int DATE_DIALOG_ID = 0;
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkingStatus=new CheckingStatus();
        conn=new ConnectivityReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(conn, intentFilter);
        checkfunction(pp_agri.this);

        setContentView(R.layout.pp_agri);
        getSupportActionBar().setTitle("Tractors & Trailers Package Policy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RadioButton pa_no =  findViewById(R.id.pp_agri_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_agri_paod_value.setText("0");
                pp_agri_paod_value.setEnabled(false);
            }
        });
        RadioButton pa_yes = findViewById(R.id.pp_agri_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_agri_paod_value.setText("320");
                pp_agri_paod_value.setEnabled(true);
            }
        });

        RadioButton trailer_no = (RadioButton) findViewById(R.id.pp_agri_trailer_no);
        trailer_no.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                EditText ed1=(EditText)findViewById(R.id.pp_agri_trailer_value_act);
                ed1.setEnabled(false);
                ed1.setText("0");
            }
        });

        RadioButton trailer_yes = (RadioButton) findViewById(R.id.pp_agri_trailer_yes);
        trailer_yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                EditText ed1=(EditText)findViewById(R.id.pp_agri_trailer_value_act);
                ed1.setEnabled(true);
                ed1.setText("");
                ed1.setVisibility(View.VISIBLE);
                TextView tv=(TextView) findViewById(R.id.rupee);
                tv.setVisibility(View.VISIBLE);



            }
        });

        RadioButton nd_no = (RadioButton) findViewById(R.id.pp_agri_nd_value_no);
        nd_no.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                EditText ed1=(EditText)findViewById(R.id.pp_agri_nd_value);
                ed1.setEnabled(false);
                ed1.setText("0");

            }
        });
        RadioButton nd_yes = (RadioButton) findViewById(R.id.pp_agri_nd_value_yes);
        nd_yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText ed1=(EditText)findViewById(R.id.pp_agri_nd_value);
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


        //Date-start
        mDateDisplay = (TextView) findViewById(R.id.pp_agri_date_value);
        mPickDate = (Button) findViewById(R.id.pp_agri_date_btn);


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
        Spinner spin = (Spinner) findViewById(R.id.pp_agri_ncb_value);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,ncb);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        //spinner-end


        findViewById(R.id.pp_agri_btn).setOnClickListener(listener_pp_agri_btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();
        updateDisplay();

        pp_agri_btn.setOnClickListener(this);
        //stop-passthevalues
    };


    View.OnClickListener listener_pp_agri_btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pp_agri.this, ppdisplay_agri.class);
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
        pp_agri_btn = (Button) findViewById(R.id.pp_agri_btn);

        pp_agri_idv_tractor_value = (EditText) findViewById(R.id.pp_agri_tractor_act);
        pp_agri_idv_trailer_value = (EditText) findViewById(R.id.pp_agri_trailer_value_act);
        pp_agri_date_value = (EditText) findViewById(R.id.pp_agri_date_value);
        pp_agri_nd_value = (EditText) findViewById(R.id.pp_agri_nd_value);
        pp_agri_uwd_value = (EditText) findViewById(R.id.pp_agri_uwd_value);
        pp_agri_coolie = (EditText) findViewById(R.id.pp_agri_coolie);
        pp_agri_paod_value = findViewById(R.id.pp_agri_paod);

        final ScrollView scrollview = ((ScrollView) findViewById(R.id.pp_agri_sv));
        pp_agri_coolie.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_DONE)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            pp_agri_btn.requestFocus();
                        }
                    });
                return false;
            }
        });

        pp_agri_zone = (RadioGroup) findViewById(R.id.pp_agri_zone);
        pp_agri_trailer = (RadioGroup) findViewById(R.id.pp_agri_trailer);
        pp_agri_nd=(RadioGroup) findViewById(R.id.pp_agri_nd);

    }


    @Override
    public void onClick(View v) {
        String idv_value = pp_agri_idv_tractor_value.getText().toString();
        String idv_trailer_value = pp_agri_idv_trailer_value.getText().toString();
        String nd_value = pp_agri_nd_value.getText().toString();
        String uwd_value =pp_agri_uwd_value.getText().toString();
        String coolie_value =pp_agri_coolie.getText().toString();

        RadioButton rb2 = (RadioButton) findViewById(R.id.pp_agri_trailer_yes);
        RadioButton rb1 = (RadioButton) findViewById(R.id.pp_agri_trailer_no);
        if(idv_value.equals("")|| uwd_value.equals("")||coolie_value.equals("")){
            Toast.makeText(getApplicationContext(),"Please enter all fields",Toast.LENGTH_SHORT).show();
        }
        else {
            if (pp_agri_idv_tractor_value.getVisibility() == View.VISIBLE && idv_trailer_value.equals("")) {
                Toast.makeText(getApplicationContext(),"Please enter all fields",Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), ppdisplay_agri.class);
                //Create a bundle object
                Bundle b = new Bundle();

                //Inserts a String value into the mapping of this Bundle
                b.putString("pp_agri_idv_tractor_value", pp_agri_idv_tractor_value.getText().toString());
                b.putString("pp_agri_trailer_value", pp_agri_idv_trailer_value.getText().toString());
                b.putString("pp_agri_date_value", pp_agri_date_value.getText().toString());
                b.putString("pp_agri_nd_value", pp_agri_nd_value.getText().toString());
                b.putString("pp_agri_uwd_value", pp_agri_uwd_value.getText().toString());
                b.putString("pp_agri_paod_value", pp_agri_paod_value.getText().toString());
                b.putString("pp_agri_coolie", pp_agri_coolie.getText().toString());

                b.putString("pp_agri_spinner_value", selected);

                int id1 = pp_agri_zone.getCheckedRadioButtonId();
                RadioButton radioButton1 = (RadioButton) findViewById(id1);
                b.putString("pp_agri_zone", radioButton1.getText().toString());

                int id2 = pp_agri_trailer.getCheckedRadioButtonId();
                RadioButton radioButton2 = (RadioButton) findViewById(id2);
                b.putString("pp_agri_trailer", radioButton2.getText().toString());
                int id3 = pp_agri_nd.getCheckedRadioButtonId();

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


    void display_nd(){
        EditText ed1=(EditText)findViewById(R.id.pp_agri_nd_value);
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
