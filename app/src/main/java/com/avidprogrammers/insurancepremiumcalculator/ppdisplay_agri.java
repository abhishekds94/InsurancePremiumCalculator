package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avidprogrammers.utils.PermissionsActivity;
import com.avidprogrammers.utils.PermissionsChecker;
import com.google.android.gms.ads.AdRequest;
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

public class ppdisplay_agri extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    long diffInDays;
    double dop_value;
    float rounded_dop_value;
    float rounded_value_nd,rounded_value_antitheft,rounded_uw_value,rounded_ncb_value;
    double value_uw1;
    double value_spin1;
    double total_premium_b;
    CircleButton share_btn;
    File file;

    TextView pp_agri_IDV_tractor_value;
    TextView ppdisplay_agri_IDV_trailer_value;
    TextView pp_agri_date_value;
    TextView pp_agri_zone;
    TextView pp_agri_nd_value;
    TextView pp_agri_uwd_value;
    TextView agri_coolie;
    TextView ppdisplay_agri_ncb_value;
    TextView ppdisplay_agri_od_value;
    TextView ppdisplay_agri_total_value;
    TextView ppdisplay_agri_ab_value;
    TextView ppdisplay_agri_paod_value;
    TextView ppdisplay_agri_b_value;
    TextView ppdisplay_agri_tp_trailer_value;

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
        checkfunction(ppdisplay_agri.this);

        setContentView(R.layout.ppdisplay_agri);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Bundle b = getIntent().getExtras();
         pp_agri_IDV_tractor_value = (TextView) findViewById(R.id.ppdisplay_agri_IDV_value);
         ppdisplay_agri_IDV_trailer_value = (TextView) findViewById(R.id.ppdisplay_agri_IDV_trailer_value);
         pp_agri_date_value = (TextView) findViewById(R.id.ppdisplay_agri_date_value);
         pp_agri_zone = (TextView) findViewById(R.id.ppdisplay_agri_zone_value);
         pp_agri_nd_value = (TextView) findViewById(R.id.ppdisplay_agri_nd_value);
         pp_agri_uwd_value = (TextView) findViewById(R.id.ppdisplay_agri_uwd_value);
         agri_coolie = (TextView) findViewById(R.id.ppdisplay_agri_coolie_value);
         ppdisplay_agri_ncb_value= (TextView) findViewById(R.id.ppdisplay_agri_ncb_value);
         ppdisplay_agri_od_value= (TextView) findViewById(R.id.ppdisplay_agri_od_value);
         ppdisplay_agri_total_value= (TextView) findViewById(R.id.ppdisplay_agri_total_value);
         ppdisplay_agri_ab_value= (TextView) findViewById(R.id.ppdisplay_agri_ab_value);
         ppdisplay_agri_b_value= (TextView) findViewById(R.id.ppdisplay_agri_b_value);
         ppdisplay_agri_paod_value = findViewById(R.id.ppdisplay_agri_pa_value);
         ppdisplay_agri_tp_trailer_value= (TextView) findViewById(R.id.ppdisplay_agri_tp_trailer_value);


        pp_agri_IDV_tractor_value.setText(b.getCharSequence("pp_agri_idv_tractor_value"));

        pp_agri_date_value.setText(b.getCharSequence("pp_agri_date_value"));
        pp_agri_zone.setText(b.getCharSequence("pp_agri_zone"));

        pp_agri_uwd_value.setText(b.getCharSequence("pp_agri_uwd_value"));

        ppdisplay_agri_paod_value.setText(b.getCharSequence("pp_agri_paod_value"));
        ppdisplay_agri_IDV_trailer_value.setText(b.getCharSequence("pp_agri_trailer_value"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tractors & Trailers Package Policy");

        findViewById(R.id.ppdisplay_agri_home).setOnClickListener(listener_ppdisplay_agri_home);

        share_btn = (CircleButton) findViewById(R.id.ppdisplay_agri_share);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsChecker checker = new PermissionsChecker(ppdisplay_agri.this);
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(ppdisplay_agri.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);

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


        String d1=b.getString("pp_agri_date_value").toString();
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

        if((b.getString("pp_agri_zone")).equals("A")) {
            if (diffInDays < 1825) {
                double idv_value_tractor=Integer.valueOf(b.getString("pp_agri_idv_tractor_value"));
                double idv_value_trailer=Integer.valueOf(b.getString("pp_agri_trailer_value"));
                dop_value=(idv_value_tractor*1.208/100) +(idv_value_trailer*1.208/100);
                double round_value=dop_value;
                rounded_dop_value=new Float(Math.round(round_value));

            }else if(diffInDays >= 1825 ) {
                if (diffInDays < 2555) {
                    double idv_value_tractor = Integer.valueOf(b.getString("pp_agri_idv_tractor_value"));
                    double idv_value_trailer=Integer.valueOf(b.getString("pp_agri_trailer_value"));
                    dop_value =( idv_value_tractor * 1.238 / 100 )+ (idv_value_trailer* 1.238 / 100);
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                } else if (diffInDays >=2555) {
                    double idv_value_tractor = Integer.valueOf(b.getString("pp_agri_idv_tractor_value"));
                    double idv_value_trailer=Integer.valueOf(b.getString("pp_agri_trailer_value"));
                    dop_value = (idv_value_tractor * 1.268 / 100) +(idv_value_trailer* 1.268 / 100);
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                }
            }
        }
        else if((b.getString("pp_agri_zone")).equals("B")){
            if (diffInDays < 1825) {
                double idv_value_tractor= Integer.valueOf(b.getString("pp_agri_idv_tractor_value"));
                double idv_value_trailer=Integer.valueOf(b.getString("pp_agri_trailer_value"));
                dop_value = (idv_value_tractor * 1.202 / 100) + (idv_value_trailer* 1.202 / 100);
                double round_value = dop_value  ;
                rounded_dop_value = new Float(Math.round(round_value));

            } else if (diffInDays >=1825 ) {
                if (diffInDays < 2555) {
                    double idv_value_tractor = Integer.valueOf(b.getString("pp_agri_idv_tractor_value"));
                    double idv_value_trailer=Integer.valueOf(b.getString("pp_agri_trailer_value"));
                    dop_value = (idv_value_tractor * 1.232 / 100) + (idv_value_trailer* 1.232 / 100);
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                } else if (diffInDays >= 2555) {
                    double idv_value_tractor = Integer.valueOf(b.getString("pp_agri_idv_tractor_value"));
                    double idv_value_trailer=Integer.valueOf(b.getString("pp_agri_trailer_value"));
                    dop_value = (idv_value_tractor * 1.262 / 100) + (idv_value_trailer* 1.262 / 100);
                    double round_value = dop_value ;
                    rounded_dop_value = new Float(Math.round(round_value));
                }
            }

        } else if((b.getString("pp_agri_zone")).equals("C")){
            if (diffInDays < 1825) {
                double idv_value_tractor = (double) Integer.valueOf(b.getString("pp_agri_idv_tractor_value"));
                double idv_value_trailer = (double) Integer.valueOf(b.getString("pp_agri_trailer_value"));
                dop_value =  ((idv_value_tractor * 1.190 )/ 100 ) + ((idv_value_trailer * 1.190) / 100);
                double round_value = dop_value  ;
                rounded_dop_value = new Float(Math.round(round_value));

            } else if (diffInDays >=1825 ) {
                if (diffInDays < 2555) {
                    double idv_value_tractor= Integer.valueOf(b.getString("pp_agri_idv_tractor_value"));
                    double idv_value_trailer=Integer.valueOf(b.getString("pp_agri_trailer_value"));
                    dop_value = idv_value_tractor* 1.220 / 100 + idv_value_trailer* 1.220 / 100;
                    double round_value = dop_value ;
                    rounded_dop_value = new Float(Math.round(round_value));
                } else if (diffInDays >= 2555) {
                    double idv_value_tractor = Integer.valueOf(b.getString("pp_agri_idv_tractor_value"));
                    double idv_value_trailer=Integer.valueOf(b.getString("pp_agri_trailer_value"));
                    dop_value = idv_value_tractor* 1.250 / 100 + idv_value_trailer* 1.250 / 100;
                    double round_value = dop_value;
                    rounded_dop_value = new Float(Math.round(round_value));
                }
            }

        }
        else{
            Toast.makeText(getApplicationContext(),"Please enter correct DoP",Toast.LENGTH_SHORT).show();
        }

        //Calculation of N/D

        double nd_value = Integer.valueOf(b.getString("pp_agri_nd_value"));
        double value_nd1=((nd_value)*rounded_dop_value)/100;
        double value_nd2=rounded_dop_value + value_nd1;
        double value_nd3=value_nd2;
        rounded_value_nd = new Float(Math.round(value_nd3));

        int rounded_value_nd_int = (int) rounded_value_nd;

        pp_agri_nd_value.setText(b.getCharSequence("pp_agri_nd_value"));

        //Calculation of U/W discount
        double uw_value = Integer.valueOf(b.getString("pp_agri_uwd_value"));
        double val=((uw_value)*rounded_value_nd)/100;
        value_uw1=rounded_value_nd - val;
        double value_uw2=value_uw1;
        rounded_uw_value = new Float(Math.round(value_uw2));

        pp_agri_uwd_value.setText(String.valueOf((int)uw_value));

        // Calculation of NCB
        double spinner_value = Integer.valueOf(b.getString("pp_agri_spinner_value"));
        double spin_val=((spinner_value)*rounded_uw_value)/100;
        value_spin1=rounded_uw_value - spin_val;
        double value_spin2=value_spin1;
        rounded_ncb_value = new Float(Math.round(value_spin2));
        int rounded_ncb_value_int = (int) rounded_ncb_value;

        ppdisplay_agri_ncb_value.setText(String.valueOf((int)spinner_value));

        ppdisplay_agri_od_value.setText(String.valueOf(rounded_ncb_value_int));



        //Calculation of B
        int pp_agri_tp_basic_tractor=6115;
        String no="No";
        int pp_agri_tp_basic_trailer;
        if(b.getString("pp_agri_trailer").equals(no)) {
            pp_agri_tp_basic_trailer=0;
        }else {
            pp_agri_tp_basic_trailer=2091;
        }

        int pp_agri_pa_owner=Integer.parseInt(ppdisplay_agri_paod_value.getText().toString());
        int pp_agri_ll_driver=50;
        int pp_agri_coolie_value=Integer.valueOf(b.getString("pp_agri_coolie"));

        agri_coolie.setText(String.valueOf(pp_agri_coolie_value*50));
        double total=pp_agri_tp_basic_tractor + pp_agri_tp_basic_trailer+ pp_agri_pa_owner + pp_agri_ll_driver+ (pp_agri_coolie_value*50);
        total_premium_b = total;
        ppdisplay_agri_b_value.setText(String.valueOf((int)total_premium_b));
        ppdisplay_agri_tp_trailer_value.setText(String.valueOf(pp_agri_tp_basic_trailer));

        //rounding of total_premium value
        double round_value=total_premium_b+value_spin2;
        ppdisplay_agri_ab_value.setText(String.valueOf((int)round_value));

        double round_value_total=round_value+round_value*18/100;
        float rounded_total_premium=new Float(Math.round(round_value_total));
        int rounded_total_premium_int = (int) rounded_total_premium;
        ppdisplay_agri_total_value.setText(String.valueOf(rounded_total_premium_int));

    }

    View.OnClickListener listener_ppdisplay_agri_home = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ppdisplay_agri.this, home_activity.class);
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
        checkfunction(ppdisplay_agri.this);
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
            p.add(new Chunk(": TRACTORS & TRAILERS"));

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
            p.add(new Chunk("IDV (TRACTORS)"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+pp_agri_IDV_tractor_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("IDV (TRAILERS)"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+ppdisplay_agri_IDV_trailer_value.getText().toString()));
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
            p.add(new Chunk(pp_agri_date_value.getText().toString()));
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
            p.add(new Chunk(pp_agri_zone.getText().toString()));
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
            p.add(new Chunk(pp_agri_nd_value.getText().toString()+"%"));
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
            p.add(new Chunk(pp_agri_uwd_value.getText().toString()));
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
            p.add(new Chunk(ppdisplay_agri_ncb_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_agri_od_value.getText().toString(),white));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.BLACK);
            pdfPCell.setBackgroundColor(BaseColor.BLACK);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("TP BASIC (TRACTORS)"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+"6115"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("TP BASIC (TRAILERS)"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+ppdisplay_agri_tp_trailer_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_agri_paod_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+"50"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("COOLIE"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+agri_coolie.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_agri_b_value.getText().toString(),white));
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
            p.add(new Chunk("Rs. "+ppdisplay_agri_ab_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_agri_total_value.getText().toString(),white));
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



            Uri uri = FileProvider.getUriForFile(ppdisplay_agri.this, BuildConfig.APPLICATION_ID + ".provider",file);
            //Uri uri = Uri.fromFile(file);
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
