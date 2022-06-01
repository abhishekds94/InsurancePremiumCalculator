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
class lpdisplay_com_goods_public_7500 : AppCompatActivity(), ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var lp_com_goods_public_7500_value = 0
    var lp_com_goods_public_7500_paod_value = 0
    var lp_com_goods_public_7500_lld_value = 0
    var lp_com_goods_public_7500_coolie_value = 0
    var lp_com_goods_public_7500_nfpp_value = 0
    var lp_com_goods_public_7500_tax_value = 0
    var lp_com_goods_public_7500 = 0
    var total_premium = 0.0
    var share_btn: CircleButton? = null
    var file: File? = null
    private var tax_18 = 0.0
    private var tax_12 = 0.0
    var lp_com_goods_public_7500_act: TextView? = null
    var lp_com_goods_public_7500_paod: TextView? = null
    var lp_com_goods_public_7500_ll: TextView? = null

    //TextView lp_com_goods_public_7500_tax;
    var lp_com_goods_public_7500_coolie: TextView? = null
    var lp_com_goods_public_7500_nfpp: TextView? = null
    var lp_com_goods_public_7500_total: TextView? = null
    var lp_com_goods_public_7500_tax_12: TextView? = null
    var lp_com_goods_public_7500_tax_18: TextView? = null
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
        checkfunction(this@lpdisplay_com_goods_public_7500)
        setContentView(R.layout.lpdisplay_com_goods_public_7500)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        val b: Bundle = getIntent().getExtras()!!
        lp_com_goods_public_7500_paod =
            findViewById<View>(R.id.lpdisplay_com_goods_public_7500_paod_value) as TextView?
        lp_com_goods_public_7500_ll =
            findViewById<View>(R.id.lpdisplay_com_goods_public_7500_ll_value) as TextView?
        //lp_com_goods_public_7500_tax = (TextView) findViewById(R.id.lpdisplay_com_goods_public_7500_tax_value);
        lp_com_goods_public_7500_coolie =
            findViewById<View>(R.id.lpdisplay_com_goods_public_7500_coolie_value) as TextView?
        lp_com_goods_public_7500_nfpp =
            findViewById<View>(R.id.lpdisplay_com_goods_public_7500_nfpp_value) as TextView?
        lp_com_goods_public_7500_tax_12 =
            findViewById<View>(R.id.lpdisplay_com_goods_public_7500_tax12_value) as TextView?
        lp_com_goods_public_7500_tax_18 =
            findViewById<View>(R.id.lpdisplay_com_goods_public_7500_tax18_value) as TextView?
        lp_com_goods_public_7500_total =
            findViewById<View>(R.id.lpdisplay_com_goods_public_7500_total_value) as TextView?
        lp_com_goods_public_7500_act =
            findViewById<View>(R.id.lpdisplay_com_goods_public_7500_act_value) as TextView?
        lp_com_goods_public_7500_act!!.text = b.getCharSequence("lp_com_goods_public_7500_act")
        lp_com_goods_public_7500_paod!!.text = b.getCharSequence("lp_com_goods_public_7500_paod")
        lp_com_goods_public_7500_ll!!.text = b.getCharSequence("lp_com_goods_public_7500_ll")
        //lp_com_goods_public_7500_tax.setText(b.getCharSequence("lp_com_goods_public_7500_tax"));
        lp_com_goods_public_7500_coolie!!.text =
            b.getCharSequence("lp_com_goods_public_7500_coolie")
        lp_com_goods_public_7500_nfpp!!.text = b.getCharSequence("lp_com_goods_public_7500_nfpp")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Public Commercial Vehicle - Liability Policy" as CharSequence)
        var nfpp = lp_com_goods_public_7500_coolie!!.text.toString().toInt()
        var coolie = lp_com_goods_public_7500_nfpp!!.text.toString().toInt()
        nfpp = nfpp * 75
        coolie = coolie * 50
        lp_com_goods_public_7500_coolie!!.text = coolie.toString()
        lp_com_goods_public_7500_nfpp!!.text = nfpp.toString()
        findViewById<View>(R.id.lpdisplay_com_goods_public_7500_home).setOnClickListener(
            listener_lpdisplay_com_goods_public_7500_home
        )
        share_btn = findViewById<View>(R.id.lpdisplay_com_goods_public_7500_share) as CircleButton?
        share_btn!!.setOnClickListener {
            val checker = PermissionsChecker(this@lpdisplay_com_goods_public_7500)
            if (checker.lacksPermissions(*PermissionsChecker.REQUIRED_PERMISSION)) {
                PermissionsActivity.startActivityForResult(
                    this@lpdisplay_com_goods_public_7500,
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
        val act_val = b.getCharSequence("lp_com_goods_public_7500_act").toString()
        val paod_value = b.getCharSequence("lp_com_goods_public_7500_paod").toString()
        val lld_value = b.getCharSequence("lp_com_goods_public_7500_ll").toString()
        val coolie_value = b.getCharSequence("lp_com_goods_public_7500_coolie").toString()
        val nfpp_value = b.getCharSequence("lp_com_goods_public_7500_nfpp").toString()
        //String tax_value = b.getCharSequence("lp_com_goods_public_7500_tax").toString();
        lp_com_goods_public_7500_value = Integer.valueOf(act_val).toInt()
        lp_com_goods_public_7500_paod_value = Integer.valueOf(paod_value).toInt()
        lp_com_goods_public_7500_lld_value = Integer.valueOf(lld_value).toInt()
        lp_com_goods_public_7500_coolie_value = Integer.valueOf(coolie_value).toInt()
        lp_com_goods_public_7500_nfpp_value = Integer.valueOf(nfpp_value).toInt()
        //this.lp_com_goods_public_7500_tax_value = Integer.valueOf(tax_value).intValue();
        val total =
            (lp_com_goods_public_7500_value + lp_com_goods_public_7500_paod_value + lp_com_goods_public_7500_lld_value + lp_com_goods_public_7500_coolie_value * 50 + lp_com_goods_public_7500_nfpp_value * 75).toDouble()

        //To calculate 18% tax
        tax_18 = (total - 16049) * 0.18
        val tax_18_final = Math.round(tax_18).toInt()
        lp_com_goods_public_7500_tax_18!!.text = tax_18_final.toString()

        //To calculate 12% tax
        tax_12 = 16049 * 0.12
        val tax_12_final = Math.round(tax_12).toInt()
        lp_com_goods_public_7500_tax_12!!.text = tax_12_final.toString()
        total_premium = tax_18_final + tax_12_final + total
        lp_com_goods_public_7500_total!!.text =
            Math.round(total_premium).toFloat().toInt().toString()
    }

    var listener_lpdisplay_com_goods_public_7500_home = View.OnClickListener {
        val intent = Intent(this@lpdisplay_com_goods_public_7500, home_activity::class.java)
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
            p.add(Chunk(": COMMERCIAL GOODS VEHICLE PUBLIC"))
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
            p.add(Chunk("Rs. " + lp_com_goods_public_7500_act!!.text.toString()))
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
            p.add(Chunk("Rs. " + lp_com_goods_public_7500_paod!!.text.toString()))
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
            p.add(Chunk("Rs. " + lp_com_goods_public_7500_ll!!.text.toString()))
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
            p.add(Chunk(lp_com_goods_public_7500_coolie!!.text.toString()))
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
            p.add(Chunk(lp_com_goods_public_7500_nfpp!!.text.toString()))
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
            p.add(Chunk("Rs. " + lp_com_goods_public_7500_total!!.text.toString(), white))
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
                this@lpdisplay_com_goods_public_7500,
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