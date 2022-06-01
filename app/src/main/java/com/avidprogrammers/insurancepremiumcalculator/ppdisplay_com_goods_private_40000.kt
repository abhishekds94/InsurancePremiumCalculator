package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.core.content.FileProvider;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

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

public class ppdisplay_com_goods_private_40000 extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

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
    CircleButton share_btn;
    File file;
    TextView ppdisplay_com_goods_private_40000_total_idv;
    TextView pp_com_goods_private_40000_gvw_value;
    TextView pp_com_goods_private_40000_date_value;
    TextView pp_com_goods_private_40000_zone;
    TextView pp_com_goods_private_40000_imt23;
    TextView pp_com_goods_private_40000_imt23_value;
    TextView pp_com_goods_private_40000_nd_value;
    TextView pp_com_goods_private_40000_uwd_value;
    TextView pp_com_goods_private_40000_ncb_value;
    TextView pp_com_goods_private_40000_coolie_value;
    TextView pp_com_goods_private_40000_nfpp_value;
    TextView pp_com_goods_private_40000_paod_value;
    TextView ppdisplay_com_goods_private_40000_od_value;
    TextView ppdisplay_com_goods_private_40000_b_value;
    TextView ppdisplay_com_goods_private_40000_ab_value;
    TextView ppdisplay_com_goods_private_40000_tax18_value;
    TextView ppdisplay_com_goods_private_40000_tax12_value;
    TextView ppdisplay_com_goods_private_40000_total_value;

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
        checkfunction(ppdisplay_com_goods_private_40000.this);

        setContentView(R.layout.ppdisplay_com_goods_private_40000);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        Bundle b = getIntent().getExtras();
         pp_com_goods_private_40000_gvw_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_gvw_value);
         pp_com_goods_private_40000_date_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_date_value);
         pp_com_goods_private_40000_zone = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_zone_value);
         pp_com_goods_private_40000_imt23 = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_imt23);
         pp_com_goods_private_40000_imt23_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_imt23_value);
         pp_com_goods_private_40000_nd_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_nd_value);
         pp_com_goods_private_40000_uwd_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_uwd_value);
         pp_com_goods_private_40000_ncb_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_ncb_value);
         pp_com_goods_private_40000_coolie_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_coolie_value);
         pp_com_goods_private_40000_nfpp_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_nfpp_value);
         pp_com_goods_private_40000_paod_value = findViewById(R.id.ppdisplay_com_goods_private_40000_pa_value);

         ppdisplay_com_goods_private_40000_od_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_od_value);
         ppdisplay_com_goods_private_40000_b_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_b_value);
         ppdisplay_com_goods_private_40000_ab_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_ab_value);
        ppdisplay_com_goods_private_40000_tax18_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_tax18_value);
        ppdisplay_com_goods_private_40000_tax12_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_tax12_value);
        ppdisplay_com_goods_private_40000_total_value = (TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_total_value);
        ppdisplay_com_goods_private_40000_total_idv = ((TextView) findViewById(R.id.ppdisplay_com_goods_private_40000_IDV_value));
        ppdisplay_com_goods_private_40000_total_idv.setText(b.getCharSequence("pp_com_goods_private_40000_idv_value"));
        pp_com_goods_private_40000_gvw_value.setText(b.getCharSequence("pp_com_goods_private_40000_gvw_value"));
        pp_com_goods_private_40000_date_value.setText(b.getCharSequence("pp_com_goods_private_40000_date_value"));
        pp_com_goods_private_40000_zone.setText(b.getCharSequence("pp_com_goods_private_40000_zone"));
        pp_com_goods_private_40000_paod_value.setText(b.getCharSequence("pp_com_goods_private_40000_paod_value"));
        pp_com_goods_private_40000_nd_value.setText(b.getCharSequence("pp_com_goods_private_40000_nd_value"));
        pp_com_goods_private_40000_uwd_value.setText(b.getCharSequence("pp_com_goods_private_40000_uwd_value"));
        pp_com_goods_private_40000_ncb_value.setText(b.getCharSequence("pp_com_goods_private_40000_ncb_value"));
        pp_com_goods_private_40000_coolie_value.setText(String.valueOf(Integer.valueOf(b.getCharSequence("pp_com_goods_private_40000_coolie").toString()).intValue() * 50));
        pp_com_goods_private_40000_nfpp_value.setText(String.valueOf(Integer.valueOf(b.getCharSequence("pp_com_goods_private_40000_nfpp").toString()).intValue() * 75));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Private Commercial Vehicle - Package Policy");
        findViewById(R.id.ppdisplay_com_goods_private_40000_home).setOnClickListener(this.listener_ppdisplay_com_goods_private_40000_home);
        share_btn = (CircleButton) findViewById(R.id.ppdisplay_com_goods_private_40000_share);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsChecker checker = new PermissionsChecker(ppdisplay_com_goods_private_40000.this);
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(ppdisplay_com_goods_private_40000.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);

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
        String s = b.getString("pp_com_goods_private_40000_date_value").toString();
        int y = b.getInt("year");
        int m = b.getInt("month");
        int old_day = b.getInt("day");
        int old_month = m;
        int old_year = y;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int mYear,mMonth, mDay;
        mYear = calendar1.get(Calendar.YEAR);
        mMonth = calendar1.get(Calendar.MONTH);
        mDay = calendar1.get(Calendar.DAY_OF_MONTH);
        calendar1.set(mYear, mMonth, mDay);
        calendar2.set(old_year, old_month, old_day);
        long diffInMilis = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
        long diffInSecond = diffInMilis / 1000;
        long diffInMinute = diffInMilis / 60000;
        long diffInHour = diffInMilis / 3600000;
        this.diffInDays = diffInMilis / 86400000;
        if (b.getString("pp_com_goods_private_40000_zone").equals("A")) {
            if (this.diffInDays < 1825) {
                this.dop_value = (1.226d * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_idv_value")).intValue())) / 100.0d;
                this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
            } else if (this.diffInDays >= 1825) {
                if (this.diffInDays < 2555) {
                    this.dop_value = (1.257d * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                } else if (this.diffInDays >= 2555) {
                    this.dop_value = (1.287d * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                }
            }
        } else if (b.getString("pp_com_goods_private_40000_zone").equals("B")) {
            if (this.diffInDays < 1825) {
                this.dop_value = (1.22d * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_idv_value")).intValue())) / 100.0d;
                this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
            } else if (this.diffInDays >= 1825) {
                if (this.diffInDays < 2555) {
                    this.dop_value = (1.251d * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                } else if (this.diffInDays >= 2555) {
                    this.dop_value = (1.281d * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                }
            }
        } else if (b.getString("pp_com_goods_private_40000_zone").equals("C")) {

            if (this.diffInDays < 1825) {
                this.dop_value = (1.208d * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_idv_value")).intValue())) / 100.0d;
                this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
            } else if (this.diffInDays >= 1825) {
                if (this.diffInDays < 2555) {
                    this.dop_value = (1.239d * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                } else if (this.diffInDays >= 2555) {
                    this.dop_value = (1.268d * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_idv_value")).intValue())) / 100.0d;
                    this.rounded_dop_value = new Float((float) Math.round(this.dop_value)).floatValue();
                }
            }
        }
        double intermediate_after_gvw = (Math.ceil((double) (((float) (Integer.valueOf(b.getCharSequence("pp_com_goods_private_40000_gvw_value").toString()).intValue() - 20001)) / 100.0f)) * 27.0d) + ((double) this.rounded_dop_value);
        double nd_value = (double) Integer.valueOf(b.getCharSequence("pp_com_goods_private_40000_nd_value").toString()).intValue();
        if (nd_value > 0.0d) {
            imt23_value = 15;
            pp_com_goods_private_40000_imt23_value.setText(String.valueOf(15));
        } else {
            imt23_value = 0;
            ((TableRow) findViewById(R.id.imt23_row)).setVisibility(View.INVISIBLE);
        }
        double imt23_total_value = intermediate_after_gvw + ((((double) imt23_value) * intermediate_after_gvw) / 100.0d);
        this.rounded_value_nd = new Float((float) Math.round(imt23_total_value + ((nd_value * imt23_total_value) / 100.0d))).floatValue();
        int rounded_value_nd_int = (int) this.rounded_value_nd;
        pp_com_goods_private_40000_nd_value.setText(String.valueOf(imt23_total_value ));

        this.value_uw1 = ((double) this.rounded_value_nd) - ((((double) this.rounded_value_nd) * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_uwd_value")).intValue())) / 100.0d);
        this.rounded_uw_value = new Float((float) Math.round(this.value_uw1)).floatValue();
        pp_com_goods_private_40000_uwd_value.setText(String.valueOf(rounded_uw_value));

        this.value_spin1 = ((double) this.rounded_uw_value) - ((((double) this.rounded_uw_value) * ((double) Integer.valueOf(b.getString("pp_com_goods_private_40000_ncb_value")).intValue())) / 100.0d);
        this.rounded_ncb_value = new Float((float) Math.round(this.value_spin1)).floatValue();

        ppdisplay_com_goods_private_40000_od_value.setText(String.valueOf((int) this.rounded_ncb_value));
        double total_b = (double) ((((Integer.valueOf("17476").intValue() + Integer.valueOf(b.getCharSequence("pp_com_goods_private_40000_paod_value").toString()).intValue()) + Integer.valueOf("50").intValue()) + (Integer.valueOf(b.getCharSequence("pp_com_goods_private_40000_coolie").toString()).intValue() * 50)) + (Integer.valueOf(b.getCharSequence("pp_com_goods_private_40000_nfpp").toString()).intValue() * 75));
        ppdisplay_com_goods_private_40000_b_value.setText(String.valueOf((int) total_b));
        double round_value = total_b + ((double) this.rounded_ncb_value);

        //To calculate 18% tax
        tax_18 = ((((round_value - 17476) * (double) Integer.valueOf("18").intValue())) / 100.0d);
        int tax_18_final = (int)Math.round(tax_18);
        ppdisplay_com_goods_private_40000_tax18_value.setText(String.valueOf(tax_18_final));

        //To calculate 12% tax
        tax_12 = 17476*0.12;
        int tax_12_final = (int)Math.round(tax_12);
        ppdisplay_com_goods_private_40000_tax12_value.setText(String.valueOf(tax_12_final));

        //int rounded_total_premium_ab_int = (int) new Float((float) Math.round(round_value + ((((double) Integer.valueOf("18").intValue()) * round_value) / 100.0d))).floatValue();
        int rounded_total_premium_ab_int = (int) new Float((float) Math.round(round_value + tax_18_final + tax_12_final)).floatValue();
        ppdisplay_com_goods_private_40000_ab_value.setText(String.valueOf((int) round_value));
        ppdisplay_com_goods_private_40000_total_value.setText(String.valueOf(rounded_total_premium_ab_int));
    }

    View.OnClickListener listener_ppdisplay_com_goods_private_40000_home = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ppdisplay_com_goods_private_40000.this, home_activity.class);
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
        checkfunction(ppdisplay_com_goods_private_40000.this);
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
            p.add(new Chunk(": COMMERCIAL GOODS VEHICLE PRIVATE"));

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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_private_40000_total_idv.getText().toString()));
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
            p.add(new Chunk("Rs. "+pp_com_goods_private_40000_gvw_value.getText().toString()));
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
            p.add(new Chunk(pp_com_goods_private_40000_date_value.getText().toString()));
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
            p.add(new Chunk(pp_com_goods_private_40000_zone.getText().toString()));
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
            p.add(new Chunk(pp_com_goods_private_40000_imt23_value.getText().toString()));
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
            p.add(new Chunk(pp_com_goods_private_40000_nd_value.getText().toString()+"%"));
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
            p.add(new Chunk(pp_com_goods_private_40000_uwd_value.getText().toString()));
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
            p.add(new Chunk(pp_com_goods_private_40000_ncb_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_private_40000_od_value.getText().toString(),white));
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
            p.add(new Chunk("Rs. 17476"));
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
            p.add(new Chunk("Rs. "+pp_com_goods_private_40000_paod_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+pp_com_goods_private_40000_coolie_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+pp_com_goods_private_40000_nfpp_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_private_40000_b_value.getText().toString(),white));
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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_private_40000_ab_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+ppdisplay_com_goods_private_40000_total_value.getText().toString(),white));
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

            Uri uri = FileProvider.getUriForFile(ppdisplay_com_goods_private_40000.this, BuildConfig.APPLICATION_ID + ".provider",file);
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
