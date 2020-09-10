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

public class lpdisplay_goodsauto_public extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private AdView mAdView;
    CircleButton share_btn;
    File file;
    TextView lp_goodsauto_public_act;
    TextView lp_goodsauto_public_paod;
    TextView lp_goodsauto_public_ll;
    TextView lp_goodsauto_public_tax;
    TextView lp_goodsauto_public_coolie;
    TextView lp_goodsauto_public_nfpp;
    TextView lp_goodsauto_public_tax_12;
    TextView lp_goodsauto_public_tax_18;
    private double tax_12;
    private double tax_18;
    TextView lp_goodsauto_public_lpgkit,total_premium;

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
        checkfunction(lpdisplay_goodsauto_public.this);

        setContentView(R.layout.lpdisplay_goodsauto_public);

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        Bundle b = getIntent().getExtras();
         lp_goodsauto_public_act = (TextView) findViewById(R.id.lpdisplay_goodsauto_public_act_value);
         lp_goodsauto_public_paod = (TextView) findViewById(R.id.lpdisplay_goodsauto_public_paod_value);
         lp_goodsauto_public_ll = (TextView) findViewById(R.id.lpdisplay_goodsauto_public_ll_value);
         //lp_goodsauto_public_tax = (TextView) findViewById(R.id.lpdisplay_goodsauto_public_tax_value);
         lp_goodsauto_public_coolie = (TextView) findViewById(R.id.lpdisplay_goodsauto_public_coolie_value);
         lp_goodsauto_public_nfpp = (TextView) findViewById(R.id.lpdisplay_goodsauto_public_nfpp_value);
        lp_goodsauto_public_tax_12 = (TextView) findViewById(R.id.lpdisplay_goodsauto_public_tax12_value);
        lp_goodsauto_public_tax_18 = (TextView) findViewById(R.id.lpdisplay_goodsauto_public_tax18_value);

         lp_goodsauto_public_lpgkit = (TextView) findViewById(R.id.lpdisplay_goodsauto_public_lpgkit_value);



        lp_goodsauto_public_act.setText(b.getCharSequence("lp_goodsauto_public_act"));
        lp_goodsauto_public_paod.setText(b.getCharSequence("lp_goodsauto_public_paod"));
        lp_goodsauto_public_ll.setText(b.getCharSequence("lp_goodsauto_public_ll"));
        //lp_goodsauto_public_tax.setText(b.getCharSequence("lp_goodsauto_public_tax"));
        lp_goodsauto_public_coolie.setText(b.getCharSequence("lp_goodsauto_public_coolie"));
        lp_goodsauto_public_nfpp.setText(b.getCharSequence("lp_goodsauto_public_nfpp"));

        lp_goodsauto_public_lpgkit.setText(b.getCharSequence("lp_goodsauto_public_lpgkit"));


        //variables to take integer values
        int act = Integer.parseInt(b.getString("lp_goodsauto_public_act"));
        int paod = Integer.parseInt(b.getString("lp_goodsauto_public_paod"));
        int ll = Integer.parseInt(b.getString("lp_goodsauto_public_ll"));
        //int tax = Integer.parseInt(b.getString("lp_goodsauto_public_tax"));
        String cngkit = b.getString("lp_goodsauto_public_lpgkit");
        //int nfpp = Integer.parseInt(b.getString("lp_goodsauto_public_nfpp"));
        //int coolie = Integer.parseInt(b.getString("lp_goodsauto_public_coolie"));

        //nfpp and coolie
        int nfpp = Integer.parseInt(lp_goodsauto_public_nfpp.getText().toString());
        int coolie = Integer.parseInt(lp_goodsauto_public_coolie.getText().toString());
        nfpp = nfpp*75;
        coolie = coolie*50;
        lp_goodsauto_public_coolie.setText(String.valueOf(coolie));
        lp_goodsauto_public_nfpp.setText(String.valueOf(nfpp));

        //calculate the total premium for liability policy
        double tot_premium = calculate(act,paod,ll,cngkit,nfpp,coolie);

        //print total premium
         total_premium = (TextView)findViewById(R.id.lpdisplay_goodsauto_public_total_value);
        int final_premium  = (int)( Math.round(tot_premium));// for rounding up
        total_premium.setText(String.valueOf(final_premium));   //Setting final value to text view


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Goods Auto Public Liability Policy");

        findViewById(R.id.lpdisplay_goodsauto_public_home).setOnClickListener(listener_lpdisplay_goodsauto_public_home);

        share_btn = (CircleButton) findViewById(R.id.lpdisplay_goodsauto_public_share);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsChecker checker = new PermissionsChecker(lpdisplay_goodsauto_public.this);
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(lpdisplay_goodsauto_public.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);

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

    //Function to calculate
    double calculate(int act,int paod,int ll,String cng,int nfpp,int coolie){
        double total=0;
        if(cng.equals("Yes")){
            total+=act + paod + ll + nfpp+ coolie + 60;

            //To calculate 18% tax
            tax_18 = (total-4092)*0.18;
            int tax_18_final = (int)Math.round(tax_18);
            lp_goodsauto_public_tax_18.setText(String.valueOf(tax_18_final));

            //To calculate 12% tax
            tax_12 = 4092*0.12;
            int tax_12_final = (int)Math.round(tax_12);
            lp_goodsauto_public_tax_12.setText(String.valueOf(tax_12_final));

            total = (total + tax_18_final + tax_12_final);
            //total = (total + (tax*total*0.01));
        }
        else {
            total+=act + paod + ll + nfpp+ coolie ;

            //To calculate 18% tax
            tax_18 = (total-4092)*0.18;
            int tax_18_final = (int)Math.round(tax_18);
            lp_goodsauto_public_tax_18.setText(String.valueOf(tax_18_final));

            //To calculate 12% tax
            tax_12 = 4092*0.12;
            int tax_12_final = (int)Math.round(tax_12);
            lp_goodsauto_public_tax_12.setText(String.valueOf(tax_12_final));

            total = (total + tax_18_final + tax_12_final);
            //total = (total + (tax*total*0.01));
        }
        return total;
    }

    View.OnClickListener listener_lpdisplay_goodsauto_public_home = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(lpdisplay_goodsauto_public.this, home_activity.class);
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
            p.add(new Chunk("Rs. "+lp_goodsauto_public_act.getText().toString()));
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
            p.add(new Chunk("Rs. "+lp_goodsauto_public_paod.getText().toString()));
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
            p.add(new Chunk("Rs. "+lp_goodsauto_public_ll.getText().toString()));
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
            p.add(new Chunk("Rs. "+lp_goodsauto_public_nfpp.getText().toString()));
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
            p.add(new Chunk(lp_goodsauto_public_coolie.getText().toString()));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk("CNG KIT"));
            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            pdfPCell.addElement(p);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell();
            pdfPCell.setBorderColor(BaseColor.WHITE);
            table.addCell(pdfPCell);

            p = new Paragraph();
            p.add(new Chunk(lp_goodsauto_public_lpgkit.getText().toString()));
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
            p.add(new Chunk("Rs. "+total_premium.getText().toString(),white));
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

            Uri uri = FileProvider.getUriForFile(lpdisplay_goodsauto_public.this, BuildConfig.APPLICATION_ID + ".provider",file);
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
