package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.os.Environment
import android.view.View
import com.itextpdf.text.*
import com.itextpdf.text.pdf.draw.LineSeparator
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import android.os.Bundle
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.content.Intent
import android.widget.Toast
import android.widget.TextView
import at.markushi.ui.CircleButton
import com.avidprogrammers.utils.PermissionsChecker
import com.avidprogrammers.utils.PermissionsActivity
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfPCell
import androidx.core.content.FileProvider

/**
 * Created by Abhishek on 26-Mar-17.
 */
class ppdisplay_agri : AppCompatActivity(), ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var diffInDays: Long = 0
    var dop_value = 0.0
    var rounded_dop_value = 0f
    var rounded_value_nd = 0f
    var rounded_value_antitheft = 0f
    var rounded_uw_value = 0f
    var rounded_ncb_value = 0f
    var value_uw1 = 0.0
    var value_spin1 = 0.0
    var total_premium_b = 0.0
    var share_btn: CircleButton? = null
    var file: File? = null
    var pp_agri_IDV_tractor_value: TextView? = null
    var ppdisplay_agri_IDV_trailer_value: TextView? = null
    var pp_agri_date_value: TextView? = null
    var pp_agri_zone: TextView? = null
    var pp_agri_nd_value: TextView? = null
    var pp_agri_uwd_value: TextView? = null
    var agri_coolie: TextView? = null
    var ppdisplay_agri_ncb_value: TextView? = null
    var ppdisplay_agri_od_value: TextView? = null
    var ppdisplay_agri_total_value: TextView? = null
    var ppdisplay_agri_ab_value: TextView? = null
    var ppdisplay_agri_paod_value: TextView? = null
    var ppdisplay_agri_b_value: TextView? = null
    var ppdisplay_agri_tp_trailer_value: TextView? = null
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
        checkfunction(this@ppdisplay_agri)
        setContentView(R.layout.ppdisplay_agri)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        val b: Bundle = getIntent().getExtras()!!
        pp_agri_IDV_tractor_value =
            findViewById<View>(R.id.ppdisplay_agri_IDV_value) as TextView?
        ppdisplay_agri_IDV_trailer_value =
            findViewById<View>(R.id.ppdisplay_agri_IDV_trailer_value) as TextView?
        pp_agri_date_value =
            findViewById<View>(R.id.ppdisplay_agri_date_value) as TextView?
        pp_agri_zone = findViewById<View>(R.id.ppdisplay_agri_zone_value) as TextView?
        pp_agri_nd_value =
            findViewById<View>(R.id.ppdisplay_agri_nd_value) as TextView?
        pp_agri_uwd_value =
            findViewById<View>(R.id.ppdisplay_agri_uwd_value) as TextView?
        agri_coolie =
            findViewById<View>(R.id.ppdisplay_agri_coolie_value) as TextView?
        ppdisplay_agri_ncb_value =
            findViewById<View>(R.id.ppdisplay_agri_ncb_value) as TextView?
        ppdisplay_agri_od_value =
            findViewById<View>(R.id.ppdisplay_agri_od_value) as TextView?
        ppdisplay_agri_total_value =
            findViewById<View>(R.id.ppdisplay_agri_total_value) as TextView?
        ppdisplay_agri_ab_value =
            findViewById<View>(R.id.ppdisplay_agri_ab_value) as TextView?
        ppdisplay_agri_b_value =
            findViewById<View>(R.id.ppdisplay_agri_b_value) as TextView?
        ppdisplay_agri_paod_value = findViewById<TextView>(R.id.ppdisplay_agri_pa_value)
        ppdisplay_agri_tp_trailer_value =
            findViewById<View>(R.id.ppdisplay_agri_tp_trailer_value) as TextView?
        pp_agri_IDV_tractor_value!!.text = b.getCharSequence("pp_agri_idv_tractor_value")
        pp_agri_date_value!!.text = b.getCharSequence("pp_agri_date_value")
        pp_agri_zone!!.text = b.getCharSequence("pp_agri_zone")
        pp_agri_uwd_value!!.text = b.getCharSequence("pp_agri_uwd_value")
        ppdisplay_agri_paod_value!!.text = b.getCharSequence("pp_agri_paod_value")
        ppdisplay_agri_IDV_trailer_value!!.text = b.getCharSequence("pp_agri_trailer_value")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Tractors & Trailers Package Policy")
        findViewById<View>(R.id.ppdisplay_agri_home).setOnClickListener(
            listener_ppdisplay_agri_home
        )
        share_btn = findViewById<View>(R.id.ppdisplay_agri_share) as CircleButton?
        share_btn!!.setOnClickListener {
            val checker = PermissionsChecker(this@ppdisplay_agri)
            if (checker.lacksPermissions(*PermissionsChecker.REQUIRED_PERMISSION)) {
                PermissionsActivity.startActivityForResult(
                    this@ppdisplay_agri,
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
        val d1 = b.getString("pp_agri_date_value").toString()
        val s = d1
        val y = b.getInt("year")
        val m = b.getInt("month")
        val d = b.getInt("day")
        val mYear: Int
        val mMonth: Int
        val mDay: Int
        // Create Calendar instance
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        mYear = calendar1[Calendar.YEAR]
        mMonth = calendar1[Calendar.MONTH]
        mDay = calendar1[Calendar.DAY_OF_MONTH]

        // Set the values for the calendar fields YEAR, MONTH, and DAY_OF_MONTH.
        calendar1[mYear, mMonth] = mDay
        calendar2[y, m] = d

        /*
            * Use getTimeInMillis() method to get the Calendar's time value in
            * milliseconds. This method returns the current time as UTC
            * milliseconds from the epoch
            */
        val miliSecondForDate1 = calendar1.timeInMillis
        val miliSecondForDate2 = calendar2.timeInMillis

        // Calculate the difference in millisecond between two dates
        val diffInMilis = miliSecondForDate1 - miliSecondForDate2

        /*
              * Now we have difference between two date in form of millsecond we can
              * easily convert it Minute / Hour / Days by dividing the difference
              * with appropriate value. 1 Second : 1000 milisecond 1 Hour : 60 * 1000
              * millisecond 1 Day : 24 * 60 * 1000 milisecond
              */
        val diffInSecond = diffInMilis / 1000
        val diffInMinute = diffInMilis / (60 * 1000)
        val diffInHour = diffInMilis / (60 * 60 * 1000)
        diffInDays = diffInMilis / (24 * 60 * 60 * 1000)

        //Calculation of Dop value
        if (b.getString("pp_agri_zone") == "A") {
            if (diffInDays < 1825) {
                val idv_value_tractor =
                    Integer.valueOf(b.getString("pp_agri_idv_tractor_value")).toDouble()
                val idv_value_trailer =
                    Integer.valueOf(b.getString("pp_agri_trailer_value")).toDouble()
                dop_value = idv_value_tractor * 1.208 / 100 + idv_value_trailer * 1.208 / 100
                val round_value = dop_value
                rounded_dop_value = Math.round(round_value).toFloat()
            } else if (diffInDays >= 1825) {
                if (diffInDays < 2555) {
                    val idv_value_tractor =
                        Integer.valueOf(b.getString("pp_agri_idv_tractor_value"))
                            .toDouble()
                    val idv_value_trailer =
                        Integer.valueOf(b.getString("pp_agri_trailer_value")).toDouble()
                    dop_value = idv_value_tractor * 1.238 / 100 + idv_value_trailer * 1.238 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                } else if (diffInDays >= 2555) {
                    val idv_value_tractor =
                        Integer.valueOf(b.getString("pp_agri_idv_tractor_value"))
                            .toDouble()
                    val idv_value_trailer =
                        Integer.valueOf(b.getString("pp_agri_trailer_value")).toDouble()
                    dop_value = idv_value_tractor * 1.268 / 100 + idv_value_trailer * 1.268 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                }
            }
        } else if (b.getString("pp_agri_zone") == "B") {
            if (diffInDays < 1825) {
                val idv_value_tractor =
                    Integer.valueOf(b.getString("pp_agri_idv_tractor_value")).toDouble()
                val idv_value_trailer =
                    Integer.valueOf(b.getString("pp_agri_trailer_value")).toDouble()
                dop_value = idv_value_tractor * 1.202 / 100 + idv_value_trailer * 1.202 / 100
                val round_value = dop_value
                rounded_dop_value = Math.round(round_value).toFloat()
            } else if (diffInDays >= 1825) {
                if (diffInDays < 2555) {
                    val idv_value_tractor =
                        Integer.valueOf(b.getString("pp_agri_idv_tractor_value"))
                            .toDouble()
                    val idv_value_trailer =
                        Integer.valueOf(b.getString("pp_agri_trailer_value")).toDouble()
                    dop_value = idv_value_tractor * 1.232 / 100 + idv_value_trailer * 1.232 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                } else if (diffInDays >= 2555) {
                    val idv_value_tractor =
                        Integer.valueOf(b.getString("pp_agri_idv_tractor_value"))
                            .toDouble()
                    val idv_value_trailer =
                        Integer.valueOf(b.getString("pp_agri_trailer_value")).toDouble()
                    dop_value = idv_value_tractor * 1.262 / 100 + idv_value_trailer * 1.262 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                }
            }
        } else if (b.getString("pp_agri_zone") == "C") {
            if (diffInDays < 1825) {
                val idv_value_tractor =
                    Integer.valueOf(b.getString("pp_agri_idv_tractor_value")).toDouble()
                val idv_value_trailer =
                    Integer.valueOf(b.getString("pp_agri_trailer_value")).toDouble()
                dop_value = idv_value_tractor * 1.190 / 100 + idv_value_trailer * 1.190 / 100
                val round_value = dop_value
                rounded_dop_value = Math.round(round_value).toFloat()
            } else if (diffInDays >= 1825) {
                if (diffInDays < 2555) {
                    val idv_value_tractor =
                        Integer.valueOf(b.getString("pp_agri_idv_tractor_value"))
                            .toDouble()
                    val idv_value_trailer =
                        Integer.valueOf(b.getString("pp_agri_trailer_value")).toDouble()
                    dop_value = idv_value_tractor * 1.220 / 100 + idv_value_trailer * 1.220 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                } else if (diffInDays >= 2555) {
                    val idv_value_tractor =
                        Integer.valueOf(b.getString("pp_agri_idv_tractor_value"))
                            .toDouble()
                    val idv_value_trailer =
                        Integer.valueOf(b.getString("pp_agri_trailer_value")).toDouble()
                    dop_value = idv_value_tractor * 1.250 / 100 + idv_value_trailer * 1.250 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter correct DoP", Toast.LENGTH_SHORT)
                .show()
        }

        //Calculation of N/D
        val nd_value = Integer.valueOf(b.getString("pp_agri_nd_value")).toDouble()
        val value_nd1 = nd_value * rounded_dop_value / 100
        val value_nd2 = rounded_dop_value + value_nd1
        rounded_value_nd = Math.round(value_nd2).toFloat()
        val rounded_value_nd_int = rounded_value_nd.toInt()
        pp_agri_nd_value!!.text = b.getCharSequence("pp_agri_nd_value")

        //Calculation of U/W discount
        val uw_value = Integer.valueOf(b.getString("pp_agri_uwd_value")).toDouble()
        val `val` = uw_value * rounded_value_nd / 100
        value_uw1 = rounded_value_nd - `val`
        val value_uw2 = value_uw1
        rounded_uw_value = Math.round(value_uw2).toFloat()
        pp_agri_uwd_value!!.text = uw_value.toInt().toString()

        // Calculation of NCB
        val spinner_value =
            Integer.valueOf(b.getString("pp_agri_spinner_value")).toDouble()
        val spin_val = spinner_value * rounded_uw_value / 100
        value_spin1 = rounded_uw_value - spin_val
        val value_spin2 = value_spin1
        rounded_ncb_value = Math.round(value_spin2).toFloat()
        val rounded_ncb_value_int = rounded_ncb_value.toInt()
        ppdisplay_agri_ncb_value!!.text = spinner_value.toInt().toString()
        ppdisplay_agri_od_value!!.text = rounded_ncb_value_int.toString()


        //Calculation of B
        val pp_agri_tp_basic_tractor = 6847
        val no = "No"
        val pp_agri_tp_basic_trailer: Int
        pp_agri_tp_basic_trailer = if (b.getString("pp_agri_trailer") == no) {
            0
        } else {
            2485
        }
        val pp_agri_pa_owner = ppdisplay_agri_paod_value!!.text.toString().toInt()
        val pp_agri_ll_driver = 50
        val pp_agri_coolie_value = Integer.valueOf(b.getString("pp_agri_coolie"))
        agri_coolie!!.text = (pp_agri_coolie_value * 50).toString()
        val total =
            (pp_agri_tp_basic_tractor + pp_agri_tp_basic_trailer + pp_agri_pa_owner + pp_agri_ll_driver + pp_agri_coolie_value * 50).toDouble()
        total_premium_b = total
        ppdisplay_agri_b_value!!.text = total_premium_b.toInt().toString()
        ppdisplay_agri_tp_trailer_value!!.text = pp_agri_tp_basic_trailer.toString()

        //rounding of total_premium value
        val round_value = total_premium_b + value_spin2
        ppdisplay_agri_ab_value!!.text = round_value.toInt().toString()
        val round_value_total = round_value + round_value * 18 / 100
        val rounded_total_premium = Math.round(round_value_total).toFloat()
        val rounded_total_premium_int = rounded_total_premium.toInt()
        ppdisplay_agri_total_value!!.text = rounded_total_premium_int.toString()
    }

    var listener_ppdisplay_agri_home = View.OnClickListener {
        val intent = Intent(this@ppdisplay_agri, home_activity::class.java)
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
        checkfunction(this@ppdisplay_agri)
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
            p.add(Chunk(": TRACTORS & TRAILERS"))
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
            p.add(Chunk("IDV (TRACTORS)"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + pp_agri_IDV_tractor_value!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("IDV (TRAILERS)"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + ppdisplay_agri_IDV_trailer_value!!.text.toString()))
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
            p.add(Chunk(pp_agri_date_value!!.text.toString()))
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
            p.add(Chunk(pp_agri_zone!!.text.toString()))
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
            p.add(Chunk(pp_agri_nd_value!!.text.toString() + "%"))
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
            p.add(Chunk(pp_agri_uwd_value!!.text.toString()))
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
            p.add(Chunk(ppdisplay_agri_ncb_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + ppdisplay_agri_od_value!!.text.toString(), white))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.BLACK
            pdfPCell.backgroundColor = BaseColor.BLACK
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("TP BASIC (TRACTORS)"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + "6847"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("TP BASIC (TRAILERS)"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + ppdisplay_agri_tp_trailer_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + ppdisplay_agri_paod_value!!.text.toString()))
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
            p.add(Chunk("COOLIE"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + agri_coolie!!.text.toString()))
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
            p.add(Chunk("Rs. " + ppdisplay_agri_b_value!!.text.toString(), white))
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
            p.add(Chunk("Rs. " + ppdisplay_agri_ab_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + ppdisplay_agri_total_value!!.text.toString(), white))
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
                this@ppdisplay_agri,
                BuildConfig.APPLICATION_ID + ".provider",
                file!!
            )
            //Uri uri = Uri.fromFile(file);
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