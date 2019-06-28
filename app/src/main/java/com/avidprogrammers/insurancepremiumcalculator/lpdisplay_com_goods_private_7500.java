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

public class lpdisplay_com_goods_private_7500 extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;

    int lp_com_goods_private_7500_value,lp_com_goods_private_7500_paod_value,lp_com_goods_private_7500_lld_value,lp_com_goods_private_7500_coolie_value,lp_com_goods_private_7500_nfpp_value,lp_com_goods_private_7500_tax_value;
    int lp_com_goods_private_7500;
    double total_premium;
    CircleButton share_btn;
    File file;
    private double tax_18;
    private double tax_12;

    TextView lp_com_goods_private_7500_act;
    TextView lp_com_goods_private_7500_paod;
    TextView lp_com_goods_private_7500_ll;
    TextView lp_com_goods_private_7500_tax;
    TextView lp_com_goods_private_7500_coolie;
    TextView lp_com_goods_private_7500_nfpp;
    TextView lp_com_goods_private_7500_total;
    TextView lp_com_goods_private_7500_tax_12;
    TextView lp_com_goods_private_7500_tax_18;

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
        checkfunction(lpdisplay_com_goods_private_7500.this);

        setContentView(R.layout.lpdisplay_com_goods_private_7500);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle b = getIntent().getExtras();
         lp_com_goods_private_7500_paod = (TextView) findViewById(R.id.lpdisplay_com_goods_private_7500_paod_value);
         lp_com_goods_private_7500_ll = (TextView) findViewById(R.id.lpdisplay_com_goods_private_7500_ll_value);
         //lp_com_goods_private_7500_tax = (TextView) findViewById(R.id.lpdisplay_com_goods_private_7500_tax_value);
         lp_com_goods_private_7500_coolie = (TextView) findViewById(R.id.lpdisplay_com_goods_private_7500_coolie_value);
         lp_com_goods_private_7500_nfpp = (TextView) findViewById(R.id.lpdisplay_com_goods_private_7500_nfpp_value);
         lp_com_goods_private_7500_tax_12 = (TextView) findViewById(R.id.lpdisplay_com_goods_private_7500_tax12_value);
         lp_com_goods_private_7500_tax_18 = (TextView) findViewById(R.id.lpdisplay_com_goods_private_7500_tax18_value);
         lp_com_goods_private_7500_total = (TextView) findViewById(R.id.lpdisplay_com_goods_private_7500_total_value);
        lp_com_goods_private_7500_act = ((TextView) findViewById(R.id.lpdisplay_com_goods_private_7500_act_value));
        lp_com_goods_private_7500_act.setText(b.getCharSequence("lp_com_goods_private_7500_act"));
        lp_com_goods_private_7500_paod.setText(b.getCharSequence("lp_com_goods_private_7500_paod"));
        lp_com_goods_private_7500_ll.setText(b.getCharSequence("lp_com_goods_private_7500_ll"));
        //lp_com_goods_private_7500_tax.setText(b.getCharSequence("lp_com_goods_private_7500_tax"));
        lp_com_goods_private_7500_coolie.setText(String.valueOf(Integer.valueOf(b.getCharSequence("lp_com_goods_private_7500_coolie").toString()).intValue() * 50));
        lp_com_goods_private_7500_nfpp.setText(String.valueOf(Integer.valueOf(b.getCharSequence("lp_com_goods_private_7500_nfpp").toString()).intValue() * 75));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Private Commercial Vehicle - Liability Policy");
        findViewById(R.id.lpdisplay_com_goods_private_7500_home).setOnClickListener(this.listener_lpdisplay_com_goods_private_7500_home);
        share_btn = (CircleButton) findViewById(R.id.lpdisplay_com_goods_private_7500_share);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsChecker checker = new PermissionsChecker(lpdisplay_com_goods_private_7500.this);
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(lpdisplay_com_goods_private_7500.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);

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
        String act_val = b.getCharSequence("lp_com_goods_private_7500_act").toString();
        String paod_value = b.getCharSequence("lp_com_goods_private_7500_paod").toString();
        String lld_value = b.getCharSequence("lp_com_goods_private_7500_ll").toString();
        String coolie_value = b.getCharSequence("lp_com_goods_private_7500_coolie").toString();
        String nfpp_value = b.getCharSequence("lp_com_goods_private_7500_nfpp").toString();
        //String tax_value = b.getCharSequence("lp_com_goods_private_7500_tax").toString();
        this.lp_com_goods_private_7500_value = Integer.valueOf(act_val).intValue();
        this.lp_com_goods_private_7500_paod_value = Integer.valueOf(paod_value).intValue();
        this.lp_com_goods_private_7500_lld_value = Integer.valueOf(lld_value).intValue();
        this.lp_com_goods_private_7500_coolie_value = Integer.valueOf(coolie_value).intValue();
        this.lp_com_goods_private_7500_nfpp_value = Integer.valueOf(nfpp_value).intValue();
        //this.lp_com_goods_private_7500_tax_value = Integer.valueOf(tax_value).intValue();
        double total = (double) ((((this.lp_com_goods_private_7500_value + this.lp_com_goods_private_7500_paod_value) + this.lp_com_goods_private_7500_lld_value) + (this.lp_com_goods_private_7500_coolie_value * 50)) + (this.lp_com_goods_private_7500_nfpp_value * 75));

        //To calculate 18% tax
        tax_18 = (total-8438)*0.18;
        int tax_18_final = (int)Math.round(tax_18);
        lp_com_goods_private_7500_tax_18.setText(String.valueOf(tax_18_final));

        //To calculate 12% tax
        tax_12 = 8438*0.12;
        int tax_12_final = (int)Math.round(tax_12);
        lp_com_goods_private_7500_tax_12.setText(String.valueOf(tax_12_final));

        this.total_premium = tax_18_final + tax_12_final + total;
        lp_com_goods_private_7500_total.setText(String.valueOf((int) new Float((float) Math.round(this.total_premium)).floatValue()));
    }

    View.OnClickListener listener_lpdisplay_com_goods_private_7500_home = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lpdisplay_com_goods_private_7500.this, home_activity.class);
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
            p.add(new Chunk("Rs. "+lp_com_goods_private_7500_act.getText().toString()));
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
            p.add(new Chunk("Rs. "+lp_com_goods_private_7500_paod.getText().toString()));
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
            p.add(new Chunk("Rs. "+lp_com_goods_private_7500_ll.getText().toString()));
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
            p.add(new Chunk(lp_com_goods_private_7500_coolie.getText().toString()));
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
            p.add(new Chunk(lp_com_goods_private_7500_nfpp.getText().toString()));
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
            p.add(new Chunk("Rs. "+lp_com_goods_private_7500_total.getText().toString(),white));
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

            Uri uri = FileProvider.getUriForFile(lpdisplay_com_goods_private_7500.this, BuildConfig.APPLICATION_ID + ".provider",file);
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
