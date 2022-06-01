package com.avidprogrammers.insurancepremiumcalculator

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
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
class ppdisplay_com_goods_private_12000 : AppCompatActivity(), ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    private var tax_18 = 0.0
    private var tax_12 = 0.0
    var diffInDays: Long = 0
    var dop_value = 0.0
    var rounded_dop_value = 0f
    var rounded_value_nd = 0f
    var rounded_uw_value = 0f
    var rounded_ncb_value = 0f
    var value_uw1 = 0.0
    var value_spin1 = 0.0
    var imt23_value = 0
    var share_btn: CircleButton? = null
    var file: File? = null
    var ppdisplay_com_goods_private_12000_total_idv: TextView? = null
    var pp_com_goods_private_12000_gvw_value: TextView? = null
    var pp_com_goods_private_12000_date_value: TextView? = null
    var pp_com_goods_private_12000_zone: TextView? = null
    var pp_com_goods_private_12000_imt23: TextView? = null
    var pp_com_goods_private_12000_imt23_value: TextView? = null
    var pp_com_goods_private_12000_nd_value: TextView? = null
    var pp_com_goods_private_12000_uwd_value: TextView? = null
    var pp_com_goods_private_12000_ncb_value: TextView? = null
    var pp_com_goods_private_12000_coolie_value: TextView? = null
    var pp_com_goods_private_12000_nfpp_value: TextView? = null
    var pp_com_goods_private_12000_paod_value: TextView? = null
    var ppdisplay_com_goods_private_12000_od_value: TextView? = null
    var ppdisplay_com_goods_private_12000_b_value: TextView? = null
    var ppdisplay_com_goods_private_12000_ab_value: TextView? = null
    var ppdisplay_com_goods_private_12000_tax18_value: TextView? = null
    var ppdisplay_com_goods_private_12000_tax12_value: TextView? = null
    var ppdisplay_com_goods_private_12000_total_value: TextView? = null
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
        checkfunction(this@ppdisplay_com_goods_private_12000)
        setContentView(R.layout.ppdisplay_com_goods_private_12000)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        val b: Bundle = getIntent().getExtras()!!
        pp_com_goods_private_12000_gvw_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_gvw_value) as TextView?
        pp_com_goods_private_12000_date_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_date_value) as TextView?
        pp_com_goods_private_12000_zone =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_zone_value) as TextView?
        pp_com_goods_private_12000_imt23 =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_imt23) as TextView?
        pp_com_goods_private_12000_imt23_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_imt23_value) as TextView?
        pp_com_goods_private_12000_nd_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_nd_value) as TextView?
        pp_com_goods_private_12000_uwd_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_uwd_value) as TextView?
        pp_com_goods_private_12000_ncb_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_ncb_value) as TextView?
        pp_com_goods_private_12000_coolie_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_coolie_value) as TextView?
        pp_com_goods_private_12000_nfpp_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_nfpp_value) as TextView?
        pp_com_goods_private_12000_paod_value =
            findViewById<TextView>(R.id.ppdisplay_com_goods_private_12000_pa_value)
        ppdisplay_com_goods_private_12000_od_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_od_value) as TextView?
        ppdisplay_com_goods_private_12000_b_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_b_value) as TextView?
        ppdisplay_com_goods_private_12000_ab_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_ab_value) as TextView?
        ppdisplay_com_goods_private_12000_tax18_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_tax18_value) as TextView?
        ppdisplay_com_goods_private_12000_tax12_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_tax12_value) as TextView?
        ppdisplay_com_goods_private_12000_total_value =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_total_value) as TextView?
        ppdisplay_com_goods_private_12000_total_idv =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_IDV_value) as TextView?
        ppdisplay_com_goods_private_12000_total_idv!!.text =
            b.getCharSequence("pp_com_goods_private_12000_idv_value")
        pp_com_goods_private_12000_gvw_value!!.text =
            b.getCharSequence("pp_com_goods_private_12000_gvw_value")
        pp_com_goods_private_12000_date_value!!.text =
            b.getCharSequence("pp_com_goods_private_12000_date_value")
        pp_com_goods_private_12000_zone!!.text =
            b.getCharSequence("pp_com_goods_private_12000_zone")
        pp_com_goods_private_12000_nd_value!!.text =
            b.getCharSequence("pp_com_goods_private_12000_nd_value")
        pp_com_goods_private_12000_uwd_value!!.text =
            b.getCharSequence("pp_com_goods_private_12000_uwd_value")
        pp_com_goods_private_12000_ncb_value!!.text =
            b.getCharSequence("pp_com_goods_private_12000_ncb_value")
        pp_com_goods_private_12000_coolie_value!!.text =
            (Integer.valueOf(b.getCharSequence("pp_com_goods_private_12000_coolie").toString())
                .toInt() * 50).toString()
        pp_com_goods_private_12000_paod_value!!.text =
            b.getCharSequence("pp_com_goods_private_12000_paod_value")
        pp_com_goods_private_12000_nfpp_value!!.text =
            (Integer.valueOf(b.getCharSequence("pp_com_goods_private_12000_nfpp").toString())
                .toInt() * 75).toString()
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setTitle("Private Commercial Vehicle - Package Policy" as CharSequence)
        findViewById<View>(R.id.ppdisplay_com_goods_private_12000_home).setOnClickListener(
            listener_ppdisplay_com_goods_private_12000_home
        )
        share_btn =
            findViewById<View>(R.id.ppdisplay_com_goods_private_12000_share) as CircleButton?
        share_btn!!.setOnClickListener {
            val checker = PermissionsChecker(this@ppdisplay_com_goods_private_12000)
            if (checker.lacksPermissions(*PermissionsChecker.REQUIRED_PERMISSION)) {
                PermissionsActivity.startActivityForResult(
                    this@ppdisplay_com_goods_private_12000,
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
        val s = b.getString("pp_com_goods_private_12000_date_value").toString()
        val y = b.getInt("year")
        val m = b.getInt("month")
        val old_day = b.getInt("day")
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        val mYear: Int
        val mMonth: Int
        val mDay: Int
        mYear = calendar1[Calendar.YEAR]
        mMonth = calendar1[Calendar.MONTH]
        mDay = calendar1[Calendar.DAY_OF_MONTH]
        calendar1[mYear, mMonth] = mDay
        calendar2[y, m] = old_day
        val diffInMilis = calendar1.timeInMillis - calendar2.timeInMillis
        val diffInSecond = diffInMilis / 1000
        val diffInMinute = diffInMilis / 60000
        val diffInHour = diffInMilis / 3600000
        diffInDays = diffInMilis / 86400000
        if (b.getString("pp_com_goods_private_12000_zone") == "A") {
            if (diffInDays < 1825) {
                dop_value =
                    1.226 * Integer.valueOf(b.getString("pp_com_goods_private_12000_idv_value"))
                        .toInt().toDouble() / 100.0
                rounded_dop_value = Math.round(dop_value).toFloat()
            } else if (diffInDays >= 1825) {
                if (diffInDays < 2555) {
                    dop_value =
                        1.257 * Integer.valueOf(b.getString("pp_com_goods_private_12000_idv_value"))
                            .toInt().toDouble() / 100.0
                    rounded_dop_value = Math.round(dop_value).toFloat()
                } else if (diffInDays >= 2555) {
                    dop_value =
                        1.287 * Integer.valueOf(b.getString("pp_com_goods_private_12000_idv_value"))
                            .toInt().toDouble() / 100.0
                    rounded_dop_value = Math.round(dop_value).toFloat()
                }
            }
        } else if (b.getString("pp_com_goods_private_12000_zone") == "B") {
            if (diffInDays < 1825) {
                dop_value =
                    1.22 * Integer.valueOf(b.getString("pp_com_goods_private_12000_idv_value"))
                        .toInt().toDouble() / 100.0
                rounded_dop_value = Math.round(dop_value).toFloat()
            } else if (diffInDays >= 1825) {
                if (diffInDays < 2555) {
                    dop_value =
                        1.251 * Integer.valueOf(b.getString("pp_com_goods_private_12000_idv_value"))
                            .toInt().toDouble() / 100.0
                    rounded_dop_value = Math.round(dop_value).toFloat()
                } else if (diffInDays >= 2555) {
                    dop_value =
                        1.281 * Integer.valueOf(b.getString("pp_com_goods_private_12000_idv_value"))
                            .toInt().toDouble() / 100.0
                    rounded_dop_value = Math.round(dop_value).toFloat()
                }
            }
        } else if (b.getString("pp_com_goods_private_12000_zone") == "C") {
            if (diffInDays < 1825) {
                dop_value =
                    1.208 * Integer.valueOf(b.getString("pp_com_goods_private_12000_idv_value"))
                        .toInt().toDouble() / 100.0
                rounded_dop_value = Math.round(dop_value).toFloat()
            } else if (diffInDays >= 1825) {
                if (diffInDays < 2555) {
                    dop_value =
                        1.239 * Integer.valueOf(b.getString("pp_com_goods_private_12000_idv_value"))
                            .toInt().toDouble() / 100.0
                    rounded_dop_value = Math.round(dop_value).toFloat()
                } else if (diffInDays >= 2555) {
                    dop_value =
                        1.268 * Integer.valueOf(b.getString("pp_com_goods_private_12000_idv_value"))
                            .toInt().toDouble() / 100.0
                    rounded_dop_value = Math.round(dop_value).toFloat()
                }
            }
        }
        val intermediate_after_gvw = Math.ceil(
            ((Integer.valueOf(
                b.getCharSequence("pp_com_goods_private_12000_gvw_value").toString()
            ).toInt() - 0).toFloat() / 1.0f).toDouble()
        ) * 1.0 + rounded_dop_value.toDouble()
        val nd_value =
            Integer.valueOf(b.getCharSequence("pp_com_goods_private_12000_nd_value").toString())
                .toInt().toDouble()
        if (nd_value > 0.0) {
            imt23_value = 15
            pp_com_goods_private_12000_imt23_value!!.text = 15.toString()
        } else {
            imt23_value = 0
            (findViewById<View>(R.id.imt23_row) as TableRow).visibility = View.INVISIBLE
        }
        val imt23_total_value =
            intermediate_after_gvw + imt23_value.toDouble() * intermediate_after_gvw / 100.0
        rounded_value_nd =
            Math.round(imt23_total_value + nd_value * imt23_total_value / 100.0).toFloat()
        val rounded_value_nd_int = rounded_value_nd.toInt()
        pp_com_goods_private_12000_nd_value!!.text = imt23_total_value.toString()
        value_uw1 = rounded_value_nd.toDouble() - rounded_value_nd.toDouble() * Integer.valueOf(
            b.getString("pp_com_goods_private_12000_uwd_value")
        ).toInt().toDouble() / 100.0
        rounded_uw_value = Math.round(value_uw1).toFloat()
        pp_com_goods_private_12000_uwd_value!!.text = rounded_uw_value.toString()
        value_spin1 = rounded_uw_value.toDouble() - rounded_uw_value.toDouble() * Integer.valueOf(
            b.getString("pp_com_goods_private_12000_ncb_value")
        ).toInt().toDouble() / 100.0
        rounded_ncb_value = Math.round(value_spin1).toFloat()
        ppdisplay_com_goods_private_12000_od_value!!.text = rounded_ncb_value.toInt().toString()
        val total_b = (Integer.valueOf("17204").toInt() + Integer.valueOf(
            b.getCharSequence("pp_com_goods_private_12000_paod_value").toString()
        ).toInt() + Integer.valueOf("50").toInt() + Integer.valueOf(
            b.getCharSequence("pp_com_goods_private_12000_coolie").toString()
        ).toInt() * 50 + Integer.valueOf(
            b.getCharSequence("pp_com_goods_private_12000_nfpp").toString()
        ).toInt() * 75).toDouble()
        ppdisplay_com_goods_private_12000_b_value!!.text = total_b.toInt().toString()
        val round_value = total_b + rounded_ncb_value.toDouble()

        //To calculate 18% tax
        tax_18 = (round_value - 17204) * Integer.valueOf("18").toInt().toDouble() / 100.0
        val tax_18_final = Math.round(tax_18).toInt()
        ppdisplay_com_goods_private_12000_tax18_value!!.text = tax_18_final.toString()

        //To calculate 12% tax
        tax_12 = 17204 * 0.12
        val tax_12_final = Math.round(tax_12).toInt()
        ppdisplay_com_goods_private_12000_tax12_value!!.text = tax_12_final.toString()

        //int rounded_total_premium_ab_int = (int) new Float((float) Math.round(round_value + ((((double) Integer.valueOf("18").intValue()) * round_value) / 100.0d))).floatValue();
        val rounded_total_premium_ab_int =
            Math.round(round_value + tax_18_final + tax_12_final).toFloat()
                .toInt()
        ppdisplay_com_goods_private_12000_ab_value!!.text = round_value.toInt().toString()
        ppdisplay_com_goods_private_12000_total_value!!.text =
            rounded_total_premium_ab_int.toString()
    }

    var listener_ppdisplay_com_goods_private_12000_home = View.OnClickListener {
        val intent = Intent(this@ppdisplay_com_goods_private_12000, home_activity::class.java)
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
        checkfunction(this@ppdisplay_com_goods_private_12000)
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
            p.add(Chunk(": COMMERCIAL GOODS VEHICLE PRIVATE"))
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
            p.add(Chunk("Rs. " + ppdisplay_com_goods_private_12000_total_idv!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("G V W"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("Rs. " + pp_com_goods_private_12000_gvw_value!!.text.toString()))
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
            p.add(Chunk(pp_com_goods_private_12000_date_value!!.text.toString()))
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
            p.add(Chunk(pp_com_goods_private_12000_zone!!.text.toString()))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk("IMT23"))
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            pdfPCell.addElement(p)
            table.addCell(pdfPCell)
            pdfPCell = PdfPCell()
            pdfPCell.borderColor = BaseColor.WHITE
            table.addCell(pdfPCell)
            p = Paragraph()
            p.add(Chunk(pp_com_goods_private_12000_imt23_value!!.text.toString()))
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
            p.add(Chunk(pp_com_goods_private_12000_nd_value!!.text.toString() + "%"))
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
            p.add(Chunk(pp_com_goods_private_12000_uwd_value!!.text.toString()))
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
            p.add(Chunk(pp_com_goods_private_12000_ncb_value!!.text.toString()))
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
            p.add(
                Chunk(
                    "Rs. " + ppdisplay_com_goods_private_12000_od_value!!.text.toString(),
                    white
                )
            )
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
            p.add(Chunk("Rs. 17204"))
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
            p.add(Chunk("Rs. " + pp_com_goods_private_12000_paod_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + pp_com_goods_private_12000_coolie_value!!.text.toString()))
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
            p.add(Chunk("Rs. " + pp_com_goods_private_12000_nfpp_value!!.text.toString()))
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
            p.add(
                Chunk(
                    "Rs. " + ppdisplay_com_goods_private_12000_b_value!!.text.toString(),
                    white
                )
            )
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
            p.add(Chunk("Rs. " + ppdisplay_com_goods_private_12000_ab_value!!.text.toString()))
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
                    "Rs. " + ppdisplay_com_goods_private_12000_total_value!!.text.toString(),
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
                this@ppdisplay_com_goods_private_12000,
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