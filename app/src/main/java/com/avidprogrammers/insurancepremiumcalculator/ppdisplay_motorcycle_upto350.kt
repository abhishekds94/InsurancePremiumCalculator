package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import android.widget.Toast
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
class ppdisplay_motorcycle_upto350 : AppCompatActivity(), ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var diffInDays: Long = 0
    var dop_value = 0.0
    var pp_lgkit_assumevalue = 0
    var pp_scpass_value = 0
    var pp_pa_pass_value = 0
    var rounded_dop_value = 0f
    var pp_tax_value = 18
    var value_nd = 0f
    var rounded_lgptype_value = 0f
    var rounded_value_nd = 0f
    var rounded_value_antitheft = 0f
    var rounded_uw_value = 0f
    var rounded_ncb_value = 0f
    var value_lgptype1 = 0.0
    var nild_value = 0.0
    var value_uw1 = 0.0
    var value_spin1 = 0.0
    var value_ndd = 0.0
    var rounded_ndd_value = 0.0
    var value_lgptype2 = 0.0
    var pp_total_premium = 0.0
    var share_btn: CircleButton? = null
    var file: File? = null
    var pp_motorcycle_upto350_IDV_value: TextView? = null
    var pp_motorcycle_upto350_date_value: TextView? = null
    var pp_motorcycle_upto350_zone_value: TextView? = null
    var pp_motorcycle_upto350_cc_value: TextView? = null
    var pp_motorcycle_upto350_nd_value: TextView? = null
    var pp_motorcycle_upto350_ndd_value: TextView? = null
    var pp_motorcycle_upto350_uwd_value: TextView? = null
    var pp_motorcycle_upto350_ncb_value: TextView? = null
    var pp_motorcycle_upto350_od_value: TextView? = null
    var pp_motorcycle_upto350_ab_value: TextView? = null
    var pp_motorcycle_upto350_b_value: TextView? = null
    var ppdisplay_motorcycle_upto350_paod_value: TextView? = null
    var ppdisplay_motorcycle_upto350_total_value: TextView? = null
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
        checkfunction(this@ppdisplay_motorcycle_upto350)
        setContentView(R.layout.ppdisplay_motorcycle_upto350)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        val b: Bundle = getIntent().getExtras()!!
        pp_motorcycle_upto350_IDV_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_IDV_value) as TextView?
        pp_motorcycle_upto350_date_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_date_value) as TextView?
        pp_motorcycle_upto350_zone_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_zone_value) as TextView?
        pp_motorcycle_upto350_cc_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_cc_value) as TextView?
        pp_motorcycle_upto350_nd_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_nd_value) as TextView?
        pp_motorcycle_upto350_ndd_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_ndd_value) as TextView?
        pp_motorcycle_upto350_uwd_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_uwd_value) as TextView?
        pp_motorcycle_upto350_ncb_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_ncb_value) as TextView?
        pp_motorcycle_upto350_od_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_od_value) as TextView?
        pp_motorcycle_upto350_ab_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_ab_value) as TextView?
        pp_motorcycle_upto350_b_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_b_value) as TextView?
        ppdisplay_motorcycle_upto350_paod_value =
            findViewById<TextView>(R.id.ppdisplay_motorcycle_upto350_pa_value)
        ppdisplay_motorcycle_upto350_total_value =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_total_value) as TextView?
        share_btn =
            findViewById<View>(R.id.ppdisplay_motorcycle_upto350_share) as CircleButton?
        pp_motorcycle_upto350_IDV_value!!.text =
            b.getCharSequence("pp_motorcycle_upto350_idv_value")
        pp_motorcycle_upto350_date_value!!.text =
            b.getCharSequence("pp_motorcycle_upto350_date_value")
        pp_motorcycle_upto350_zone_value!!.text = b.getCharSequence("pp_motorcycle_upto350_zone")
        pp_motorcycle_upto350_cc_value!!.text = b.getCharSequence("pp_motorcycle_upto350_cc_value")
        pp_motorcycle_upto350_nd_value!!.text = b.getCharSequence("pp_motorcycle_upto350_nd_value")
        pp_motorcycle_upto350_ndd_value!!.text =
            b.getCharSequence("pp_motorcycle_upto350_ndd_value")
        pp_motorcycle_upto350_uwd_value!!.text =
            b.getCharSequence("pp_motorcycle_upto350_uwd_value")
        ppdisplay_motorcycle_upto350_paod_value!!.text =
            b.getCharSequence("pp_motorcycle_upto350_paod_value")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Motorcycle Package Policy")
        share_btn!!.setOnClickListener {
            val checker = PermissionsChecker(this@ppdisplay_motorcycle_upto350)
            if (checker.lacksPermissions(*PermissionsChecker.REQUIRED_PERMISSION)) {
                PermissionsActivity.startActivityForResult(
                    this@ppdisplay_motorcycle_upto350,
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
        findViewById<View>(R.id.ppdisplay_motorcycle_upto350_home).setOnClickListener(
            listener_ppdisplay_motorcycle_upto350_home
        )


        //Calculation
        val d1 = b.getString("pp_motorcycle_upto350_date_value").toString()
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
        if (b.getString("pp_motorcycle_upto350_zone") == "A") {
            if (diffInDays < 1825) {
                val idv_value =
                    Integer.valueOf(b.getString("pp_motorcycle_upto350_idv_value"))
                        .toDouble()
                dop_value = idv_value * 1.793 / 100
                val round_value = dop_value
                rounded_dop_value = Math.round(round_value).toFloat()
            } else if (diffInDays >= 1825) {
                if (diffInDays < 3650) {
                    val idv_value =
                        Integer.valueOf(b.getString("pp_motorcycle_upto350_idv_value"))
                            .toDouble()
                    dop_value = idv_value * 1.883 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                } else if (diffInDays >= 3650) {
                    val idv_value =
                        Integer.valueOf(b.getString("pp_motorcycle_upto350_idv_value"))
                            .toDouble()
                    dop_value = idv_value * 1.928 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                }
            }
        } else if (b.getString("pp_motorcycle_upto350_zone") == "B") {
            if (diffInDays < 1825) {
                val idv_value =
                    Integer.valueOf(b.getString("pp_motorcycle_upto350_idv_value"))
                        .toDouble()
                dop_value = idv_value * 1.760 / 100
                val round_value = dop_value
                rounded_dop_value = Math.round(round_value).toFloat()
            } else if (diffInDays >= 1825) {
                if (diffInDays < 3650) {
                    val idv_value =
                        Integer.valueOf(b.getString("pp_motorcycle_upto350_idv_value"))
                            .toDouble()
                    dop_value = idv_value * 1.848 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                } else if (diffInDays >= 3650) {
                    val idv_value =
                        Integer.valueOf(b.getString("pp_motorcycle_upto350_idv_value"))
                            .toDouble()
                    dop_value = idv_value * 1.892 / 100
                    val round_value = dop_value
                    rounded_dop_value = Math.round(round_value).toFloat()
                }
            }
        } else {
            Toast.makeText(
                getApplicationContext(),
                "Please enter correct Date",
                Toast.LENGTH_SHORT
            ).show()
        }
        //Calculation of ND value
        var nd_value1 = 0.0
        val radio_button_value = b.getString("pp_motorcycle_upto350_nd")
        if (radio_button_value == "NO") {
            rounded_value_nd = rounded_dop_value
        } else {
            if (diffInDays < 182) {
                nd_value1 = 10.0
            } else if (diffInDays in 182..729) {
                nd_value1 = 20.0
            } else if (diffInDays in 730..1824) {
                nd_value1 = 30.0
            } else if (diffInDays >= 1825) {
                nd_value1 = 0.0
            }

            //  double nd_value1 = Integer.valueOf(b.getString("pp_motorcycle_upto75_nd_value"));
            val value_nd1 = nd_value1 * rounded_dop_value / 100
            val value_nd2 = rounded_dop_value + value_nd1
            nild_value = Math.round(value_nd1).toDouble()
            rounded_value_nd = Math.round(value_nd2).toFloat()
        }
        val rounded_value_nd_int = rounded_value_nd.toInt()
        if (nd_value1 == 0.0) {
            pp_motorcycle_upto350_nd_value!!.text = "0"
        } else {
            pp_motorcycle_upto350_nd_value!!.text = b.getString("pp_motorcycle_upto350_nd_value")
            //pp_motorcycle_upto150_nd_value.setText(String.valueOf(rounded_value_nd_int));
        }
        //Calculation of U/W discount
        val uw_value =
            Integer.valueOf(b.getString("pp_motorcycle_upto350_uwd_value")).toDouble()
        val `val` = uw_value * rounded_dop_value / 100
        value_uw1 = rounded_value_nd - `val`
        val value_uw2 = value_uw1
        rounded_uw_value = Math.round(value_uw2).toFloat()


        //Calculation of ND discount
        val ndd_value =
            Integer.valueOf(b.getString("pp_motorcycle_upto350_ndd_value")).toDouble()
        val ndd = ndd_value * nild_value / 100
        value_ndd = rounded_value_nd - ndd
        val value_ndd2 = value_ndd
        rounded_ndd_value = Math.round(value_ndd2).toDouble()


        // Calculation of NCB
        val spinner_value =
            Integer.valueOf(b.getString("pp_motorcycle_upto350_spinner_value"))
                .toDouble()
        val spin_val = spinner_value * rounded_ndd_value / 100
        value_spin1 = rounded_ndd_value - spin_val
        val value_spin2 = value_spin1
        rounded_ncb_value = Math.round(value_spin2).toFloat()
        val rounded_ncb_value_int = rounded_ncb_value.toInt()
        pp_motorcycle_upto350_ncb_value!!.text = b.getString("pp_motorcycle_upto350_spinner_value")
        //pp_motorcycle_upto150_ncb_value.setText(String.valueOf(rounded_ncb_value_int));
        pp_motorcycle_upto350_od_value!!.text = rounded_ncb_value_int.toString()

        //Calculation of B part
        pp_total_premium =
            (1366 + Integer.valueOf(ppdisplay_motorcycle_upto350_paod_value!!.text.toString())).toDouble()
        val total = (1366 + Integer.valueOf(
            ppdisplay_motorcycle_upto350_paod_value!!.text.toString()
        )).toDouble()
        pp_total_premium = total


        //rounding of total_premium value
        val round_value = pp_total_premium
        val rounded_total_premium = Math.round(round_value).toFloat()
        val rounded_total_premium_int = rounded_total_premium.toInt()
        pp_motorcycle_upto350_b_value!!.text = rounded_total_premium_int.toString()
        val total_ab = rounded_total_premium.toDouble() + rounded_ncb_value.toDouble()
        val total_ab_int = total_ab.toInt()
        pp_motorcycle_upto350_ab_value!!.text = total_ab_int.toString()
        val total_plus_service_tax = total_ab + total_ab * 18 / 100
        val rounded_total_AB = Math.round(total_plus_service_tax).toFloat()
        val rounded_total_AB_int = rounded_total_AB.toInt()
        ppdisplay_motorcycle_upto350_total_value!!.text = rounded_total_AB_int.toString()
    }

    var listener_ppdisplay_motorcycle_upto350_home = View.OnClickListener {
        val intent = Intent(this@ppdisplay_motorcycle_upto350, home_activity::class.java)
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
        checkfunction(this@ppdisplay_motorcycle_upto350)
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
            p.add(Chunk(": MOTORCYCLE"))
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
            p.add(Chunk("Rs. " + pp_motorcycle_upto350_IDV_value!!.text.toString()))
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
            p.add(Chunk(pp_motorcycle_upto350_date_value!!.text.toString()))
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
            p.add(Chunk(pp_motorcycle_upto350_zone_value!!.text.toString()))
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
            p.add(Chunk(pp_motorcycle_upto350_cc_value!!.text.toString()))
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
            p.add(Chunk(pp_motorcycle_upto350_nd_value!!.text.toString()))
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
            p.add(Chunk(pp_motorcycle_upto350_ndd_value!!.text.toString()))
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
            p.add(Chunk(pp_motorcycle_upto350_uwd_value!!.text.toString()))
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
            p.add(Chunk(pp_motorcycle_upto350_ncb_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + pp_motorcycle_upto350_od_value!!.text.toString(), white))
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
            p.add(Chunk("Rs. " + "1366"))
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
            p.add(Chunk("Rs. " + ppdisplay_motorcycle_upto350_paod_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + pp_motorcycle_upto350_b_value!!.text.toString(), white))
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
            p.add(Chunk("Rs. " + pp_motorcycle_upto350_ab_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + ppdisplay_motorcycle_upto350_total_value!!.text.toString(), white))
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
                this@ppdisplay_motorcycle_upto350,
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