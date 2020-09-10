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
import java.util.Date;

import at.markushi.ui.CircleButton;

import static com.avidprogrammers.utils.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.avidprogrammers.utils.PermissionsChecker.REQUIRED_PERMISSION;

/**
 * Created by Abhishek on 26-Mar-17.
 */

public class lpdisplay_car_upto1000 extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    int lp_act_value,lp_paod_value,lp_ll_value,lp_tax_value;
    int lp_lgkit_assumevalue;
    double total_premium;

    CircleButton shareBtn;
    File file;

    TextView lp_car_upto1000_act;
    TextView lp_car_upto1000_paod;
    TextView lp_car_upto1000_ll;
    TextView lp_car_upto1000_tax;

    TextView lp_car_upto1000_lpgkit;
    TextView lp_car_upto1000_total;

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
        checkfunction(lpdisplay_car_upto1000.this);

        setContentView(R.layout.lpdisplay_car_upto1000);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        Bundle b = getIntent().getExtras();
         lp_car_upto1000_act = (TextView) findViewById(R.id.lpdisplay_car_upto1000_act_value);
         lp_car_upto1000_paod = (TextView) findViewById(R.id.lpdisplay_car_upto1000_paod_value);
         lp_car_upto1000_ll = (TextView) findViewById(R.id.lpdisplay_car_upto1000_ll_value);
         lp_car_upto1000_tax = (TextView) findViewById(R.id.lpdisplay_car_upto1000_tax_value);

         lp_car_upto1000_lpgkit = (TextView) findViewById(R.id.lpdisplay_car_upto1000_lpgkit_value);
         lp_car_upto1000_total = (TextView) findViewById(R.id.lpdisplay_car_upto1000_total_value);


        lp_car_upto1000_act.setText(b.getCharSequence("lp_car_upto1000_act"));
        lp_car_upto1000_paod.setText(b.getCharSequence("lp_car_upto1000_paod"));
        lp_car_upto1000_ll.setText(b.getCharSequence("lp_car_upto1000_ll"));
        lp_car_upto1000_tax.setText(b.getCharSequence("lp_car_upto1000_tax"));

        lp_car_upto1000_lpgkit.setText(b.getCharSequence("lp_car_upto1000_lpgkit"));



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Car Liability Policy");

        shareBtn = (CircleButton) findViewById(R.id.lpdisplay_car_upto1000_share);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsChecker checker = new PermissionsChecker(lpdisplay_car_upto1000.this);
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(lpdisplay_car_upto1000.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);

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

        String radio_button_value=b.getString("lp_car_upto1000_lpgkit");
        String yes=new String("Yes");

        //Setting LPGkit values for radiobutton selected
        if(radio_button_value.equals(yes)){
            lp_lgkit_assumevalue=60;


        }
        else{
            lp_lgkit_assumevalue=0;
        }
        //Getting values from extras and converting into Integer values
        lp_act_value= Integer.valueOf(b.getString("lp_car_upto1000_act"));
        lp_paod_value= Integer.valueOf(b.getString("lp_car_upto1000_paod"));
        lp_ll_value= Integer.valueOf(b.getString("lp_car_upto1000_ll"));
        lp_tax_value= Integer.valueOf(b.getString("lp_car_upto1000_tax"));

        //Calculation of Total by adding LG Kit Value of 60 if Yes 0 if No
        double total=lp_act_value+lp_paod_value+lp_ll_value+lp_lgkit_assumevalue;
        total_premium = total+total*lp_tax_value/100;

        //rounding of total_premium value
        double round_value=total_premium;
        float rounded_total_premium=new Float(Math.round(round_value));
        int rounded_total_premium_int = (int) rounded_total_premium;
        lp_car_upto1000_total.setText(String.valueOf(rounded_total_premium_int));



        findViewById(R.id.lpdisplay_car_upto1000_home).setOnClickListener(listener_lpdisplay_car_upto1000_home);
    }


    View.OnClickListener listener_lpdisplay_car_upto1000_home = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lpdisplay_car_upto1000.this, home_activity.class);
            startActivity(intent);
        }
    };

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

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
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
            p.add(new Chunk(": LIABILITY POLICY"));
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
            p.add(new Chunk("Act / Liability"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lp_car_upto1000_act.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("PA to Owner / Driver"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("Rs. "+lp_car_upto1000_paod.getText().toString()));
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
            p.add(new Chunk("Rs. "+lp_car_upto1000_ll.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("L P G Kit"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lp_car_upto1000_lpgkit.getText().toString()));
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
            p.add(new Chunk("Rs. "+lp_car_upto1000_total.getText().toString(),white));
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

            Uri uri = FileProvider.getUriForFile(lpdisplay_car_upto1000.this, BuildConfig.APPLICATION_ID + ".provider",file);
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
