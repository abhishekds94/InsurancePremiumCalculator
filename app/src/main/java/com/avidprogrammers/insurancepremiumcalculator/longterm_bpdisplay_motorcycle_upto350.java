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

public class longterm_bpdisplay_motorcycle_upto350 extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    long diffInDays;
    double dop_value;
    int pp_lgkit_assumevalue,pp_scpass_value,pp_pa_pass_value;
    float rounded_dop_value;
    int pp_tax_value=18;
    float value_nd;
    float rounded_lgptype_value;
    float rounded_value_nd,rounded_value_antitheft,rounded_uw_value,rounded_ncb_value,rounded_ndd_value;
    double value_lgptype1, nild_value, value_uw1, value_spin1, value_ndd;
    double value_lgptype2;
    double pp_total_premium;

    CircleButton share_btn;
    File file;
    TextView lt_bp_motorcycle_upto350_IDV_value;
    TextView lt_bp_motorcycle_upto350_date_value;
    TextView lt_bp_motorcycle_upto350_zone_value;
    TextView lt_bp_motorcycle_upto350_cc_value;
    TextView lt_bp_motorcycle_upto350_nd_value;
    TextView lt_bp_motorcycle_upto350_ndd_value;
    TextView lt_bp_motorcycle_upto350_uwd_value;
    TextView lt_bp_motorcycle_upto350_ncb_value;
    TextView lt_bp_motorcycle_upto350_od_value;
    TextView lt_bp_motorcycle_upto350_ab_value;
    TextView lt_bp_motorcycle_upto350_b_value;
    TextView lt_bpdisplay_motorcycle_upto350_total_value;
    TextView lt_bpdisplay_motorcycle_upto350_paod_value;

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
        checkfunction(longterm_bpdisplay_motorcycle_upto350.this);

        setContentView(R.layout.longterm_bpdisplay_motorcycle_upto350);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        share_btn = (CircleButton) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_share);
        Bundle b = getIntent().getExtras();
        lt_bp_motorcycle_upto350_IDV_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_IDV_value);
        lt_bp_motorcycle_upto350_date_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_date_value);
        lt_bp_motorcycle_upto350_zone_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_zone_value);
        lt_bp_motorcycle_upto350_cc_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_cc_value);
        lt_bp_motorcycle_upto350_nd_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_nd_value);
        lt_bp_motorcycle_upto350_ndd_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_ndd_value);
        lt_bp_motorcycle_upto350_uwd_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_uwd_value);
        lt_bp_motorcycle_upto350_ncb_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_ncb_value);
        lt_bp_motorcycle_upto350_od_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_od_value);
        lt_bp_motorcycle_upto350_ab_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_ab_value);
        lt_bp_motorcycle_upto350_b_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_b_value);
        lt_bpdisplay_motorcycle_upto350_paod_value = findViewById(R.id.lt_bpdisplay_motorcycle_upto350_pa_value);
        lt_bpdisplay_motorcycle_upto350_total_value = (TextView) findViewById(R.id.lt_bpdisplay_motorcycle_upto350_total_value);

        lt_bp_motorcycle_upto350_IDV_value.setText(b.getCharSequence("lt_bp_motorcycle_upto350_idv_value"));
        lt_bp_motorcycle_upto350_date_value.setText(b.getCharSequence("lt_bp_motorcycle_upto350_date_value"));
        lt_bp_motorcycle_upto350_zone_value.setText(b.getCharSequence("lt_bp_motorcycle_upto350_zone"));
        lt_bp_motorcycle_upto350_cc_value.setText(b.getCharSequence("lt_bp_motorcycle_upto350_cc_value"));
        lt_bp_motorcycle_upto350_nd_value.setText(b.getCharSequence("lt_bp_motorcycle_upto350_nd_value"));
        lt_bp_motorcycle_upto350_ndd_value.setText(b.getCharSequence("lt_bp_motorcycle_upto350_ndd_value"));
        lt_bp_motorcycle_upto350_uwd_value.setText(b.getCharSequence("lt_bp_motorcycle_upto350_uwd_value"));
        lt_bpdisplay_motorcycle_upto350_paod_value.setText(b.getCharSequence("lt_bp_motorcycle_upto350_paod_value"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Motorcycle Package Policy");

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsChecker checker = new PermissionsChecker(longterm_bpdisplay_motorcycle_upto350.this);
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(longterm_bpdisplay_motorcycle_upto350.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);

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

        findViewById(R.id.lt_bpdisplay_motorcycle_upto350_home).setOnClickListener(listener_lt_bpdisplay_motorcycle_upto350_home);


        //Calculation
        String d1 = b.getString("lt_bp_motorcycle_upto350_date_value").toString();
        String s = d1;
        int y = b.getInt("year");
        int m = b.getInt("month");
        int d = b.getInt("day");


        int old_day = d;
        int old_month = m;
        int old_year = y;

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
        diffInDays = diffInMilis / (24 * 60 * 60 * 1000);


        //Calculation of Dop value

        if ((b.getString("lt_bp_motorcycle_upto350_zone")).equals("A")) {
            if (diffInDays < 1825) {
                double idv_value = Integer.valueOf(b.getString("lt_bp_motorcycle_upto350_idv_value"));
                dop_value = idv_value * 1.76 / 100;
                double round_value = dop_value;
                rounded_dop_value = new Float(Math.round(round_value));

            } else if (diffInDays >= 1825) {
                if (diffInDays < 3650) {
                    double idv_value = Integer.valueOf(b.getString("lt_bp_motorcycle_upto350_idv_value"));
                    dop_value = idv_value * 1.76 / 100;
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                } else if (diffInDays >= 3650) {
                    double idv_value = Integer.valueOf(b.getString("lt_bp_motorcycle_upto350_idv_value"));
                    dop_value = idv_value * 1.76 / 100;
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                }
            }
        } else if ((b.getString("lt_bp_motorcycle_upto350_zone")).equals("B")) {
            if (diffInDays < 1825) {
                double idv_value = Integer.valueOf(b.getString("lt_bp_motorcycle_upto350_idv_value"));
                dop_value = idv_value * 1.76 / 100;
                double round_value = dop_value;
                rounded_dop_value = new Float(Math.round(round_value));

            } else if (diffInDays >= 1825) {
                if (diffInDays < 3650) {
                    double idv_value = Integer.valueOf(b.getString("lt_bp_motorcycle_upto350_idv_value"));
                    dop_value = idv_value * 1.76 / 100;
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                } else if (diffInDays >= 3650) {
                    double idv_value = Integer.valueOf(b.getString("lt_bp_motorcycle_upto350_idv_value"));
                    dop_value = idv_value * 1.76 / 100;
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                }
            }

        }

        else {
            Toast.makeText(getApplicationContext(), "Please enter correct Date", Toast.LENGTH_SHORT).show();

        }


//        String radio_button_value=b.getString("pp_car_upto1000_lpg");
//        String yes=new String("Yes");
//
//        //Setting LPGkit values for radiobutton selected
//        if(radio_button_value.equals(yes)){
//            pp_lgkit_assumevalue=60;
//            pp_car_upto1000_lpg_value.setText("Yes");
//
//        }
//        else{
//            pp_lgkit_assumevalue=0;
//            pp_car_upto1000_lpg_value.setText("No");
//
//        }




        //Calculation of U/W discount
        double uw_value = Integer.valueOf(b.getString("lt_bp_motorcycle_upto350_uwd_value"));
        double val = ((uw_value) * rounded_dop_value) / 100;
        value_uw1 = rounded_dop_value - val;
        double value_uw2 = value_uw1;
        rounded_uw_value = new Float(Math.round(value_uw2));


        //Calculation of ND value
        double nd_value1 = 0;
        String radio_button_value=b.getString("lt_bp_motorcycle_upto350_nd");

        if(radio_button_value.equals("NO")){
            rounded_value_nd=rounded_dop_value;

        } else {

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

            //  double nd_value1 = Integer.valueOf(b.getString("lt_bp_motorcycle_upto75_nd_value"));
            double value_nd1 = (nd_value1) * rounded_dop_value / 100;
            double value_nd2 = rounded_uw_value + value_nd1;
            double value_nd3 = value_nd2;
            nild_value = new Float(Math.round(value_nd1));
            rounded_value_nd = new Float(Math.round(value_nd3));
            Toast.makeText(getApplicationContext(), " " + rounded_value_nd,  Toast.LENGTH_SHORT).show();
        }
        int rounded_value_nd_int = (int) rounded_value_nd;
        if(nd_value1==0) {
            lt_bp_motorcycle_upto350_nd_value.setText(String.valueOf("0"));
        }else {

            lt_bp_motorcycle_upto350_nd_value.setText(b.getString("lt_bp_motorcycle_upto350_nd_value"));
            //lt_bp_motorcycle_upto350_nd_value.setText(String.valueOf(rounded_value_nd_int));
        }


        //Calculation of ND discount
        double ndd_value = Integer.valueOf(b.getString("lt_bp_motorcycle_upto350_ndd_value"));
        double ndd = ((ndd_value) * nild_value) / 100;
        value_ndd = rounded_value_nd - ndd;
        double value_ndd2 = value_ndd;
        rounded_ndd_value = new Float(Math.round(value_ndd2));



        // Calculation of NCB
        double spinner_value = Integer.valueOf(b.getString("lt_bp_motorcycle_upto350_spinner_value"));
        double spin_val = ((spinner_value) * rounded_ndd_value) / 100;
        value_spin1 = rounded_ndd_value - spin_val;
        double value_spin2 = value_spin1;
        rounded_ncb_value = new Float(Math.round(value_spin2));
        int rounded_ncb_value_int = (int) rounded_ncb_value;

        lt_bp_motorcycle_upto350_ncb_value.setText(b.getString("lt_bp_motorcycle_upto350_spinner_value"));
        //lt_bp_motorcycle_upto350_ncb_value.setText(String.valueOf(rounded_ncb_value_int));

        lt_bp_motorcycle_upto350_od_value.setText(String.valueOf(rounded_ncb_value_int));



//        Toast.makeText(getApplicationContext(), " DOP:  " + rounded_dop_value,  Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), " UW:  " + value_uw2,  Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), " ND:  " + rounded_value_nd,  Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), " NDD:  " + ndd,  Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), " NCB:  " + value_spin2,  Toast.LENGTH_SHORT).show();



        //Calculation of B part
        pp_total_premium = 5453 + Integer.valueOf(lt_bpdisplay_motorcycle_upto350_paod_value.getText().toString());


        double total=5453+ Integer.valueOf(lt_bpdisplay_motorcycle_upto350_paod_value.getText().toString());

        pp_total_premium = total;


        //rounding of total_premium value
        double round_value=pp_total_premium;
        float rounded_total_premium=new Float(Math.round(round_value));
        int rounded_total_premium_int = (int) rounded_total_premium;

        lt_bp_motorcycle_upto350_b_value.setText(String.valueOf(rounded_total_premium_int));

        double total_ab=(double)rounded_total_premium+(double)rounded_ncb_value;
        int total_ab_int = (int) total_ab;
        lt_bp_motorcycle_upto350_ab_value.setText(String.valueOf(total_ab_int));
        double total_plus_service_tax=total_ab+total_ab*18/100;
        double total_AB=total_plus_service_tax;
        float rounded_total_AB=new Float(Math.round(total_AB));
        int rounded_total_AB_int = (int) rounded_total_AB;
        lt_bpdisplay_motorcycle_upto350_total_value.setText(String.valueOf(rounded_total_AB_int));


    }
    View.OnClickListener listener_lt_bpdisplay_motorcycle_upto350_home = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(longterm_bpdisplay_motorcycle_upto350.this, home_activity.class);
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
        checkfunction(longterm_bpdisplay_motorcycle_upto350.this);
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
            p.add(new Chunk(": MOTORCYCLE"));

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
            p.add(new Chunk("Rs. "+lt_bp_motorcycle_upto350_IDV_value.getText().toString()));
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
            p.add(new Chunk(lt_bp_motorcycle_upto350_date_value.getText().toString()));
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
            p.add(new Chunk(lt_bp_motorcycle_upto350_zone_value.getText().toString()));
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
            p.add(new Chunk(lt_bp_motorcycle_upto350_cc_value.getText().toString()));
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
            p.add(new Chunk(lt_bp_motorcycle_upto350_nd_value.getText().toString()));
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
            p.add(new Chunk(lt_bp_motorcycle_upto350_ndd_value.getText().toString()));
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
            p.add(new Chunk(lt_bp_motorcycle_upto350_uwd_value.getText().toString()));
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
            p.add(new Chunk(lt_bp_motorcycle_upto350_ncb_value.getText().toString()));
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
            p.add(new Chunk(lt_bp_motorcycle_upto350_date_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+lt_bp_motorcycle_upto350_od_value.getText().toString(),white));
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
            p.add(new Chunk("Rs. "+"3285"));
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
            p.add(new Chunk("Rs. "+lt_bpdisplay_motorcycle_upto350_paod_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+lt_bp_motorcycle_upto350_b_value.getText().toString(),white));
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
            p.add(new Chunk("Rs. "+lt_bp_motorcycle_upto350_ab_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+lt_bpdisplay_motorcycle_upto350_total_value.getText().toString(),white));
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

            Uri uri = FileProvider.getUriForFile(longterm_bpdisplay_motorcycle_upto350.this, BuildConfig.APPLICATION_ID + ".provider",file);
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
