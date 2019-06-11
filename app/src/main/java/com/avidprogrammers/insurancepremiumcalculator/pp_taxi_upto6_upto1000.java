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

import java.util.concurrent.TimeUnit;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class pp_taxi_upto6_upto1000 extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "pp_taxi_upto6_upto1000";
    private AdView mAdView;

    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;

    Button pp_taxi_upto6_upto1000_btn;


    EditText pp_taxi_upto6_upto1000_idv_value;
    EditText pp_taxi_upto6_upto1000_date_value;
    EditText pp_taxi_upto6_upto1000_cc_value;
    EditText pp_taxi_upto6_upto1000_lpg_type_value;
    EditText pp_taxi_upto6_upto1000_nd_value;
    EditText pp_taxi_upto6_upto1000_ndd_value;
    EditText pp_taxi_upto6_upto1000_uwd_value;
    EditText pp_taxi_upto6_scpassengers_upto1000;
    EditText pp_taxi_upto6_upto1000_paod_value;

    RadioGroup pp_taxi_upto6_upto1000_zone;
    RadioGroup pp_taxi_upto6_upto1000_lpg;
    RadioGroup pp_taxi_upto6_upto1000_lpgtype;
    RadioGroup pp_taxi_upto6_upto1000_antitheft;
    RadioGroup pp_taxi_upto6_patooccupants_upto1000;

    //Radiobutton initialisation
    RadioButton pp_radiobutton_inbuilt,pp_radiobutton_fixed;
    RadioButton pp_radiobutton_yes_nd,pp_radiobutton_no_nd;
    RadioButton pp_radiobutton_lp_yes,pp_radiobutton_lp_no;
    RadioButton pp_taxi_upto6_upto1000_antitheft_value_yes,pp_taxi_upto6_upto1000_antitheft_value_no;
    double pp_zone_value[][]=new double[][]{{3.284,3.366,3.448},{3.191,3.271,3.351}};

    int cost=0;
    static final int DATE_DIALOG_ID = 0;
    private RadioButton pp_radiobutton_zone_a,pp_radiobutton_zone_b;

    @RequiresApi(api = Build.VERSION_CODES.N)

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
        checkfunction(pp_taxi_upto6_upto1000.this);

        setContentView(R.layout.pp_taxi_upto6_upto1000);
        getSupportActionBar().setTitle("Taxi Package Policy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //Date-start
        mDateDisplay = (TextView) findViewById(R.id.pp_taxi_upto6_upto1000_date_value);
        mPickDate = (Button) findViewById(R.id.txtbtn);


        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });


        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();
        //Date-end


        //spinner-start
        Spinner spin = (Spinner) findViewById(R.id.pp_taxi_upto6_upto1000_ncb_value);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,ncb);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        //spinner-end


        findViewById(R.id.pp_taxi_upto6_upto1000_btn).setOnClickListener(listener_pp_taxi_upto6_upto1000_btn);


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId();
        nd();
        pp_taxi_upto6_upto1000_btn.setOnClickListener(this);
        //stop-passthevalues
        dateset();
        onclicklistening();

        ndd= (EditText) findViewById(R.id.pp_taxi_upto6_upto1000_ndd_value);
        ndd.addTextChangedListener(textWatcher);

    }


    View.OnClickListener listener_pp_taxi_upto6_upto1000_btn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(pp_taxi_upto6_upto1000.this, ppdisplay_taxi_upto6_upto1000.class);
            startActivity(intent);
        }
    };


    //start-passthevalues
    private void findAllViewsId() {
        pp_taxi_upto6_upto1000_btn = (Button) findViewById(R.id.pp_taxi_upto6_upto1000_btn);

        pp_taxi_upto6_upto1000_idv_value = (EditText) findViewById(R.id.pp_taxi_upto6_upto1000_idv_value);
        pp_taxi_upto6_upto1000_date_value = (EditText) findViewById(R.id.pp_taxi_upto6_upto1000_date_value);
        pp_taxi_upto6_upto1000_cc_value = (EditText) findViewById(R.id.pp_taxi_upto6_upto1000_cc_value);
        pp_taxi_upto6_upto1000_lpg_type_value=(EditText)findViewById(R.id.pp_taxi_upto6_upto1000_lpgtype_value);
        pp_taxi_upto6_upto1000_nd_value = (EditText) findViewById(R.id.pp_taxi_upto6_upto1000_nd_value);
        pp_taxi_upto6_upto1000_ndd_value = (EditText) findViewById(R.id.pp_taxi_upto6_upto1000_ndd_value);
        pp_taxi_upto6_upto1000_uwd_value = (EditText) findViewById(R.id.pp_taxi_upto6_upto1000_uwd_value);
        pp_taxi_upto6_scpassengers_upto1000 = (EditText) findViewById(R.id.pp_taxi_upto6_scpassengers_upto1000);
        pp_taxi_upto6_upto1000_paod_value  = findViewById(R.id.pp_taxi_upto6_upto1000_paod);

        final ScrollView scrollview = ((ScrollView) findViewById(R.id.pp_taxi_upto6_upto1000_sv));

        pp_taxi_upto6_upto1000_uwd_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_NEXT)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            pp_taxi_upto6_scpassengers_upto1000.requestFocus();
                        }
                    });
                return false;
            }
        });

        pp_taxi_upto6_scpassengers_upto1000.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_DONE)
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.scrollTo(0, scrollview.getBottom());
                            pp_taxi_upto6_upto1000_btn.requestFocus();
                        }
                    });
                return false;
            }
        });

        pp_taxi_upto6_upto1000_zone = (RadioGroup) findViewById(R.id.pp_taxi_upto6_upto1000_zone);
        pp_taxi_upto6_upto1000_lpg = (RadioGroup) findViewById(R.id.pp_taxi_upto6_upto1000_lpg);
        pp_taxi_upto6_upto1000_lpgtype = (RadioGroup) findViewById(R.id.pp_taxi_upto6_upto1000_lpgtype);
        pp_taxi_upto6_upto1000_antitheft = (RadioGroup) findViewById(R.id.pp_taxi_upto6_upto1000_antitheft);

        //radiobutton initialisation
        pp_radiobutton_inbuilt=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_lpgtype_value_inbuilt);
        pp_radiobutton_fixed=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_lpgtype_value_fixed);
        pp_radiobutton_yes_nd=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_nd_value_yes);
        pp_radiobutton_no_nd=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_nd_value_no);
        pp_radiobutton_lp_yes=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_lpg_value_yes);
        pp_radiobutton_lp_no=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_lpg_value_no);
        pp_radiobutton_zone_a=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_idv_value_a);
        pp_radiobutton_zone_b=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_idv_value_b);
        pp_taxi_upto6_upto1000_antitheft_value_yes=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_antitheft_value_yes);
        pp_taxi_upto6_upto1000_antitheft_value_no=(RadioButton)findViewById(R.id.pp_taxi_upto6_upto1000_antitheft_value_no);
        pp_radiobutton_fixed.setClickable(false);
        pp_radiobutton_inbuilt.setClickable(false);
    }


    private void onclicklistening(){
        //onclick listener for all buttons
        pp_radiobutton_zone_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton z = (RadioButton) v;
                zoneCost = zone_checking(z.getText().toString(), score_time_update(mDay,mMonth+1,mYear));
            }
        });

        pp_radiobutton_zone_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton z = (RadioButton) v;
                zoneCost = zone_checking(z.getText().toString(), score_time_update(mDay,mMonth+1,mYear));
            }
        });


        pp_radiobutton_lp_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp_radiobutton_fixed.setEnabled(true);
                pp_radiobutton_inbuilt.setEnabled(true);
                pp_radiobutton_fixed.setAlpha((float) 1);
                pp_radiobutton_inbuilt.setAlpha((float)1);
                pp_radiobutton_fixed.setClickable(true);
                pp_radiobutton_inbuilt.setClickable(true);
                if(pp_radiobutton_inbuilt.isChecked()){
                    pp_taxi_upto6_upto1000_lpg_type_value.setVisibility(View.INVISIBLE);
                }else if(pp_radiobutton_fixed.isChecked()){
                    pp_taxi_upto6_upto1000_lpg_type_value.setVisibility(View.VISIBLE);
                }
            }
        });
        pp_radiobutton_lp_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp_radiobutton_fixed.setClickable(false);
                pp_radiobutton_inbuilt.setClickable(false);
                pp_radiobutton_fixed.setAlpha((float)0.5);
                pp_radiobutton_inbuilt.setAlpha((float)0.5);
                pp_taxi_upto6_upto1000_lpg_type_value.setVisibility(View.INVISIBLE);
            }
        });
        pp_radiobutton_inbuilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp_taxi_upto6_upto1000_lpg_type_value.setVisibility(View.INVISIBLE);
            }
        });
        pp_radiobutton_fixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp_taxi_upto6_upto1000_lpg_type_value.setVisibility(View.VISIBLE);
            }
        });

        RadioButton pa_no =  findViewById(R.id.pp_taxi_upto6_upto1000_paod_value_no);
        pa_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_taxi_upto6_upto1000_paod_value.setText("0");
                pp_taxi_upto6_upto1000_paod_value.setEnabled(false);
            }
        });
        RadioButton pa_yes = findViewById(R.id.pp_taxi_upto6_upto1000_paod_value_yes);
        pa_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp_taxi_upto6_upto1000_paod_value.setText("320");
                pp_taxi_upto6_upto1000_paod_value.setEnabled(true);
            }
        });

        pp_radiobutton_yes_nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nd();
            }
        });

        pp_radiobutton_no_nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nd();
            }
        });


    }
    public void dateset(){
        if(pp_radiobutton_lp_yes.isChecked()){
            if(pp_radiobutton_inbuilt.isChecked()){
                pp_taxi_upto6_upto1000_lpg_type_value.setVisibility(View.INVISIBLE);
            }
        }
        nd();
    }
    public void nd(){
        long x=score_time_update(mDay, mMonth+1, mYear);
        if(pp_radiobutton_yes_nd.isChecked()){
            if(x<1) {
                pp_taxi_upto6_upto1000_nd_value.setText("15", TextView.BufferType.EDITABLE);
            }else if(x>=1&&x<5){
                pp_taxi_upto6_upto1000_nd_value.setText("25", TextView.BufferType.EDITABLE);
            }
        }else{
            pp_taxi_upto6_upto1000_nd_value.setText("0", TextView.BufferType.EDITABLE);
        }
    }
    public double zone_checking(String x, long l){
        if (l < 5) {
            if (x.equals("A")) {
                return 3.284;
            } else if (x.equals("B")) {
                return 3.191;
            }
        } else if (l >= 5 && l < 7) {
            if (x.equals("A")) {
                return 3.366;
            } else if (x.equals("B")) {
                return 3.271;
            }
        } else if (l >= 7) {
            if (x.equals("A")) {
                return 3.448;
            } else if (x.equals("B")) {
                return 3.351;
            }
        }
        return 0;
    }

    double zoneCost=0;
    double Totalcost=0;

    public long score_time_update(int x, int y, int z){
        //Toast.makeText(pp_taxi_upto6_upto1000.this, ""+String.valueOf(z)+"-"+String.valueOf(x)+"-"+String.valueOf(y), Toast.LENGTH_SHORT).show();
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


    public void calculation_begins(){
        double Totalcost=0;
        if(!pp_taxi_upto6_upto1000_idv_value.getText().toString().equals("")) {
            Totalcost=Integer.parseInt(pp_taxi_upto6_upto1000_idv_value.getText().toString());
            String s;
            if(pp_radiobutton_zone_a.isChecked()){
                s="A";
            }else s="B";
            zoneCost = zone_checking(s, score_time_update(mDay, mMonth + 1, mYear));
            Totalcost*=(zoneCost/100);
            if (pp_radiobutton_lp_yes.isChecked()) {
                lpg_kit = 60;
                radiobuttonsel(Totalcost);
            }else if(pp_radiobutton_lp_no.isChecked()){
                lpg_kit=0;
                nd_radiobutton(Totalcost);
            }
        }else{
            pp_taxi_upto6_upto1000_idv_value.setError("Enter the value for IDV !");
        }
    }

    public void radiobuttonsel(double z){
        if (pp_radiobutton_inbuilt.isChecked()) {
            nd_radiobutton(z);
        } else if (pp_radiobutton_fixed.isChecked()) {
            if(pp_taxi_upto6_upto1000_lpg_type_value.getText().toString().isEmpty()){
                pp_taxi_upto6_upto1000_lpg_type_value.setError("Fill the details !");
            }else {
                int x = Integer.parseInt(pp_taxi_upto6_upto1000_lpg_type_value.getText().toString());
                z = z+ (x * 4 / 100);
                nd_radiobutton(z);
            }
        }
    }

    public void nd_radiobutton(double l){
        long x=score_time_update(mDay, mMonth + 1, mYear);
        Totalcost=l;
        if(pp_radiobutton_yes_nd.isChecked()){
            if(x<=1) {
                Totalcost+= ((Totalcost*(0.15)));
            }else if(x>1&&x<5){
                Totalcost+= ((Totalcost*(0.25)));
            }else if(x>=5){
                Totalcost+=0;
            }
        }else{
            Totalcost+=0;
        }

        anti_radiobutton(Totalcost);
    }

    public void anti_radiobutton(double matter) {
        Totalcost=matter;
        if (pp_taxi_upto6_upto1000_antitheft_value_yes.isChecked()) {
            if (Totalcost* (0.025) > 500) {
                Totalcost -= 500;
            } else {
                Totalcost -= Totalcost * (0.025);
            }
        }
        UWdiscount(Totalcost);
    }

    public void UWdiscount(double s){
        Totalcost=s;
        if(pp_taxi_upto6_upto1000_uwd_value.getText().toString().equals("")){
            pp_taxi_upto6_upto1000_uwd_value.setError("Fill U/W Discount ! ");
        }else {
            Totalcost -= (Integer.parseInt(pp_taxi_upto6_upto1000_uwd_value.getText().toString()) * Totalcost / 100);
            ncb(Totalcost);
        }
    }
    static double cost_total;
    public void ncb(double cost){
        cost-=cost*(Integer.parseInt(ncb[pos]))/100;
        cost_total=cost;
    }

    int lpg_kit=0;
    @Override
    public void onClick(View v) {
        if (pp_taxi_upto6_upto1000_idv_value.getText().toString().isEmpty() ||
                pp_taxi_upto6_upto1000_uwd_value.getText().toString().isEmpty() ||
                pp_taxi_upto6_scpassengers_upto1000.getText().toString().isEmpty()||
                (pp_radiobutton_lp_yes.isChecked()&&pp_radiobutton_fixed.isChecked()&&pp_taxi_upto6_upto1000_lpg_type_value.getText().toString().isEmpty())
                ) {
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
        }else {
            calculation_begins();
            Intent intent = new Intent(getApplicationContext(), ppdisplay_taxi_upto6_upto1000.class);
            //Create a bundle object
            Bundle b = new Bundle();
            //Inserts a String value into the mapping of this Bundle
            b.putString("pp_taxi_upto6_upto1000_idv_value", pp_taxi_upto6_upto1000_idv_value.getText().toString());
            b.putString("pp_taxi_upto6_upto1000_date_value", pp_taxi_upto6_upto1000_date_value.getText().toString());
            b.putString("pp_taxi_upto6_upto1000_cc_value", pp_taxi_upto6_upto1000_cc_value.getText().toString());
            b.putString("pp_taxi_upto6_upto1000_nd_value", pp_taxi_upto6_upto1000_nd_value.getText().toString());
            b.putString("pp_taxi_upto6_upto1000_ndd_value", pp_taxi_upto6_upto1000_ndd_value.getText().toString());
            b.putString("pp_taxi_upto6_upto1000_uwd_value", pp_taxi_upto6_upto1000_uwd_value.getText().toString());
            b.putString("pp_taxi_upto6_upto1000_paod_value",pp_taxi_upto6_upto1000_paod_value.getText().toString());
            b.putString("pp_taxi_upto6_scpassengers_upto1000", String.valueOf(Integer.valueOf(pp_taxi_upto6_scpassengers_upto1000.getText().toString()) * 1110));
            b.putString("pp_taxi_upto6_upto1000_od_value", String.valueOf(cost_total));
            b.putString("pp_taxi_upto6_upto1000_ncb_value", String.valueOf(ncb[pos]));
            b.putString("pp_taxi_upto6_upto1000_lpgkit_value", String.valueOf(lpg_kit));
            int id1 = pp_taxi_upto6_upto1000_zone.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(id1);
            b.putString("pp_taxi_upto6_upto1000_zone", radioButton1.getText().toString());

            int id2 = pp_taxi_upto6_upto1000_lpg.getCheckedRadioButtonId();
            RadioButton radioButton2 = (RadioButton) findViewById(id2);
            b.putString("pp_taxi_upto6_upto1000_lpg", radioButton2.getText().toString());

            // Checking the lpg type based on yes and no properties
            // to enable and disable the options of lpg type

            int id3 = pp_taxi_upto6_upto1000_lpgtype.getCheckedRadioButtonId();
            RadioButton radioButton3 = (RadioButton) findViewById(id3);
            if(pp_radiobutton_lp_no.isChecked()){
                b.putString("pp_taxi_upto6_upto1000_lpgtype","-");
            }else{
                b.putString("pp_taxi_upto6_upto1000_lpgtype", radioButton3.getText().toString());
            }

            int id4 = pp_taxi_upto6_upto1000_antitheft.getCheckedRadioButtonId();
            RadioButton radioButton4 = (RadioButton) findViewById(id4);
            b.putString("pp_taxi_upto6_upto1000_antitheft", radioButton4.getText().toString());

            //Add the bundle to the intent.
            intent.putExtras(b);
            //start the DisplayActivity
            startActivity(intent);
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
                    nd();
                    //score_time_update(mYear);
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
    static int pos=0;
    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //Toast.makeText(getApplicationContext(), ncb[position], Toast.LENGTH_LONG).show();
        pos=position;
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
                if (num>=0 && num<=65)
                {
                    //save the number
                    num1=num;
                }
                else{
                    Toast.makeText(pp_taxi_upto6_upto1000.this,"NDD range should be of 1-65",Toast.LENGTH_SHORT).show();
                    ndd.setText("");
                    num1=-1;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //this function checks for date and provides the zone percentage

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