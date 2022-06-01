package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avidprogrammers.utils.PermissionsActivity;
import com.avidprogrammers.utils.PermissionsChecker;
import com.google.android.gms.ads.AdView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import at.markushi.ui.CircleButton;

import static com.avidprogrammers.utils.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.avidprogrammers.utils.PermissionsChecker.REQUIRED_PERMISSION;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class longterm_ppdisplay_car_upto1000 extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    long diffInDays;
    double dop_value;
    int lt_bp_lgkit_assumevalue,lt_bp_scpass_value,lt_bp_pa_pass_value;
    float rounded_dop_value;
    int pp_tax_value=18;
    float value_nd;
    float rounded_lgptype_value;
    float rounded_value_nd,rounded_value_antitheft,rounded_uw_value,rounded_ncb_value;
    double value_lgptype1,value_uw1,value_spin1,nild_value,value_ndd,rounded_ndd_value;
    double value_lgptype2;
    double lt_bp_total_premium;

    TextView lt_bp_car_upto1000_IDV_value;
    TextView lt_bp_car_upto1000_date_value;
    TextView lt_bp_car_upto1000_zone;
    TextView lt_bp_car_upto1000_cc_value;
    TextView lt_bp_car_upto1000_nd_value;
    TextView lt_bp_car_upto1000_ndd_value;
    TextView lt_bp_car_upto1000_uwd_value;
    TextView lt_bp_car_upto1000_ncb_value;
    TextView lt_bp_car_upto1000_lpg_value;
    TextView lt_bp_car_upto1000_lpgtype_value;
    TextView lt_bp_car_upto1000_antitheft;
    TextView lt_bp_car_upto1000_paod_value;

    TextView lt_bp_car_upto1000_od_value;
    TextView lt_bp_car_upto1000_value_b;
    TextView lt_ppdisplay_car_upto1000_ab_value;
    TextView lt_ppdisplay_car_upto1000_lpgkit_value;
    TextView lt_ppdisplay_car_upto1000_pa_pass_value;
    TextView lt_ppdisplay_car_upto1000_total_value;

    CircleButton share_btn;
    File file;

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
        checkfunction(longterm_ppdisplay_car_upto1000.this);

        setContentView(R.layout.longterm_ppdisplay_car_upto1000);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        Bundle b = getIntent().getExtras();
        lt_bp_car_upto1000_IDV_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_IDV_value);
        lt_bp_car_upto1000_date_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_date_value);
        lt_bp_car_upto1000_zone = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_zone_value);
        lt_bp_car_upto1000_cc_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_cc_value);
        lt_bp_car_upto1000_nd_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_nd_value);
        lt_bp_car_upto1000_ndd_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_ndd_value);
        lt_bp_car_upto1000_uwd_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_uwd_value);
        lt_bp_car_upto1000_ncb_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_ncb_value);
        lt_bp_car_upto1000_lpg_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_lpg_value);
        lt_bp_car_upto1000_lpgtype_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_lpgtype_value);
        lt_bp_car_upto1000_antitheft = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_antitheft_value);
        lt_bp_car_upto1000_paod_value = findViewById(R.id.lt_ppdisplay_car_upto1000_pa_value);

        lt_bp_car_upto1000_od_value = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_od_value);
        lt_bp_car_upto1000_value_b = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_b_value);
        lt_ppdisplay_car_upto1000_ab_value= (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_ab_value);
        lt_ppdisplay_car_upto1000_lpgkit_value= (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_lpgkit_value);
        lt_ppdisplay_car_upto1000_pa_pass_value= (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_pa_pass_value);
        lt_ppdisplay_car_upto1000_total_value  = (TextView) findViewById(R.id.lt_ppdisplay_car_upto1000_total_value);
        lt_bp_car_upto1000_IDV_value.setText(b.getCharSequence("lt_bp_car_upto1000_idv_value"));
        lt_bp_car_upto1000_date_value.setText(b.getCharSequence("lt_bp_car_upto1000_date_value"));
        lt_bp_car_upto1000_zone.setText(b.getCharSequence("lt_bp_car_upto1000_zone"));
        lt_bp_car_upto1000_cc_value.setText(b.getCharSequence("lt_bp_car_upto1000_cc_value"));
        lt_bp_car_upto1000_nd_value.setText(b.getCharSequence("lt_bp_car_upto1000_nd_value"));
        lt_bp_car_upto1000_ndd_value.setText(b.getCharSequence("lt_bp_car_upto1000_ndd_value"));
        lt_bp_car_upto1000_uwd_value.setText(b.getCharSequence("lt_bp_car_upto1000_uwd_value"));
        lt_bp_car_upto1000_paod_value.setText(b.getCharSequence("lt_bp_car_upto1000_paod_value"));

        //   pp_car_upto1000_lpg.setText(b.getCharSequence("pp_car_upto1000_lpg"));
        //  pp_car_upto1000_lpgtype.setText(b.getCharSequence("pp_car_upto1000_lpgtype"));
        lt_bp_car_upto1000_antitheft.setText(b.getCharSequence("lt_bp_car_upto1000_antitheft"));



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Car Package Policy");

        findViewById(R.id.lt_ppdisplay_car_upto1000_home).setOnClickListener(listener_lt_ppdisplay_car_upto1000_home);
        share_btn = (CircleButton) findViewById(R.id.lt_ppdisplay_car_upto1000_share);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsChecker checker = new PermissionsChecker(longterm_ppdisplay_car_upto1000.this);
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(longterm_ppdisplay_car_upto1000.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);

                } else {

                    Date date = new Date();
                    SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyyHHmmss");
                    String filename = "InsurancePremium" + dateformat.format(date) + ".pdf";

                    File direct = new File(Environment.getExternalStorageDirectory() + "/InsurancePremiumCalculator");

                    if (!direct.exists()) {
                        File myDirectory = new File("/sdcard/InsurancePremiumCalculator/");
                        myDirectory.mkdirs();
                        myDirectory.setReadable(true);
                        myDirectory.setWritable(true);
                        myDirectory.setExecutable(true);
                    }

                    file = new File(new File("/sdcard/InsurancePremiumCalculator/"), filename);
                    if (file.exists()) {
                        file.delete();
                    }
                    Document document = new Document(PageSize.A4, 30, 30, 30, 30);
                    try {
                        PdfWriter.getInstance(document, new FileOutputStream(file));
                        document.open();
                        settingUpPDF(document);
                        document.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //Calculation
        String d1=b.getString("lt_bp_car_upto1000_date_value").toString();
        String s = d1;
        int y=b.getInt("year");
        int m=b.getInt("month");
        int d=b.getInt("day");


        int old_day=d;
        int old_month=m;
        int old_year=y;

        int mYear;
        int mMonth;
        int mDay;
        // Create Calendar instance
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        mYear = calendar1.get(Calendar.YEAR);
        mMonth = calendar1.get(Calendar.MONTH);
        mDay = calendar1.get(Calendar.DAY_OF_MONTH);

        // Set the values for the calendar fields YEAR, MONTH, and DAY_OF_MONTH.
        calendar1.set(mYear, mMonth, mDay);
        calendar2.set(old_year, old_month, old_day);

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




        //Calculation of Dop value

        if((b.getString("lt_bp_car_upto1000_zone")).equals("A")) {
            if (diffInDays < 1825) {
                double idv_value=Integer.valueOf(b.getString("lt_bp_car_upto1000_idv_value"));
                dop_value=idv_value*4.72/100;
                double round_value=dop_value;
                rounded_dop_value=new Float(Math.round(round_value));

            }else if(diffInDays >= 1825 ) {
                if (diffInDays < 3650) {
                    double idv_value = Integer.valueOf(b.getString("lt_bp_car_upto1000_idv_value"));
                    dop_value = idv_value * 4.72 / 100;
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                } else if (diffInDays >=3650) {
                    double idv_value = Integer.valueOf(b.getString("lt_bp_car_upto1000_idv_value"));
                    dop_value = idv_value * 4.72 / 100;
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                }
            }
        }
        else if((b.getString("lt_bp_car_upto1000_zone")).equals("B")){
            if (diffInDays < 1825) {
                double idv_value = Integer.valueOf(b.getString("lt_bp_car_upto1000_idv_value"));
                dop_value = idv_value * 4.62 / 100;
                double round_value = dop_value;
                rounded_dop_value = new Float(Math.round(round_value));

            } else if (diffInDays >=1825 ) {
                if (diffInDays < 3650) {
                    double idv_value = Integer.valueOf(b.getString("lt_bp_car_upto1000_idv_value"));
                    dop_value = idv_value * 4.62 / 100;
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                } else if (diffInDays >= 3650) {
                    double idv_value = Integer.valueOf(b.getString("lt_bp_car_upto1000_idv_value"));
                    dop_value = idv_value * 4.62 / 100;
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                }
            }

        }
        else{
            Toast.makeText(getApplicationContext(),"Please enter correct DoP",Toast.LENGTH_SHORT).show();
        }

        //LPG selection
        String radio_button_value=b.getString("lt_bp_car_upto1000_lpg");
        String yes=new String("Yes");

        //Setting LPGkit values for radiobutton selected
        if(radio_button_value.equals(yes)){
            lt_bp_lgkit_assumevalue=60;
            lt_bp_car_upto1000_lpg_value.setText("Yes");

        }
        else{
            lt_bp_lgkit_assumevalue=0;
            lt_bp_car_upto1000_lpg_value.setText("No");

        }

        //Calculation of U/W discount
        double uw_value = Integer.valueOf(b.getString("lt_bp_car_upto1000_uwd_value"));
        double val=((uw_value)*rounded_dop_value)/100;
        value_uw1=rounded_dop_value - val;
        double value_uw2=value_uw1;
        rounded_uw_value = new Float(Math.round(value_uw2));


        //Calculation of N/D Value
        String x2="0";
        if((b.getString("lt_bp_car_upto1000_nd_value")).equals(x2))
        {

            rounded_value_nd=rounded_uw_value;
            Toast.makeText(getApplicationContext(), " DOP value :  " + rounded_dop_value,  Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), " UW value :  " + rounded_uw_value,  Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), " ND value :  " + rounded_value_nd,  Toast.LENGTH_SHORT).show();
        }

        else
        {
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

            //double nd_value1 = Integer.valueOf(b.getString("pp_car_upto1000_nd_value"));
            double idv_value = Integer.valueOf(b.getString("lt_bp_car_upto1000_idv_value"));
            double value_nd1=(nd_value1)*idv_value/100;
            double value_nd2=value_uw1 + value_nd1;
            double value_nd3=value_nd2;
            nild_value = new Float(Math.round(value_nd1));
            rounded_value_nd = new Float(Math.round(value_nd3));
        }
        int rounded_value_nd_int = (int) rounded_value_nd;

        lt_bp_car_upto1000_nd_value.setText(b.getString("lt_bp_car_upto1000_nd_value"));
        //pp_car_upto1000_nd_value.setText(String.valueOf(rounded_value_nd_int));



        //Calculation part of L P G type

        String x="Inbuilt";
        String fx="Fixed";
        String button_value=b.getString("lt_bp_car_upto1000_lpgtype");

        if((button_value.equals(x) && radio_button_value.equals(yes))){
            rounded_lgptype_value=rounded_value_nd;
            lt_bp_car_upto1000_lpgtype_value.setText("InBuilt");

        }else if((button_value.equals(fx) && radio_button_value.equals(yes))) {
            double lgptype_value = Integer.valueOf(b.getString("lt_bp_car_scpassengers_lpgtype_value"));
            value_lgptype1=rounded_value_nd + ((lgptype_value)*4)/100;
            double value_lgptype2=value_lgptype1;
            rounded_lgptype_value = new Float(Math.round(value_lgptype2));
            int rounded_lgptype_value_int = (int) rounded_lgptype_value;
            //pp_car_upto1000_lpgtype_value.setText(String.valueOf(rounded_lgptype_value_int));
            lt_bp_car_upto1000_lpgtype_value.setText("Fixed");

        }else {
            rounded_lgptype_value=rounded_value_nd;
            lt_bp_car_upto1000_lpgtype_value.setText("N/A");
        }

        //Calculation of Anti-Theft value
        //
        String x3="No";
        String a3="Yes";
        if((b.getString("lt_bp_car_upto1000_antitheft")).equals(x3))
        {

            rounded_value_antitheft=rounded_lgptype_value ;

        }
        else if((b.getString("lt_bp_car_upto1000_antitheft")).equals(a3))
        {
            double l=rounded_lgptype_value*2.5/100;
            double antitheft_value1 = Math.min((double)500,l);
            double value_antitheft1=(rounded_lgptype_value)-(antitheft_value1);
            double value_antitheft2=value_antitheft1;
            rounded_value_antitheft = new Float(Math.round(value_antitheft2));
        }


        //Calculation of ND discount
        double ndd_value = Integer.valueOf(b.getString("lt_bp_car_upto1000_ndd_value"));
        double ndd = ((ndd_value) * nild_value) / 100;
        value_ndd = rounded_value_nd - ndd;
        double value_ndd2 = value_ndd;
        rounded_ndd_value = new Float(Math.round(value_ndd2));


        // Calculation of NCB
        double spinner_value = Integer.valueOf(b.getString("lt_bp_car_spinner_value"));
        double spin_val=((spinner_value)*rounded_ndd_value)/100;
        value_spin1=rounded_ndd_value - spin_val;
        double value_spin2=value_spin1;
        rounded_ncb_value = new Float(Math.round(value_spin2));
        int rounded_ncb_value_int = (int) rounded_ncb_value;
        lt_bp_car_upto1000_ncb_value.setText(b.getString("lt_bp_car_spinner_value"));
        //pp_car_upto1000_ncb_value.setText(String.valueOf(rounded_ncb_value_int));

        lt_bp_car_upto1000_od_value.setText(String.valueOf(rounded_ncb_value_int));



        //Calculation of Total of B part

        lt_bp_scpass_value= Integer.valueOf(b.getString("lt_bp_car_scpassengers_upto1000"));
        String no="No";
        String oneLac="1 Lacs";
        String twoLac="2 Lacs";
        if(b.getString("lt_bp_car_patooccupants_upto1000").equals(oneLac)) {
            lt_bp_pa_pass_value=(lt_bp_scpass_value)*50;
        }else if(b.getString("lt_bp_car_patooccupants_upto1000").equals(twoLac)){
            lt_bp_pa_pass_value=(lt_bp_scpass_value)*100;
        }else if(b.getString("lt_bp_car_patooccupants_upto1000").equals(no)){
            lt_bp_pa_pass_value=0;
        }
        int lt_bp_pa_pass_value_int = (int) lt_bp_pa_pass_value;
        lt_ppdisplay_car_upto1000_pa_pass_value.setText(String.valueOf(lt_bp_pa_pass_value_int));

        //Calculation of Total by adding LPG Kit Value of 60 if Yes 0 if No
        if(radio_button_value.equals(yes)){
            lt_bp_lgkit_assumevalue=60;


        }
        else{
            lt_bp_lgkit_assumevalue=0;
        }
        lt_ppdisplay_car_upto1000_lpgkit_value.setText(String.valueOf(lt_bp_lgkit_assumevalue));
        double total=5286.00+Double.valueOf(lt_bp_car_upto1000_paod_value.getText().toString())+150.00+lt_bp_lgkit_assumevalue+(double) lt_bp_pa_pass_value;

        lt_bp_total_premium = total;

        //rounding of total_premium value
        double round_value=lt_bp_total_premium;
        float rounded_total_premium=new Float(Math.round(round_value));
        int rounded_total_premium_int = (int) rounded_total_premium;
        lt_bp_car_upto1000_value_b.setText(String.valueOf(rounded_total_premium_int));

        double total_ab=(double)rounded_total_premium+(double)rounded_ncb_value;
        int total_ab_int = (int) total_ab;
        lt_ppdisplay_car_upto1000_ab_value.setText(String.valueOf(total_ab_int));
        double total_plus_service_tax=total_ab+total_ab*18/100;
        double total_AB=total_plus_service_tax;
        float rounded_total_AB=new Float(Math.round(total_AB));
        int rounded_total_AB_int = (int) rounded_total_AB;
        lt_ppdisplay_car_upto1000_total_value.setText(String.valueOf(rounded_total_AB_int));

    }

    View.OnClickListener listener_lt_ppdisplay_car_upto1000_home = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(longterm_ppdisplay_car_upto1000.this, home_activity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
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
        checkfunction(longterm_ppdisplay_car_upto1000.this);
    }

    private void settingUpPDF(Document document)
    {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(BaseColor.BLACK);
        Font white = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);

        try {
            Chunk mChunk = new Chunk("PREMIUM COMPUTATION SHEET");
            Paragraph mPara = new Paragraph(mChunk);
            mPara.setAlignment(Element.ALIGN_CENTER);
            document.add(mPara);
            document.add(new Chunk(lineSeparator));

            Paragraph p;
            p = new Paragraph();
            p.add(new Chunk("Vehicle type"));
            p.setTabSettings(new TabSettings(56f));
            p.add(Chunk.TABBING);
            p.add(new Chunk(": PRIVATE CAR"));

            PdfPTable table = new PdfPTable(2);
            table.setTotalWidth(document.getPageSize().getWidth() - 80);
            table.setLockedWidth(true);
            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Policy Type"));
            p.setTabSettings(new TabSettings(56f));
            p.add(Chunk.TABBING);
            p.add(new Chunk(": PACKAGE POLICY"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            document.add(table);
            document.add(new Chunk(lineSeparator));

            mChunk = new Chunk("SCHEDULE OF PREMIUM");
            mPara = new Paragraph(mChunk);
            mPara.setAlignment(Element.ALIGN_CENTER);

            document.add(mPara);
            document.add(new Chunk(lineSeparator));

            table = new PdfPTable(3);
            table.setTotalWidth(document.getPageSize().getWidth() - 80);
            table.setLockedWidth(true);

            p = new Paragraph();
            p.add(new Chunk("COVER DESCRIPTION"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("PREMIUM"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            document.add(table);
            document.add(new Chunk(lineSeparator));

            table = new PdfPTable(3);
            table.setTotalWidth(document.getPageSize().getWidth() - 80);
            table.setLockedWidth(true);

            p = new Paragraph();
            p.add(new Chunk("IDV"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lt_bp_car_upto1000_IDV_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("DATE"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_date_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("ZONE"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_zone.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("CC"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_cc_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("LPG"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_lpg_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("LPG Type"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_lpgtype_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("N / D"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_nd_value.getText().toString()+"%"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Anti-Theft"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_antitheft.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("N / D DISCOUNT"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_ndd_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("U/W DISCOUNT"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_uwd_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("N C B"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lt_bp_car_upto1000_ncb_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);


            p = new Paragraph();
            p.add(new Chunk("(A) -> OD TOTAL",white));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lt_bp_car_upto1000_od_value.getText().toString(),white));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("TP BASIC"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+"7897"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("P A to Owner - Driver"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lt_bp_car_upto1000_paod_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("L L to Driver"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+"150"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("P A to Passenger"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lt_ppdisplay_car_upto1000_pa_pass_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("LPG Kit"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lt_ppdisplay_car_upto1000_lpgkit_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("(B) -> TOTAL",white));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lt_bp_car_upto1000_value_b.getText().toString(),white));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("(A) + (B)"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lt_ppdisplay_car_upto1000_ab_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Service Tax"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("18%"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Total Premium",white));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lt_ppdisplay_car_upto1000_total_value.getText().toString(),white));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            document.add(table);
            document.add(new Chunk(lineSeparator));
            mChunk = new Chunk("Shared from Motor Insurance Premium Calculator App");
            mPara = new Paragraph(mChunk);
            mPara.setAlignment(Element.ALIGN_CENTER);
            document.add(mPara);
            document.add(new Chunk(lineSeparator));

            Uri uri = FileProvider.getUriForFile(longterm_ppdisplay_car_upto1000.this, BuildConfig.APPLICATION_ID + ".provider",file);
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM, uri);

            startActivity(Intent.createChooser(share,"Share Using"));


        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}
