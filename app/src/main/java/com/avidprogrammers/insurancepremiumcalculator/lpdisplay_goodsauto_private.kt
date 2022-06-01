package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import at.markushi.ui.CircleButton
import com.avidprogrammers.insurancepremiumcalculator.*
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.avidprogrammers.utils.PermissionsActivity
import com.avidprogrammers.utils.PermissionsChecker
import com.google.android.gms.ads.AdView
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Abhishek on 26-Mar-17.
 */
class lpdisplay_goodsauto_private : AppCompatActivity(), ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var share_btn: CircleButton? = null
    var file: File? = null
    var lp_goodsauto_private_act: TextView? = null
    var lp_goodsauto_private_paod: TextView? = null
    var lp_goodsauto_private_ll: TextView? = null
    var lp_goodsauto_private_tax: TextView? = null
    var lp_goodsauto_private_coolie: TextView? = null
    var lp_goodsauto_private_tax_12: TextView? = null
    var lp_goodsauto_private_tax_18: TextView? = null
    var lp_goodsauto_private_nfpp: TextView? = null
    var total_premium: TextView? = null
    private var tax_18 = 0.0
    private var tax_12 = 0.0
    var lp_goodsauto_private_lpgkit: TextView? = null
    protected override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@lpdisplay_goodsauto_private)
        setContentView(R.layout.lpdisplay_goodsauto_private)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        val b: Bundle = getIntent().getExtras()!!
        lp_goodsauto_private_act =
            findViewById<View>(R.id.lpdisplay_goodsauto_private_act_value) as TextView?
        lp_goodsauto_private_paod =
            findViewById<View>(R.id.lpdisplay_goodsauto_private_paod_value) as TextView?
        lp_goodsauto_private_ll =
            findViewById<View>(R.id.lpdisplay_goodsauto_private_ll_value) as TextView?
        //lp_goodsauto_private_tax = (TextView) findViewById(R.id.lpdisplay_goodsauto_private_tax_value);
        lp_goodsauto_private_coolie =
            findViewById<View>(R.id.lpdisplay_goodsauto_private_coolie_value) as TextView?
        lp_goodsauto_private_nfpp =
            findViewById<View>(R.id.lpdisplay_goodsauto_private_nfpp_value) as TextView?
        lp_goodsauto_private_tax_12 =
            findViewById<View>(R.id.lpdisplay_goodsauto_private_tax12_value) as TextView?
        lp_goodsauto_private_tax_18 =
            findViewById<View>(R.id.lpdisplay_goodsauto_private_tax18_value) as TextView?
        lp_goodsauto_private_lpgkit =
            findViewById<View>(R.id.lpdisplay_goodsauto_private_lpgkit_value) as TextView?
        lp_goodsauto_private_act!!.text = b.getCharSequence("lp_goodsauto_private_act")
        lp_goodsauto_private_paod!!.text = b.getCharSequence("lp_goodsauto_private_paod")
        lp_goodsauto_private_ll!!.text = b.getCharSequence("lp_goodsauto_private_ll")
        //        lp_goodsauto_private_tax.setText(b.getCharSequence("lp_goodsauto_private_tax"));
        lp_goodsauto_private_coolie!!.text = b.getCharSequence("lp_goodsauto_private_coolie")
        lp_goodsauto_private_nfpp!!.text = b.getCharSequence("lp_goodsauto_private_nfpp")
        lp_goodsauto_private_lpgkit!!.text = b.getCharSequence("lp_goodsauto_private_lpgkit")


        //variables to take integer values
        val act = b.getString("lp_goodsauto_private_act")!!.toInt()
        val paod = b.getString("lp_goodsauto_private_paod")!!.toInt()
        val ll = b.getString("lp_goodsauto_private_ll")!!.toInt()
        //int tax = Integer.parseInt(b.getString("lp_goodsauto_private_tax"));
        val cngkit = b.getString("lp_goodsauto_private_lpgkit")
        //int nfpp = Integer.parseInt(b.getString("lp_goodsauto_private_nfpp"));
        //int coolie = Integer.parseInt(b.getString("lp_goodsauto_private_coolie"));

        //nfpp and coolie
        var nfpp = lp_goodsauto_private_nfpp!!.text.toString().toInt()
        var coolie = lp_goodsauto_private_coolie!!.text.toString().toInt()
        nfpp = nfpp * 75
        coolie = coolie * 50
        lp_goodsauto_private_coolie!!.text = coolie.toString()
        lp_goodsauto_private_nfpp!!.text = nfpp.toString()

        //calculate the total premium for liability policy
        val tot_premium = calculate(act, paod, ll, cngkit, nfpp, coolie)

        //print total premium
        total_premium =
            findViewById<View>(R.id.lpdisplay_goodsauto_private_total_value) as TextView?
        val final_premium = Math.round(tot_premium).toInt() // for rounding up
        total_premium!!.text = final_premium.toString() //Setting final value to text view
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Goods Auto Private Liability Policy")
        findViewById<View>(R.id.lpdisplay_goodsauto_private_home).setOnClickListener(
            listener_lpdisplay_goodsauto_private_home
        )
        share_btn = findViewById<View>(R.id.lpdisplay_goodsauto_private_share) as CircleButton?
        share_btn!!.setOnClickListener {
            val checker = PermissionsChecker(this@lpdisplay_goodsauto_private)
            if (checker.lacksPermissions(*PermissionsChecker.REQUIRED_PERMISSION)) {
                PermissionsActivity.startActivityForResult(
                    this@lpdisplay_goodsauto_private,
                    PermissionsActivity.PERMISSION_REQUEST_CODE,
                    *PermissionsChecker.REQUIRED_PERMISSION
                )
            } else {
                val date = Date()
                val dateformat = SimpleDateFormat("ddMMyyHHmmss")
                val filename = "InsurancePremium" + dateformat.format(date) + ".pdf"
                val direct = File(
                    Environment.getExternalStorageDirectory()
                        .toString() + "/InsurancePremiumCalculator"
                )
                if (!direct.exists()) {
                    val myDirectory = File("/sdcard/InsurancePremiumCalculator/")
                    myDirectory.mkdirs()
                    myDirectory.setReadable(true)
                    myDirectory.setWritable(true)
                    myDirectory.setExecutable(true)
                }
                file = File(File("/sdcard/InsurancePremiumCalculator/"), filename)
                if (file!!.exists()) {
                    file!!.delete()
                }
                val document = Document(PageSize.A4, 30F, 30F, 30F, 30F)
                try {
                    PdfWriter.getInstance(document, FileOutputStream(file))
                    document.open()
                    settingUpPDF(document)
                    document.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    //Function to calculate
    fun calculate(act: Int, paod: Int, ll: Int, cng: String?, nfpp: Int, coolie: Int): Double {
        var total = 0.0
        if (cng == "Yes") {
            total += (act + paod + ll + nfpp + coolie + 60).toDouble()

            //To calculate 18% tax
            tax_18 = (total - 3922) * 0.18
            val tax_18_final = Math.round(tax_18).toInt()
            lp_goodsauto_private_tax_18!!.text = tax_18_final.toString()

            //To calculate 12% tax
            tax_12 = 3922 * 0.12
            val tax_12_final = Math.round(tax_12).toInt()
            lp_goodsauto_private_tax_12!!.text = tax_12_final.toString()
            total = total + tax_18_final + tax_12_final
            //total = (total + (tax*total*0.01));
        } else {
            total += (act + paod + ll + nfpp + coolie).toDouble()

            //To calculate 18% tax
            tax_18 = (total - 3922) * 0.18
            val tax_18_final = Math.round(tax_18).toInt()
            lp_goodsauto_private_tax_18!!.text = tax_18_final.toString()

            //To calculate 12% tax
            tax_12 = 3922 * 0.12
            val tax_12_final = Math.round(tax_12).toInt()
            lp_goodsauto_private_tax_12!!.text = tax_12_final.toString()
            total = total + tax_18_final + tax_12_final
            //total = (total + (tax*total*0.01));
        }
        return total
    }

    var listener_lpdisplay_goodsauto_private_home = View.OnClickListener {
        val intent = Intent(this@lpdisplay_goodsauto_private, home_activity::class.java)
        startActivity(intent)
    }

    fun checkfunction(context: Context?) {
        val isConnected: Boolean = ConnectivityReceiver.Companion.isConnected
        checkingStatus!!.notification(isConnected, context!!)
    }

    protected override fun onResume() {
        super.onResume()
        MyApplication.Companion.instance!!.setConnectivityListener(this)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        checkingStatus!!.notification(isConnected, this)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun settingUpPDF(document: Document) {
        val lineSeparator = LineSeparator()
        lineSeparator.lineColor = BaseColor.BLACK
        val white: Font = Font(Font.FontFamily.HELVETICA, 14F, Font.BOLD, BaseColor.WHITE)
        try {
            var mChunk = Chunk("PREMIUM COMPUTATION SHEET")
            var mPara = Paragraph(mChunk)
            mPara.alignment = Element.ALIGN_CENTER
            document.add(mPara)
            document.add(Chunk(lineSeparator))
            var p: Paragraph
            p = Paragraph()
            p.add(Chunk("Vehicle type"))
            p.tabSettings = TabSettings(56f)
            p.add(Chunk.TABBING)
            p.add(Chunk(": GOODS AUTO PRIVATE"))
            var table = PdfPTable(2)
            table.totalWidth = document.pageSize.width - 80
            table.isLockedWidth = true
            var pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Policy Type"))
            p.tabSettings = TabSettings(56f)
            p.add(Chunk.TABBING)
            p.add(Chunk(": LIABILITY POLICY"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            document.add(table)
            document.add(Chunk(lineSeparator))
            mChunk = Chunk("SCHEDULE OF PREMIUM")
            mPara = Paragraph(mChunk)
            mPara.alignment = Element.ALIGN_CENTER
            document.add(mPara)
            document.add(Chunk(lineSeparator))
            table = PdfPTable(3)
            table.totalWidth = document.pageSize.width - 80
            table.isLockedWidth = true
            p = Paragraph()
            p.add(Chunk("COVER DESCRIPTION"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("PREMIUM"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            document.add(table)
            document.add(Chunk(lineSeparator))
            table = PdfPTable(3)
            table.totalWidth = document.pageSize.width - 80
            table.isLockedWidth = true
            p = Paragraph()
            p.add(Chunk("Act / Liability"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + lp_goodsauto_private_act!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("PA to Owner / Driver"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + lp_goodsauto_private_paod!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("L L to Driver"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + lp_goodsauto_private_ll!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("NFPP"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + lp_goodsauto_private_nfpp!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("COOLIE"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(lp_goodsauto_private_coolie!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("CNG KIT"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(lp_goodsauto_private_lpgkit!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Service Tax"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("18%"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Total Premium", white))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.BLACK
            pdfPCell.backgroundColor = BaseColor.BLACK
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.BLACK
            pdfPCell.backgroundColor = BaseColor.BLACK
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + total_premium!!.text.toString(), white))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.BLACK
            pdfPCell.backgroundColor = BaseColor.BLACK
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            document.add(table)
            document.add(Chunk(lineSeparator))
            mChunk = Chunk("Shared from Motor Insurance Premium Calculator App")
            mPara = Paragraph(mChunk)
            mPara.alignment = Element.ALIGN_CENTER
            document.add(mPara)
            document.add(Chunk(lineSeparator))
            val uri = FileProvider.getUriForFile(
                this@lpdisplay_goodsauto_private,
                BuildConfig.APPLICATION_ID + ".provider",
                file!!
            )
            val share = Intent()
            share.action = Intent.ACTION_SEND
            share.type = "application/pdf"
            share.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(share, "Share Using"))
        } catch (e: DocumentException) {
            e.printStackTrace()
        }
    }
}