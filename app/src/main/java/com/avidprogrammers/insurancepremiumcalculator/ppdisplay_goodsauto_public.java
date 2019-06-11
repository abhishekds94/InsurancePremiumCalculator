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
import java.util.Date;

import at.markushi.ui.CircleButton;

import static com.avidprogrammers.utils.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.avidprogrammers.utils.PermissionsChecker.REQUIRED_PERMISSION;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class ppdisplay_goodsauto_public extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    public double  total_premium;
    public double  imt23;
    double rounded_imt23_value;

    CircleButton share_btn;
    File file;

    TextView pp_goodsauto_public_IDV_value;
    TextView pp_goodsauto_public_date_value;
    TextView pp_goodsauto_public_zone;
    TextView pp_goodsauto_public_cc_value;
    TextView pp_goodsauto_public_nd_value;
    TextView pp_goodsauto_public_uwd_value;
    TextView goodsauto_public_nfpp;
    TextView goodsauto_public_coolie;
    TextView goodsauto_public_imt23;
    TextView pp_goodsauto_public_ncb_value;
    TextView pp_goodsauto_public_od_value;
    TextView pp_goodsauto_public_b_value;
    TextView pp_goodsauto_public_ab_value;
    TextView pp_goodsauto_public_paod_value;
    TextView pp_goodsauto_public_total_value;


    TextView pp_goodsauto_public_lpg;
    TextView pp_goodsauto_public_lpgtype;
    TextView pp_goodsauto_public_lpgkit_value;

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
        checkfunction(ppdisplay_goodsauto_public.this);

        setContentView(R.layout.ppdisplay_goodsauto_public);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Bundle b = getIntent().getExtras();
         pp_goodsauto_public_IDV_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_IDV_value);
         pp_goodsauto_public_date_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_date_value);
         pp_goodsauto_public_zone = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_zone_value);
         pp_goodsauto_public_cc_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_cc_value);
         pp_goodsauto_public_nd_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_nd_value);
         pp_goodsauto_public_uwd_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_uwd_value);
         goodsauto_public_nfpp = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_nfpp_value);
         goodsauto_public_coolie = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_coolie_value);
         pp_goodsauto_public_ncb_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_ncb_value);
         pp_goodsauto_public_od_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_od_value);
         pp_goodsauto_public_b_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_b_value);
         pp_goodsauto_public_ab_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_ab_value);
         pp_goodsauto_public_paod_value = findViewById(R.id.ppdisplay_goodsauto_public_pa_value);
         pp_goodsauto_public_total_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_total_value);
         goodsauto_public_imt23 = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_imt23_value);


         pp_goodsauto_public_lpg = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_lpg_value);
         pp_goodsauto_public_lpgtype = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_lpgtype_value);
         pp_goodsauto_public_lpgkit_value = (TextView) findViewById(R.id.ppdisplay_goodsauto_public_lpgkit_value);




        pp_goodsauto_public_IDV_value.setText(b.getCharSequence("pp_goodsauto_public_idv_value"));
        pp_goodsauto_public_date_value.setText(b.getCharSequence("pp_goodsauto_public_date_value"));
        pp_goodsauto_public_zone.setText(b.getCharSequence("pp_goodsauto_public_zone"));
        pp_goodsauto_public_cc_value.setText(b.getCharSequence("pp_goodsauto_public_cc_value"));
        pp_goodsauto_public_nd_value.setText(b.getCharSequence("pp_goodsauto_public_nd_value"));
        pp_goodsauto_public_uwd_value.setText(b.getCharSequence("pp_goodsauto_public_uwd_value"));
        goodsauto_public_nfpp.setText(b.getCharSequence("pp_goodsauto_public_nfpp"));
        goodsauto_public_coolie.setText(b.getCharSequence("pp_goodsauto_public_coolie"));

        pp_goodsauto_public_lpg.setText(b.getCharSequence("pp_goodsauto_public_lpg"));
        pp_goodsauto_public_paod_value.setText(b.getCharSequence("pp_goodsauto_public_paod_value"));
        pp_goodsauto_public_lpgtype.setText(b.getCharSequence("pp_goodsauto_public_lpgtype"));

        //if cng is no then cng  type = "-"
        if(b.getString(("lpgtype_value")).equals("-"))
        {
            pp_goodsauto_public_lpgtype.setText("-");
        }
        else
        {
            pp_goodsauto_public_lpgtype.setText(b.getCharSequence("pp_goodsauto_public_lpgtype"));
        }

        pp_goodsauto_public_ncb_value.setText(b.getCharSequence("pp_goodsauto_public_ncb_value"));


        Long diff_days = b.getLong("diff_in_days");


        //function to calculate IDv using DOP
        double val_idv = calculateIdv(diff_days,pp_goodsauto_public_zone.getText().toString());
        total_premium = Long.parseLong(pp_goodsauto_public_IDV_value.getText().toString())*val_idv*0.01;

        //for N/D and imt23
        int nd_val = Integer.parseInt(pp_goodsauto_public_nd_value.getText().toString());
        if(nd_val!=0)
        {
            goodsauto_public_imt23.setText("15");
            imt23 = total_premium*15*0.01;
            double value_imt23=imt23;
            rounded_imt23_value = new Float(Math.round(value_imt23));
            //Toast.makeText(getApplicationContext(), " IMT23 value :  " + rounded_imt23_value,  Toast.LENGTH_SHORT).show();
        }
        else goodsauto_public_imt23.setText("0");

        total_premium =total_premium +  (total_premium*nd_val*0.01);

        //function to calculate cng value and type
        int is_cng = check_cng(pp_goodsauto_public_lpg.getText().toString());
        int yes=60,no=0;
        if(is_cng==0) pp_goodsauto_public_lpgkit_value.setText(String.valueOf(no));
        else if(is_cng == 1){
            pp_goodsauto_public_lpgkit_value.setText(String.valueOf(yes));
            if(pp_goodsauto_public_lpgtype.getText().toString().equals("Fixed")){
                int val = Integer.parseInt(b.getString("lpgtype_value"));
                total_premium =total_premium + (val*4*0.01);
            }
        }



        //For UW discount
        int uwd_val = Integer.parseInt(pp_goodsauto_public_uwd_value.getText().toString());
        total_premium = total_premium - (total_premium*uwd_val*0.01);
        //Toast.makeText(getApplicationContext(), " U/W value :  " + total_premium,  Toast.LENGTH_SHORT).show();

        //For NCB
        int ncb_val = Integer.parseInt(pp_goodsauto_public_ncb_value.getText().toString());
        total_premium = total_premium - (total_premium*ncb_val*0.01);

        //display OD total (A)
        int final_premium = (int)Math.round(total_premium);    // for rounding up till 2 decimal places
        pp_goodsauto_public_od_value.setText(String.valueOf(final_premium));

        //Toast.makeText(getApplicationContext(), " Total Premium value :  " + total_premium,  Toast.LENGTH_SHORT).show();

        //nfpp and coolie
        int nfpp = Integer.parseInt(goodsauto_public_nfpp.getText().toString());
        int coolie = Integer.parseInt(goodsauto_public_coolie.getText().toString());
        nfpp = nfpp*75;
        coolie = coolie*50;
        goodsauto_public_coolie.setText(String.valueOf(coolie));
        goodsauto_public_nfpp.setText(String.valueOf(nfpp));

        //display Total (B)
        int ba;
        if(is_cng==1) ba=60;
        else ba=0;
        int b_total = 4092 + Integer.parseInt(pp_goodsauto_public_paod_value.getText().toString()) + 50 +ba +nfpp + coolie ;
        pp_goodsauto_public_b_value.setText(String.valueOf(b_total));

        //display A+B
        double ab = Double.parseDouble(pp_goodsauto_public_od_value.getText().toString())+ Double.parseDouble(pp_goodsauto_public_b_value.getText().toString());
        final_premium = (int)Math.round(ab);    // for rounding up till 2 decimal places
        pp_goodsauto_public_ab_value.setText(String.valueOf(final_premium));

        total_premium = ab;
        total_premium +=total_premium*0.18;


        //Display final total premium
        final_premium = (int)Math.round(total_premium);    // for rounding up till 2 decimal places
        pp_goodsauto_public_total_value.setText(String.valueOf(final_premium));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Public Goods Auto Package Policy");

        findViewById(R.id.ppdisplay_goodsauto_public_home).setOnClickListener(listener_ppdisplay_goodsauto_public_home);

        share_btn = (CircleButton) findViewById(R.id.ppdisplay_goodsauto_public_share);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsChecker checker = new PermissionsChecker(ppdisplay_goodsauto_public.this);
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(ppdisplay_goodsauto_public.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);

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
    }

    int check_cng(String cng){
        if(cng.equals("Yes")){
            return 1;
        }
        else {
            return 0;
        }
    }
    double calculateIdv(Long diff_in_days,String zone){
        if(zone.equals("A")){
            if(diff_in_days < 1825)
            {
                return 1.664;
            }
            else if(diff_in_days <=2555)
            {
                return 1.706;
            }
            else
            {
                return 1.747;
            }
        }
        else if(zone.equals("B")){
            if(diff_in_days < 1825)
            {
                return 1.656;
            }
            else if(diff_in_days <=2555)
            {
                return 1.697;
            }
            else
            {
                return 1.739;
            }
        }
        else if(zone.equals("C")){
            if(diff_in_days < 1825)
            {
                return 1.208;
            }
            else if(diff_in_days <=2555)
            {
                return 1.292;
            }
            else
            {
                return 1.323;
            }
        }
        return 0;
    }

    View.OnClickListener listener_ppdisplay_goodsauto_public_home = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ppdisplay_goodsauto_public.this, home_activity.class);
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
        checkfunction(ppdisplay_goodsauto_public.this);
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
            p.add(new Chunk(": GOODS AUTO PUBLIC"));

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
            p.add(new Chunk("Rs. "+pp_goodsauto_public_IDV_value.getText().toString()));
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
            p.add(new Chunk(pp_goodsauto_public_date_value.getText().toString()));
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
            p.add(new Chunk(pp_goodsauto_public_zone.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("TYPE"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Public Goods Auto"));
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
            p.add(new Chunk(goodsauto_public_imt23.getText().toString()+"%"));
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
            p.add(new Chunk(pp_goodsauto_public_nd_value.getText().toString()+"%"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("CNG"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(pp_goodsauto_public_lpg.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("CNG Type"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(pp_goodsauto_public_lpgtype.getText().toString()));
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
            p.add(new Chunk(pp_goodsauto_public_uwd_value.getText().toString()));
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
            p.add(new Chunk(pp_goodsauto_public_ncb_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+pp_goodsauto_public_od_value.getText().toString(),white));
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
            p.add(new Chunk("Rs. "+"4092"));
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
            p.add(new Chunk("Rs. "+pp_goodsauto_public_paod_value.getText().toString()));
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
            p.add(new Chunk("NFPP"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+goodsauto_public_nfpp.getText().toString()));
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
            p.add(new Chunk("Rs. "+goodsauto_public_coolie.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("CNG Kit"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+pp_goodsauto_public_lpgkit_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+pp_goodsauto_public_b_value.getText().toString(),white));
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
            p.add(new Chunk("Rs. "+pp_goodsauto_public_ab_value.getText().toString()));
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
            p.add(new Chunk("Rs. "+pp_goodsauto_public_total_value.getText().toString(),white));
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

            Uri uri = FileProvider.getUriForFile(ppdisplay_goodsauto_public.this, BuildConfig.APPLICATION_ID + ".provider",file);
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
