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
class ppdisplay_taxi_upto6_upto1000 : AppCompatActivity(), ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var pp_taxi_upto6_upto1000_IDV_value: TextView? = null
    var pp_taxi_upto6_upto1000_date_value: TextView? = null
    var pp_taxi_upto6_upto1000_zone: TextView? = null
    var pp_taxi_upto6_upto1000_cc_value: TextView? = null
    var pp_taxi_upto6_upto1000_nd_value: TextView? = null
    var pp_taxi_upto6_upto1000_ndd_value: TextView? = null
    var pp_taxi_upto6_upto1000_uwd_value: TextView? = null
    var pp_taxi_upto6_upto1000_od_value: TextView? = null
    var pp_taxi_upto6_upto1000_lpg: TextView? = null
    var pp_taxi_upto6_upto1000_lpgtype: TextView? = null
    var pp_taxi_upto6_upto1000_antitheft: TextView? = null
    var pp_taxi_upto6_upto1000_ncb_value: TextView? = null
    var pp_taxi_upto6_upto1000_passrisk_value: TextView? = null
    var pp_taxi_upto6_upto1000_lpgkit_value: TextView? = null
    var pp_taxi_upto6_upto1000_b: TextView? = null
    var pp_taxi_upto6_upto1000_tp_value: TextView? = null
    var pp_taxi_upto6_upto1000_paod_value: TextView? = null
    var total_a_b: TextView? = null
    var ppdisplay_taxi_upto6_upto1000_total_value: TextView? = null
    var share_btn: CircleButton? = null
    var file: File? = null
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
        checkfunction(this@ppdisplay_taxi_upto6_upto1000)
        setContentView(R.layout.ppdisplay_taxi_upto6_upto1000)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        val b: Bundle = getIntent().getExtras()!!
        pp_taxi_upto6_upto1000_IDV_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_IDV_value) as TextView?
        pp_taxi_upto6_upto1000_date_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_date_value) as TextView?
        pp_taxi_upto6_upto1000_zone =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_zone_value) as TextView?
        pp_taxi_upto6_upto1000_cc_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_cc_value) as TextView?
        pp_taxi_upto6_upto1000_nd_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_nd_value) as TextView?
        pp_taxi_upto6_upto1000_ndd_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_ndd_value) as TextView?
        pp_taxi_upto6_upto1000_uwd_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_uwd_value) as TextView?
        pp_taxi_upto6_upto1000_od_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_od_value) as TextView?
        pp_taxi_upto6_upto1000_lpg =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_lpg_value) as TextView?
        pp_taxi_upto6_upto1000_lpgtype =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_lpgtype_value) as TextView?
        pp_taxi_upto6_upto1000_antitheft =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_antitheft_value) as TextView?
        pp_taxi_upto6_upto1000_ncb_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_ncb_value) as TextView?
        pp_taxi_upto6_upto1000_passrisk_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_passrisk_value) as TextView?
        pp_taxi_upto6_upto1000_lpgkit_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_lpgkit_value) as TextView?
        pp_taxi_upto6_upto1000_b =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_b_value) as TextView?
        pp_taxi_upto6_upto1000_paod_value =
            findViewById<TextView>(R.id.ppdisplay_taxi_upto6_upto1000_pa_value)
        pp_taxi_upto6_upto1000_tp_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_tp_value) as TextView?
        total_a_b = findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_ab_value) as TextView?
        ppdisplay_taxi_upto6_upto1000_total_value =
            findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_total_value) as TextView?
        pp_taxi_upto6_upto1000_IDV_value!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_idv_value")
        pp_taxi_upto6_upto1000_date_value!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_date_value")
        pp_taxi_upto6_upto1000_zone!!.text = b.getCharSequence("pp_taxi_upto6_upto1000_zone")
        pp_taxi_upto6_upto1000_cc_value!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_cc_value")
        pp_taxi_upto6_upto1000_nd_value!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_nd_value")
        pp_taxi_upto6_upto1000_ndd_value!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_ndd_value")
        pp_taxi_upto6_upto1000_uwd_value!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_uwd_value")
        pp_taxi_upto6_upto1000_ncb_value!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_ncb_value")
        pp_taxi_upto6_upto1000_lpg!!.text = b.getCharSequence("pp_taxi_upto6_upto1000_lpg")
        pp_taxi_upto6_upto1000_paod_value!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_paod_value")
        pp_taxi_upto6_upto1000_lpgtype!!.text = b.getCharSequence("pp_taxi_upto6_upto1000_lpgtype")
        pp_taxi_upto6_upto1000_antitheft!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_antitheft")

        //od value of A
        var od_value = b.getCharSequence("pp_taxi_upto6_upto1000_od_value").toString().toDouble()
        od_value = Math.round(od_value * 100.0).toDouble() / 100
        od_value = Math.ceil(od_value)
        val d = od_value
        val x = d.toInt()
        pp_taxi_upto6_upto1000_od_value!!.text = x.toString()
        pp_taxi_upto6_upto1000_passrisk_value!!.text =
            b.getCharSequence("pp_taxi_upto6_scpassengers_upto1000")
        pp_taxi_upto6_upto1000_lpgkit_value!!.text =
            b.getCharSequence("pp_taxi_upto6_upto1000_lpgkit_value")

        //calculating value of B
        val total_b = (Integer.valueOf(pp_taxi_upto6_upto1000_tp_value!!.text.toString())
                + 50 + Integer.valueOf(pp_taxi_upto6_upto1000_paod_value!!.text.toString()) //320+50
                + Integer.valueOf(
            b.getCharSequence("pp_taxi_upto6_scpassengers_upto1000").toString()
        )
                + Integer.valueOf(
            b.getCharSequence("pp_taxi_upto6_upto1000_lpgkit_value").toString()
        ))
        pp_taxi_upto6_upto1000_b!!.text = total_b.toString()
        total_a_b!!.text = (x + total_b).toString()
        ppdisplay_taxi_upto6_upto1000_total_value!!.text =
            Math.floor((od_value + total_b) * 1.18).toInt().toString()
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Taxi Package Policy")
        findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_home).setOnClickListener(
            listener_ppdisplay_taxi_upto6_upto1000_home
        )
        share_btn = findViewById<View>(R.id.ppdisplay_taxi_upto6_upto1000_share) as CircleButton?
        share_btn!!.setOnClickListener {
            val checker = PermissionsChecker(this@ppdisplay_taxi_upto6_upto1000)
            if (checker.lacksPermissions(*PermissionsChecker.REQUIRED_PERMISSION)) {
                PermissionsActivity.startActivityForResult(
                    this@ppdisplay_taxi_upto6_upto1000,
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

    var listener_ppdisplay_taxi_upto6_upto1000_home = View.OnClickListener {
        val intent = Intent(this@ppdisplay_taxi_upto6_upto1000, home_activity::class.java)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
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
        checkfunction(this@ppdisplay_taxi_upto6_upto1000)
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
            p.add(Chunk(": TAXI"))
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
            p.add(Chunk(": PACKAGE POLICY"))
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
            p.add(Chunk("IDV"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + pp_taxi_upto6_upto1000_IDV_value!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("DATE"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_date_value!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("ZONE"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_zone!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("CC"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_cc_value!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("LPG"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_lpg!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("LPG Type"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_lpgtype!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("N / D"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_nd_value!!.text.toString() + "%"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Anti-Theft"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_antitheft!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("N / D DISCOUNT"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_ndd_value!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("U/W DISCOUNT"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_uwd_value!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("N C B"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_taxi_upto6_upto1000_ncb_value!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("(A) -> OD TOTAL", white))
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
            p.add(Chunk("Rs. " + pp_taxi_upto6_upto1000_od_value!!.text.toString(), white))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.BLACK
            pdfPCell.backgroundColor = BaseColor.BLACK
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("TP BASIC"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + "6040"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("P A to Owner - Driver"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + pp_taxi_upto6_upto1000_paod_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + "50"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Passenger's RISK"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + pp_taxi_upto6_upto1000_passrisk_value!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("LPG Kit"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + pp_taxi_upto6_upto1000_lpgkit_value!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("(B) -> TOTAL", white))
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
            p.add(Chunk("Rs. " + pp_taxi_upto6_upto1000_b!!.text.toString(), white))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.BLACK
            pdfPCell.backgroundColor = BaseColor.BLACK
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("(A) + (B)"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + total_a_b!!.text.toString()))
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
            p.add(
                Chunk(
                    "Rs. " + ppdisplay_taxi_upto6_upto1000_total_value!!.text.toString(),
                    white
                )
            )
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
                this@ppdisplay_taxi_upto6_upto1000,
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