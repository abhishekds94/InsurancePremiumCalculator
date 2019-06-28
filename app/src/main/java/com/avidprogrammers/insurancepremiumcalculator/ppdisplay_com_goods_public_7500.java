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
import android.widget.TableRow;
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

public class ppdisplay_com_goods_public_7500 extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;
    private double tax_18;
    private double tax_12;
    long diffInDays;
    double dop_value;
    float rounded_dop_value;
    float rounded_value_nd,rounded_uw_value,rounded_ncb_value;
    double value_uw1;
    double value_spin1;
    int imt23_value;
    int mYear;
    int mMonth;
    int mDay;
    CircleButton share_btn;
    File file;
    TextView ppdisplay_com_goods_public_7500_total_idv;
    TextView pp_com_goods_public_7500_gvw_value;
    TextView pp_com_goods_public_7500_date_value;
    TextView pp_com_goods_public_7500_zone;
    TextView pp_com_goods_public_7500_imt23;
    TextView pp_com_goods_public_7500_imt23_value;
    TextView pp_com_goods_public_7500_nd_value;
    TextView pp_com_goods_public_7500_uwd_value;
    TextView pp_com_goods_public_7500_ncb_value;
    TextView pp_com_goods_public_7500_coolie_value;
    TextView pp_com_goods_public_7500_nfpp_value;
    TextView pp_com_goods_public_7500_paod_value;
    TextView ppdisplay_com_goods_public_7500_od_value;
    TextView ppdisplay_com_goods_public_7500_b_value;
    TextView ppdisplay_com_goods_public_7500_ab_value;
    TextView ppdisplay_com_goods_public_7500_tax18_value;
    TextView ppdisplay_com_goods_public_7500_tax12_value;
    TextView ppdisplay_com_goods_public_7500_total_value;

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
        checkfunction(ppdisplay_com_goods_public_7500.this);

        setContentView(R.layout.ppdisplay_com_goods_public_7500);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle b = getIntent().getExtras();
         pp_com_goods_public_7500_gvw_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_gvw_value);
         pp_com_goods_public_7500_date_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_date_value);
         pp_com_goods_public_7500_zone = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_zone_value);
         pp_com_goods_public_7500_imt23 = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_imt23);
         pp_com_goods_public_7500_imt23_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_imt23_value);
         pp_com_goods_public_7500_nd_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_nd_value);
         pp_com_goods_public_7500_uwd_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_uwd_value);
         pp_com_goods_public_7500_ncb_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_ncb_value);
         pp_com_goods_public_7500_coolie_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_coolie_value);
         pp_com_goods_public_7500_nfpp_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_nfpp_value);
         ppdisplay_com_goods_public_7500_od_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_od_value);
         ppdisplay_com_goods_public_7500_b_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_b_value);
         ppdisplay_com_goods_public_7500_ab_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_ab_value);
         ppdisplay_com_goods_public_7500_tax18_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_tax18_value);
         ppdisplay_com_goods_public_7500_tax12_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_tax12_value);
         pp_com_goods_public_7500_paod_value = findViewById(R.id.ppdisplay_com_goods_public_7500_pa_value);
         ppdisplay_com_goods_public_7500_total_value = (TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_total_value);
        ppdisplay_com_goods_public_7500_total_idv = ((TextView) findViewById(R.id.ppdisplay_com_goods_public_7500_IDV_value));
        ppdisplay_com_goods_public_7500_total_idv.setText(b.getCharSequence("pp_com_goods_public_7500_idv_value"));
        pp_com_goods_public_7500_gvw_value.setText(b.getCharSequence("pp_com_goods_public_7500_gvw_value"));
        pp_com_goods_public_7500_date_value.setText(b.getCharSequence("pp_com_goods_public_7500_date_value"));
        pp_com_goods_public_7500_zone.setText(b.getCharSequence("pp_com_goods_public_7500_zone"));
        pp_com_goods_public_7500_nd_value.setText(b.getCharSequence("pp_com_goods_public_7500_nd_value"));
        pp_com_goods_public_7500_uwd_value.setText(b.getCharSequence("pp_com_goods_public_7500_uwd_value"));
        pp_com_goods_public_7500_ncb_value.setText(b.getCharSequence("pp_com_goods_public_7500_ncb_value"));
        pp_com_goods_public_7500_coolie_value.setText(b.getCharSequence("pp_com_goods_public_7500_coolie"));
        pp_com_goods_public_7500_nfpp_value.setText(b.getCharSequence("pp_com_goods_public_7500_nfpp"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Public Commercial Vehicle - Package Policy");

        int nfpp = Integer.parseInt(pp_com_goods_public_7500_nfpp_value.getText().toString());
        int coolie = Integer.parseInt(pp_com_goods_public_7500_coolie_value.getText().toString());
        nfpp = nfpp*75;
        coolie = coolie*50;
        pp_com_goods_public_7500_coolie_value.setText(String.valueOf(coolie));
        pp_com_goods_public_7500_nfpp_value.setText(String.valueOf(nfpp));

        findViewById(R.id.ppdisplay_com_goods_public_7500_home).setOnClickListener(this.listener_ppdisplay_com_goods_public_7500_home);
        share_btn = (CircleButton) findViewById(R.id.ppdisplay_com_goods_public_7500_share);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsChecker checker = new PermissionsChecker(ppdisplay_com_goods_public_7500.this);
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(ppdisplay_com_goods_public_7500.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);

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
        String s = b.getString("pp_com_goods_public_7500_date_value").toString();
        int y = b.getInt("year");
        int m = b.getInt("month");
        int old_day = b.getInt("day");
        int old_month = m;
        int old_year = y;

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        mYear = calendar1.get(Calendar.YEAR);
        mMonth = calendar1.get(Calendar.MONTH);
        mDay = calendar1.get(Calendar.DAY_OF_MONTH);

        // Set the values for the calendar fields YEAR, MONTH, and DAY_OF_MONTH.
        calendar1.set(mYear, mMonth, mDay);
        calendar2.set(old_year, old_month, old_day);
        long diffInMilis = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
        long diffInSecond = diffInMilis / 1000;
        long diffInMinute = diffInMilis / 60000;
        long diffInHour = diffInMilis / 3600000;
        this.diffInDays = diffInMilis / 86400000;
        if (b.getString("pp_com_goods_public_7500_zone").equals("A")) {
            if (this.diffInDays < 1825) {
                this.dop_value = (1.751d * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_idv_value")).intValue())) / 100.0d;
                this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
            } else if (this.diffInDays >= 1825) {
                if (this.diffInDays < 2555) {
                    this.dop_value = (1.795d * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                } else if (this.diffInDays >= 2555) {
                    this.dop_value = (1.839d * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                }
            }
        } else if (b.getString("pp_com_goods_public_7500_zone").equals("B")) {
            if (this.diffInDays < 1825) {
                this.dop_value = (1.743d * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_idv_value")).intValue())) / 100.0d;
                this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
            } else if (this.diffInDays >= 1825) {
                if (this.diffInDays < 2555) {
                    this.dop_value = (1.787d * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                } else if (this.diffInDays >= 2555) {
                    this.dop_value = (1.83d * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                }
            }
        } else if (b.getString("pp_com_goods_public_7500_zone").equals("C")) {
            if (this.diffInDays < 1825) {
                this.dop_value = (1.726d * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_idv_value")).intValue())) / 100.0d;
                this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
            } else if (this.diffInDays >= 1825) {
                if (this.diffInDays < 2555) {
                    this.dop_value = (1.77d * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                } else if (this.diffInDays >= 2555) {
                    this.dop_value = (1.812d * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                }
            }
        }
        double intermediate_after_gvw = (Math.ceil((double) (((float) (Integer.valueOf(b.getCharSequence("pp_com_goods_public_7500_gvw_value").toString()).intValue() - 0)) / 1.0f)) * 1.0d) + ((double) this.rounded_dop_value);
        double nd_value = (double) Integer.valueOf(b.getCharSequence("pp_com_goods_public_7500_nd_value").toString()).intValue();
        if (nd_value > 0.0d) {
            imt23_value = 15;
            pp_com_goods_public_7500_imt23_value.setText(String.valueOf(15));
        } else {
            imt23_value = 0;
            ((TableRow) findViewById(R.id.imt23_row)).setVisibility(View.INVISIBLE);
        }

        double imt23_total_value =  ((((double) imt23_value) * rounded_dop_value) / 100.0d);
        this.rounded_value_nd = new Float((float) Math.round(imt23_total_value ));
        int rounded_value_nd_int = (int) this.rounded_value_nd;
        Toast.makeText(getApplicationContext(), " DOP value :  " + rounded_dop_value,  Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), " IMT23 value :  " + rounded_value_nd,  Toast.LENGTH_SHORT).show();

        this.value_uw1 = (rounded_value_nd + rounded_dop_value) * (((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_uwd_value")).intValue()) / 100.0d);
        this.rounded_uw_value = new Float((float) Math.round(this.value_uw1)).floatValue();
        Toast.makeText(getApplicationContext(), " UW value :  " + rounded_uw_value,  Toast.LENGTH_SHORT).show();

        double ncb =  (rounded_dop_value + rounded_value_nd - rounded_uw_value);
        this.value_spin1 = new Float((float) Math.round(ncb));
        Toast.makeText(getApplicationContext(), " Spin value :  " + value_spin1,  Toast.LENGTH_SHORT).show();

        this.value_spin1 = ((double) this.value_spin1) - ((((double) this.value_spin1) * ((double) Integer.valueOf(b.getString("pp_com_goods_public_7500_ncb_value")).intValue())) / 100.0d);
        this.rounded_ncb_value = new Float((float) Math.round(this.value_spin1)).floatValue();
        ppdisplay_com_goods_public_7500_od_value.setText(String.valueOf((int) this.rounded_ncb_value));
        Toast.makeText(getApplicationContext(), " Spin value :  " + rounded_ncb_value,  Toast.LENGTH_SHORT).show();

        double total_b = (double) ((((Integer.valueOf("15746").intValue() + Integer.valueOf(b.getCharSequence("pp_com_goods_public_7500_paod_value").toString()).intValue()) + Integer.valueOf("50").intValue()) + (Integer.valueOf(b.getCharSequence("pp_com_goods_public_7500_coolie").toString()).intValue() * 50)) + (Integer.valueOf(b.getCharSequence("pp_com_goods_public_7500_nfpp").toString()).intValue() * 75));
        ppdisplay_com_goods_public_7500_b_value.setText(String.valueOf((int) total_b));
        double round_value = total_b + ((double) this.rounded_ncb_value);

        //To calculate 18% tax
        tax_18 = ((((round_value - 15746) * (double) Integer.valueOf("18").intValue())) / 100.0d);
        int tax_18_final = (int)Math.round(tax_18);
        ppdisplay_com_goods_public_7500_tax18_value.setText(String.valueOf(tax_18_final));

        //To calculate 12% tax
        tax_12 = 15746*0.12;
        int tax_12_final = (int)Math.round(tax_12);
        ppdisplay_com_goods_public_7500_tax12_value.setText(String.valueOf(tax_12_final));

        //int rounded_total_premium_ab_int = (int) new Float((float) Math.round(round_value + ((((double) Integer.valueOf("18").intValue()) * round_value) / 100.0d))).floatValue();
        int rounded_total_premium_ab_int = (int) new Float((float) Math.round(round_value + tax_18_final + tax_12_final)).floatValue();
        ppdisplay_com_goods_public_7500_ab_value.setText(String.valueOf((int) round_value));
        ppdisplay_com_goods_public_7500_total_value.setText(String.valueOf(rounded_total_premium_ab_int));
    }

    View.OnClickListener listener_ppdisplay_com_goods_public_7500_home = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ppdisplay_com_goods_public_7500.this, home_activity.class);
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
        checkfunction(ppdisplay_com_goods_public_7500.this);
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
            p.add(new Chunk(": COMMERCIAL GOODS VEHICLE PUBLIC"));

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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_public_7500_total_idv.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("G V W"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+pp_com_goods_public_7500_gvw_value.getText().toString()));
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
            p.add(new Chunk(pp_com_goods_public_7500_date_value.getText().toString()));
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
            p.add(new Chunk(pp_com_goods_public_7500_zone.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("IMT23"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(pp_com_goods_public_7500_imt23_value.getText().toString()));
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
            p.add(new Chunk(pp_com_goods_public_7500_nd_value.getText().toString()+"%"));
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
            p.add(new Chunk(pp_com_goods_public_7500_uwd_value.getText().toString()));
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
            p.add(new Chunk(pp_com_goods_public_7500_ncb_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_public_7500_od_value.getText().toString(),white));
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
            p.add(new Chunk("Rs. 15746"));
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
            p.add(new Chunk("Rs. "+"320"));
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
            p.add(new Chunk("Rs. "+pp_com_goods_public_7500_coolie_value.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("NFPP"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+pp_com_goods_public_7500_nfpp_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_public_7500_b_value.getText().toString(),white));
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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_public_7500_ab_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_public_7500_total_value.getText().toString(),white));
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

            Uri uri = FileProvider.getUriForFile(ppdisplay_com_goods_public_7500.this, BuildConfig.APPLICATION_ID + ".provider",file);
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
