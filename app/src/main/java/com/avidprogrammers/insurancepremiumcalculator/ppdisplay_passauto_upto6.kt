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
class ppdisplay_passauto_upto6 : AppCompatActivity(), ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    private var total_premium = 0.0
    private val cng_val = 0.0
    var cng_kit = 0
    var nd_val = 0.0
    var value_ndd = 0.0
    var rounded_value_nd = 0.0
    var rounded_ndd_value = 0.0
    var share_btn: CircleButton? = null
    var file: File? = null
    var pp_passauto_upto6_IDV_value: TextView? = null
    var pp_passauto_upto6_date_value: TextView? = null
    var pp_passauto_upto6_zone: TextView? = null
    var pp_passauto_upto6_cc_value: TextView? = null
    var pp_passauto_upto6_nd_value: TextView? = null
    var pp_passauto_upto6_ndd_value: TextView? = null
    var pp_passauto_upto6_uwd_value: TextView? = null
    var pp_passauto_upto6_ncb_value: TextView? = null
    var pp_passauto_upto6_od_value: TextView? = null
    var pp_passauto_upto6_b_value: TextView? = null
    var pp_passauto_upto6_ab_value: TextView? = null
    var pp_passauto_upto6_paod_value: TextView? = null
    var pp_passauto_upto6_total_value: TextView? = null
    var pp_passauto_upto6_lpg: TextView? = null
    var pp_passauto_upto6_lpgtype: TextView? = null
    var pp_passauto_upto6_lpgkit_value: TextView? = null
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
        checkfunction(this@ppdisplay_passauto_upto6)
        setContentView(R.layout.ppdisplay_passauto_upto6)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        val b: Bundle = getIntent().getExtras()!!
        pp_passauto_upto6_IDV_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_IDV_value) as TextView?
        pp_passauto_upto6_date_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_date_value) as TextView?
        pp_passauto_upto6_zone =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_zone_value) as TextView?
        pp_passauto_upto6_cc_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_cc_value) as TextView?
        pp_passauto_upto6_nd_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_nd_value) as TextView?
        pp_passauto_upto6_ndd_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_ndd_value) as TextView?
        pp_passauto_upto6_uwd_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_uwd_value) as TextView?
        pp_passauto_upto6_ncb_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_ncb_value) as TextView?
        pp_passauto_upto6_od_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_od_value) as TextView?
        pp_passauto_upto6_b_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_b_value) as TextView?
        pp_passauto_upto6_ab_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_ab_value) as TextView?
        pp_passauto_upto6_paod_value =
            findViewById<TextView>(R.id.ppdisplay_passauto_upto6_pa_value)
        pp_passauto_upto6_total_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_total_value) as TextView?
        pp_passauto_upto6_lpg =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_lpg_value) as TextView?
        pp_passauto_upto6_lpgtype =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_lpgtype_value) as TextView?
        pp_passauto_upto6_lpgkit_value =
            findViewById<View>(R.id.ppdisplay_passauto_upto6_lpgkit_value) as TextView?
        pp_passauto_upto6_IDV_value!!.text = b.getCharSequence("pp_passauto_upto6_idv_value")
        pp_passauto_upto6_date_value!!.text = b.getCharSequence("pp_passauto_upto6_date_value")
        pp_passauto_upto6_zone!!.text = b.getCharSequence("pp_passauto_upto6_zone")
        pp_passauto_upto6_cc_value!!.text = b.getCharSequence("pp_passauto_upto6_cc_value")
        pp_passauto_upto6_nd_value!!.text = b.getCharSequence("pp_passauto_upto6_nd_value")
        pp_passauto_upto6_ndd_value!!.text = b.getCharSequence("pp_passauto_upto6_ndd_value")
        pp_passauto_upto6_uwd_value!!.text = b.getCharSequence("pp_passauto_upto6_uwd_value")
        pp_passauto_upto6_paod_value!!.text = b.getCharSequence("pp_passauto_upto6_paod_value")
        pp_passauto_upto6_lpg!!.text = b.getCharSequence("pp_passauto_upto6_lpg")
        //if cng is no then cng  type = "-"
        if (b.getString("lpgtype_value") == "-") {
            pp_passauto_upto6_lpgtype!!.text = "-"
        } else {
            pp_passauto_upto6_lpgtype!!.text = b.getCharSequence("pp_passauto_upto6_lpgtype")
        }
        pp_passauto_upto6_ncb_value!!.text = b.getCharSequence("pp_passauto_upto6_ncb_value")
        val diff_days = b.getLong("diff_in_days")

        //function to calculate IDv using DOP
        val val_idv = calculateIdv(diff_days, pp_passauto_upto6_zone!!.text.toString())
        total_premium = pp_passauto_upto6_IDV_value!!.text.toString().toLong() * val_idv * 0.01

        //function to calculate cng value and type
        val is_cng = check_cng(pp_passauto_upto6_lpg!!.text.toString())
        val yes = 60
        val no = 0
        if (is_cng == 0) pp_passauto_upto6_lpgkit_value!!.text =
            no.toString() else if (is_cng == 1) {
            pp_passauto_upto6_lpgkit_value!!.text = yes.toString()
            if (pp_passauto_upto6_lpgtype!!.text.toString() == "Fixed") {
                val `val` = b.getString("lpgtype_value")!!.toInt()
                total_premium = total_premium + `val` * 4 * 0.01
            }
        }
        //For N/D
        val nd_val = pp_passauto_upto6_nd_value!!.text.toString().toInt()
        total_premium = total_premium + total_premium * nd_val * 0.01
        //        Toast.makeText(getApplicationContext(), " ND:  " + nd_val,  Toast.LENGTH_SHORT).show();

        //For UW discount
        val uwd_val = pp_passauto_upto6_uwd_value!!.text.toString().toInt()
        total_premium = total_premium - total_premium * uwd_val * 0.01

        //Calculation of ND discount
        val ndd_value = Integer.valueOf(b.getString("pp_passauto_upto6_ndd_value")).toDouble()
        val ndd = ndd_value * nd_val / 100
        //        Toast.makeText(getApplicationContext(), " NDD:  " + ndd,  Toast.LENGTH_SHORT).show();
        //total_premium = ((total_premium - ndd) *0.01);
        total_premium = total_premium - ndd

        //For NCB
        val ncb_val = pp_passauto_upto6_ncb_value!!.text.toString().toInt()
        total_premium = total_premium - total_premium * ncb_val * 0.01

        //display OD total (A)
        var final_premium =
            Math.round(total_premium).toInt() // for rounding up till 2 decimal places
        pp_passauto_upto6_od_value!!.text = final_premium.toString()

        //display Total (B)
        val ba: Int
        ba = if (is_cng == 1) 60 else 0
        val b_total = 2539 + pp_passauto_upto6_paod_value!!.text.toString().toInt() + 50 + 3642 + ba
        pp_passauto_upto6_b_value!!.text = b_total.toString()

        //display A+B
        val ab = pp_passauto_upto6_od_value!!.text.toString()
            .toDouble() + pp_passauto_upto6_b_value!!.text.toString().toDouble()
        final_premium = Math.round(ab).toInt() // for rounding up till 2 decimal places
        pp_passauto_upto6_ab_value!!.text = final_premium.toString()
        total_premium = ab
        total_premium += total_premium * 0.18

        //Display final total premium
        final_premium = Math.round(total_premium).toInt() // for rounding up till 2 decimal places
        pp_passauto_upto6_total_value!!.text = final_premium.toString()
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Passenger Auto Package Policy")
        findViewById<View>(R.id.ppdisplay_passauto_upto6_home).setOnClickListener(
            listener_ppdisplay_passauto_upto6_home
        )
        share_btn = findViewById<View>(R.id.ppdisplay_passauto_upto6_share) as CircleButton?
        share_btn!!.setOnClickListener {
            val checker = PermissionsChecker(this@ppdisplay_passauto_upto6)
            if (checker.lacksPermissions(*PermissionsChecker.REQUIRED_PERMISSION)) {
                PermissionsActivity.startActivityForResult(
                    this@ppdisplay_passauto_upto6,
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

    fun check_cng(cng: String): Int {
        return if (cng == "Yes") {
            1
        } else {
            0
        }
    }

    fun calculateIdv(diff_in_days: Long, zone: String): Double {
        if (zone == "A") {
            return if (diff_in_days < 1825) {
                1.278
            } else if (diff_in_days <= 2555) {
                1.310
            } else {
                1.342
            }
        } else if (zone == "B") {
            return if (diff_in_days < 1825) {
                1.272
            } else if (diff_in_days <= 2555) {
                1.304
            } else {
                1.336
            }
        } else if (zone == "C") {
            return if (diff_in_days < 1825) {
                1.260
            } else if (diff_in_days <= 2555) {
                1.292
            } else {
                1.323
            }
        }
        return 0.0
    }

    var listener_ppdisplay_passauto_upto6_home = View.OnClickListener {
        val intent = Intent(this@ppdisplay_passauto_upto6, home_activity::class.java)
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
        checkfunction(this@ppdisplay_passauto_upto6)
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
            p.add(Chunk(": PASSENGER AUTO"))
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
            p.add(Chunk("O D Basic"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. 450"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
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
            p.add(Chunk("Rs. " + pp_passauto_upto6_IDV_value!!.text.toString()))
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
            p.add(Chunk(pp_passauto_upto6_date_value!!.text.toString()))
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
            p.add(Chunk(pp_passauto_upto6_zone!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("TYPE"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Upto 6 Passengers"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("CNG"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_passauto_upto6_lpg!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("CNG TYPE"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_passauto_upto6_lpgtype!!.text.toString()))
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
            p.add(Chunk(pp_passauto_upto6_nd_value!!.text.toString() + "%"))
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
            p.add(Chunk(pp_passauto_upto6_ndd_value!!.text.toString() + "%"))
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
            p.add(Chunk(pp_passauto_upto6_uwd_value!!.text.toString()))
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
            p.add(Chunk(pp_passauto_upto6_ncb_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + pp_passauto_upto6_od_value!!.text.toString(), white))
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
            p.add(Chunk("Rs. " + "2539"))
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
            p.add(Chunk("Rs. " + pp_passauto_upto6_paod_value!!.text.toString()))
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
            p.add(Chunk("Rs. 50"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Passengers Risk"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. 3642"))
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
            p.add(Chunk("Rs. " + pp_passauto_upto6_b_value!!.text.toString(), white))
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
            p.add(Chunk("Rs. " + pp_passauto_upto6_ab_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + pp_passauto_upto6_total_value!!.text.toString(), white))
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
                this@ppdisplay_passauto_upto6,
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