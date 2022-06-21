package com.avidprogrammers.insurancepremiumcalculator

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.ads.InterstitialAdManager
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

/**
 * Created by Abhishek on 26-Mar-17.
 */
class pp_taxi_upto6_upto1000 : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    View.OnClickListener, ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    private var mDateDisplay: TextView? = null
    private var mPickDate: Button? = null
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    var pp_taxi_upto6_upto1000_btn: Button? = null
    var pp_taxi_upto6_upto1000_idv_value: EditText? = null
    var pp_taxi_upto6_upto1000_date_value: EditText? = null
    var pp_taxi_upto6_upto1000_cc_value: EditText? = null
    var pp_taxi_upto6_upto1000_lpg_type_value: EditText? = null
    var pp_taxi_upto6_upto1000_nd_value: EditText? = null
    var pp_taxi_upto6_upto1000_ndd_value: EditText? = null
    var pp_taxi_upto6_upto1000_uwd_value: EditText? = null
    var pp_taxi_upto6_scpassengers_upto1000: EditText? = null
    var pp_taxi_upto6_upto1000_paod_value: EditText? = null
    var pp_taxi_upto6_upto1000_zone: RadioGroup? = null
    var pp_taxi_upto6_upto1000_lpg: RadioGroup? = null
    var pp_taxi_upto6_upto1000_lpgtype: RadioGroup? = null
    var pp_taxi_upto6_upto1000_antitheft: RadioGroup? = null
    var pp_taxi_upto6_patooccupants_upto1000: RadioGroup? = null

    //Radiobutton initialisation
    var pp_radiobutton_inbuilt: RadioButton? = null
    var pp_radiobutton_fixed: RadioButton? = null
    var pp_radiobutton_yes_nd: RadioButton? = null
    var pp_radiobutton_no_nd: RadioButton? = null
    var pp_radiobutton_lp_yes: RadioButton? = null
    var pp_radiobutton_lp_no: RadioButton? = null
    var pp_taxi_upto6_upto1000_antitheft_value_yes: RadioButton? = null
    var pp_taxi_upto6_upto1000_antitheft_value_no: RadioButton? = null
    var pp_zone_value =
        arrayOf(doubleArrayOf(3.284, 3.366, 3.448), doubleArrayOf(3.191, 3.271, 3.351))
    var cost = 0
    private var pp_radiobutton_zone_a: RadioButton? = null
    private var pp_radiobutton_zone_b: RadioButton? = null

    @RequiresApi(api = Build.VERSION_CODES.N)
    var ndd: EditText? = null
    var num1 = 0

    private var interstitialAdManager: InterstitialAdManager? = null

    protected override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    override fun onPause() {
        super.onPause()
        interstitialAdManager!!.showInterstitial(this)
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@pp_taxi_upto6_upto1000)
        setContentView(R.layout.pp_taxi_upto6_upto1000)
        getSupportActionBar()!!.setTitle("Taxi Package Policy")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        interstitialAdManager = InterstitialAdManager(this)

        //Date-start
        mDateDisplay = findViewById<View>(R.id.pp_taxi_upto6_upto1000_date_value) as TextView?
        mPickDate = findViewById<View>(R.id.txtbtn) as Button?
        mPickDate!!.setOnClickListener { showDialog(pp_taxi_upto6_upto1000.Companion.DATE_DIALOG_ID) }
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        updateDisplay()
        //Date-end


        //spinner-start
        val spin = findViewById<View>(R.id.pp_taxi_upto6_upto1000_ncb_value) as Spinner
        spin.onItemSelectedListener = this
        val aa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ncb)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = aa
        //spinner-end
        findViewById<View>(R.id.pp_taxi_upto6_upto1000_btn).setOnClickListener(
            listener_pp_taxi_upto6_upto1000_btn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        nd()
        pp_taxi_upto6_upto1000_btn!!.setOnClickListener(this)
        //stop-passthevalues
        dateset()
        onclicklistening()
        ndd = findViewById<View>(R.id.pp_taxi_upto6_upto1000_ndd_value) as EditText?
        ndd!!.addTextChangedListener(textWatcher)
    }

    var listener_pp_taxi_upto6_upto1000_btn = View.OnClickListener {
        val intent = Intent(this@pp_taxi_upto6_upto1000, ppdisplay_taxi_upto6_upto1000::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        pp_taxi_upto6_upto1000_btn = findViewById<View>(R.id.pp_taxi_upto6_upto1000_btn) as Button?
        pp_taxi_upto6_upto1000_idv_value =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_idv_value) as EditText?
        pp_taxi_upto6_upto1000_date_value =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_date_value) as EditText?
        pp_taxi_upto6_upto1000_cc_value =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_cc_value) as EditText?
        pp_taxi_upto6_upto1000_lpg_type_value =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_lpgtype_value) as EditText?
        pp_taxi_upto6_upto1000_nd_value =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_nd_value) as EditText?
        pp_taxi_upto6_upto1000_ndd_value =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_ndd_value) as EditText?
        pp_taxi_upto6_upto1000_uwd_value =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_uwd_value) as EditText?
        pp_taxi_upto6_scpassengers_upto1000 =
            findViewById<View>(R.id.pp_taxi_upto6_scpassengers_upto1000) as EditText?
        pp_taxi_upto6_upto1000_paod_value = findViewById<EditText>(R.id.pp_taxi_upto6_upto1000_paod)
        val scrollview = findViewById<View>(R.id.pp_taxi_upto6_upto1000_sv) as ScrollView
        pp_taxi_upto6_upto1000_uwd_value!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_NEXT) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_taxi_upto6_scpassengers_upto1000!!.requestFocus()
            }
            false
        }
        pp_taxi_upto6_scpassengers_upto1000!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_taxi_upto6_upto1000_btn!!.requestFocus()
            }
            false
        }
        pp_taxi_upto6_upto1000_zone =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_zone) as RadioGroup?
        pp_taxi_upto6_upto1000_lpg =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_lpg) as RadioGroup?
        pp_taxi_upto6_upto1000_lpgtype =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_lpgtype) as RadioGroup?
        pp_taxi_upto6_upto1000_antitheft =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_antitheft) as RadioGroup?

        //radiobutton initialisation
        pp_radiobutton_inbuilt =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_lpgtype_value_inbuilt) as RadioButton?
        pp_radiobutton_fixed =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_lpgtype_value_fixed) as RadioButton?
        pp_radiobutton_yes_nd =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_nd_value_yes) as RadioButton?
        pp_radiobutton_no_nd =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_nd_value_no) as RadioButton?
        pp_radiobutton_lp_yes =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_lpg_value_yes) as RadioButton?
        pp_radiobutton_lp_no =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_lpg_value_no) as RadioButton?
        pp_radiobutton_zone_a =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_idv_value_a) as RadioButton?
        pp_radiobutton_zone_b =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_idv_value_b) as RadioButton?
        pp_taxi_upto6_upto1000_antitheft_value_yes =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_antitheft_value_yes) as RadioButton?
        pp_taxi_upto6_upto1000_antitheft_value_no =
            findViewById<View>(R.id.pp_taxi_upto6_upto1000_antitheft_value_no) as RadioButton?
        pp_radiobutton_fixed!!.isClickable = false
        pp_radiobutton_inbuilt!!.isClickable = false
    }

    private fun onclicklistening() {
        //onclick listener for all buttons
        pp_radiobutton_zone_a!!.setOnClickListener { v ->
            val z = v as RadioButton
            zoneCost = zone_checking(z.text.toString(), score_time_update(mDay, mMonth + 1, mYear))
        }
        pp_radiobutton_zone_b!!.setOnClickListener { v ->
            val z = v as RadioButton
            zoneCost = zone_checking(z.text.toString(), score_time_update(mDay, mMonth + 1, mYear))
        }
        pp_radiobutton_lp_yes!!.setOnClickListener {
            pp_radiobutton_fixed!!.isEnabled = true
            pp_radiobutton_inbuilt!!.isEnabled = true
            pp_radiobutton_fixed!!.alpha = 1f
            pp_radiobutton_inbuilt!!.alpha = 1f
            pp_radiobutton_fixed!!.isClickable = true
            pp_radiobutton_inbuilt!!.isClickable = true
            if (pp_radiobutton_inbuilt!!.isChecked) {
                pp_taxi_upto6_upto1000_lpg_type_value!!.visibility = View.INVISIBLE
            } else if (pp_radiobutton_fixed!!.isChecked) {
                pp_taxi_upto6_upto1000_lpg_type_value!!.visibility = View.VISIBLE
            }
        }
        pp_radiobutton_lp_no!!.setOnClickListener {
            pp_radiobutton_fixed!!.isClickable = false
            pp_radiobutton_inbuilt!!.isClickable = false
            pp_radiobutton_fixed!!.alpha = 0.5.toFloat()
            pp_radiobutton_inbuilt!!.alpha = 0.5.toFloat()
            pp_taxi_upto6_upto1000_lpg_type_value!!.visibility = View.INVISIBLE
        }
        pp_radiobutton_inbuilt!!.setOnClickListener {
            pp_taxi_upto6_upto1000_lpg_type_value!!.visibility = View.INVISIBLE
        }
        pp_radiobutton_fixed!!.setOnClickListener {
            pp_taxi_upto6_upto1000_lpg_type_value!!.visibility = View.VISIBLE
        }
        val pa_no: RadioButton =
            findViewById<RadioButton>(R.id.pp_taxi_upto6_upto1000_paod_value_no)
        pa_no.setOnClickListener {
            pp_taxi_upto6_upto1000_paod_value!!.setText("0")
            pp_taxi_upto6_upto1000_paod_value!!.isEnabled = false
        }
        val pa_yes: RadioButton =
            findViewById<RadioButton>(R.id.pp_taxi_upto6_upto1000_paod_value_yes)
        pa_yes.setOnClickListener {
            pp_taxi_upto6_upto1000_paod_value!!.setText("320")
            pp_taxi_upto6_upto1000_paod_value!!.isEnabled = true
        }
        pp_radiobutton_yes_nd!!.setOnClickListener { nd() }
        pp_radiobutton_no_nd!!.setOnClickListener { nd() }
    }

    fun dateset() {
        if (pp_radiobutton_lp_yes!!.isChecked) {
            if (pp_radiobutton_inbuilt!!.isChecked) {
                pp_taxi_upto6_upto1000_lpg_type_value!!.visibility = View.INVISIBLE
            }
        }
        nd()
    }

    fun nd() {
        val x = score_time_update_nd(mDay, mMonth + 1, mYear)
        if (pp_radiobutton_yes_nd!!.isChecked) {
            if (x < 182) {
                pp_taxi_upto6_upto1000_nd_value!!.setText("10", TextView.BufferType.EDITABLE)
            } else if (x in 182..729) {
                pp_taxi_upto6_upto1000_nd_value!!.setText("20", TextView.BufferType.EDITABLE)
            } else if (x in 730..1824) {
                pp_taxi_upto6_upto1000_nd_value!!.setText("30", TextView.BufferType.EDITABLE)
            } else if (x >= 1825) {
                pp_taxi_upto6_upto1000_nd_value!!.setText("0", TextView.BufferType.EDITABLE)
            }
        } else {
            pp_taxi_upto6_upto1000_nd_value!!.setText("0", TextView.BufferType.EDITABLE)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    fun score_time_update_nd(x: Int, y: Int, z: Int): Long {
        //Toast.makeText(pp_bus_upto1000.this, ""+String.valueOf(z)+"-"+String.valueOf(x)+"-"+String.valueOf(y), Toast.LENGTH_SHORT).show();
        val date1 = Calendar.getInstance()
        val date2 = Calendar.getInstance()
        val c = Calendar.getInstance()
        date1.clear()
        date1[z, y] = x
        date2.clear()
        date2[c[Calendar.YEAR], c[Calendar.MONTH] + 1] = c[Calendar.DAY_OF_MONTH]
        val msdiff = -date2.timeInMillis + date1.timeInMillis
        var daydiff = TimeUnit.MILLISECONDS.toDays(msdiff)
        daydiff = Math.abs(daydiff)
        return daydiff
    }


    fun zone_checking(x: String, l: Long): Double {
        if (l < 5) {
            if (x == "A") {
                return 3.284
            } else if (x == "B") {
                return 3.191
            }
        } else if (l >= 5 && l < 7) {
            if (x == "A") {
                return 3.366
            } else if (x == "B") {
                return 3.271
            }
        } else if (l >= 7) {
            if (x == "A") {
                return 3.448
            } else if (x == "B") {
                return 3.351
            }
        }
        return 0.0
    }

    var zoneCost = 0.0
    var Totalcost = 0.0
    fun score_time_update(x: Int, y: Int, z: Int): Long {
        //Toast.makeText(pp_taxi_upto6_upto1000.this, ""+String.valueOf(z)+"-"+String.valueOf(x)+"-"+String.valueOf(y), Toast.LENGTH_SHORT).show();
        val date1 = Calendar.getInstance()
        val date2 = Calendar.getInstance()
        val c = Calendar.getInstance()
        date1.clear()
        date1[z, y] = x
        date2.clear()
        date2[c[Calendar.YEAR], c[Calendar.MONTH] + 1] = c[Calendar.DAY_OF_MONTH]
        val msdiff = -date2.timeInMillis + date1.timeInMillis
        var daydiff = TimeUnit.MILLISECONDS.toDays(msdiff)
        daydiff = Math.abs(daydiff)
        return daydiff / 365
    }

    fun calculation_begins() {
        var Totalcost = 0.0
        if (pp_taxi_upto6_upto1000_idv_value!!.text.toString() != "") {
            Totalcost = pp_taxi_upto6_upto1000_idv_value!!.text.toString().toInt().toDouble()
            val s: String
            s = if (pp_radiobutton_zone_a!!.isChecked) {
                "A"
            } else "B"
            zoneCost = zone_checking(s, score_time_update(mDay, mMonth + 1, mYear))
            Totalcost *= zoneCost / 100
            if (pp_radiobutton_lp_yes!!.isChecked) {
                lpg_kit = 60
                radiobuttonsel(Totalcost)
            } else if (pp_radiobutton_lp_no!!.isChecked) {
                lpg_kit = 0
                nd_radiobutton(Totalcost)
            }
        } else {
            pp_taxi_upto6_upto1000_idv_value!!.error = "Enter the value for IDV !"
        }
    }

    fun radiobuttonsel(z: Double) {
        var z = z
        if (pp_radiobutton_inbuilt!!.isChecked) {
            nd_radiobutton(z)
        } else if (pp_radiobutton_fixed!!.isChecked) {
            if (pp_taxi_upto6_upto1000_lpg_type_value!!.text.toString().isEmpty()) {
                pp_taxi_upto6_upto1000_lpg_type_value!!.error = "Fill the details !"
            } else {
                val x = pp_taxi_upto6_upto1000_lpg_type_value!!.text.toString().toInt()
                z = z + x * 4 / 100
                nd_radiobutton(z)
            }
        }
    }

    fun nd_radiobutton(l: Double) {
        val x = score_time_update_nd(mDay, mMonth + 1, mYear)
        Totalcost = l
        if (pp_radiobutton_yes_nd!!.isChecked) {
            if (x <= 182) {
                Totalcost += Totalcost * 0.10
            } else if (x in 182..729) {
                Totalcost += Totalcost * 0.20
            } else if (x in 730..1824) {
                Totalcost += Totalcost * 0.30
            } else if (x >= 1825) {
                Totalcost += 0.0
            }
        } else {
            Totalcost += 0.0
        }
        anti_radiobutton(Totalcost)
    }

    fun anti_radiobutton(matter: Double) {
        Totalcost = matter
        if (pp_taxi_upto6_upto1000_antitheft_value_yes!!.isChecked) {
            Totalcost -= if (Totalcost * 0.025 > 500) {
                500.0
            } else {
                Totalcost * 0.025
            }
        }
        UWdiscount(Totalcost)
    }

    fun UWdiscount(s: Double) {
        Totalcost = s
        if (pp_taxi_upto6_upto1000_uwd_value!!.text.toString() == "") {
            pp_taxi_upto6_upto1000_uwd_value!!.error = "Fill U/W Discount ! "
        } else {
            Totalcost -= pp_taxi_upto6_upto1000_uwd_value!!.text.toString()
                .toInt() * Totalcost / 100
            ncb(Totalcost)
        }
    }

    fun ncb(cost: Double) {
        var cost = cost
        cost -= cost * ncb[pp_taxi_upto6_upto1000.Companion.pos].toInt() / 100
        pp_taxi_upto6_upto1000.Companion.cost_total = cost
    }

    var lpg_kit = 0
    override fun onClick(v: View) {
        if (pp_taxi_upto6_upto1000_idv_value!!.text.toString().isEmpty() ||
            pp_taxi_upto6_upto1000_uwd_value!!.text.toString().isEmpty() ||
            pp_taxi_upto6_scpassengers_upto1000!!.text.toString().isEmpty() ||
            pp_radiobutton_lp_yes!!.isChecked && pp_radiobutton_fixed!!.isChecked && pp_taxi_upto6_upto1000_lpg_type_value!!.text.toString()
                .isEmpty()
        ) {
            val bar =
                Snackbar.make(v, "Please enter all fields to Calculate!", Snackbar.LENGTH_LONG)
            val mainTextView =
                bar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) mainTextView.textAlignment =
                View.TEXT_ALIGNMENT_CENTER else mainTextView.gravity = Gravity.CENTER_HORIZONTAL
            mainTextView.gravity = Gravity.CENTER_HORIZONTAL

            /*                   .setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Handle user action
                        }
                    });*/bar.setActionTextColor(getResources().getColor(R.color.colorSnackBarDismiss))
            val tv =
                bar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            tv.setTextColor(getResources().getColor(R.color.colorSnackBar))
            val view = bar.view
            view.setBackgroundColor(getResources().getColor(R.color.colorSnackBarBg))
            bar.show()
        } else {
            calculation_begins()
            val intent = Intent(getApplicationContext(), ppdisplay_taxi_upto6_upto1000::class.java)
            //Create a bundle object
            val b = Bundle()
            //Inserts a String value into the mapping of this Bundle
            b.putString(
                "pp_taxi_upto6_upto1000_idv_value",
                pp_taxi_upto6_upto1000_idv_value!!.text.toString()
            )
            b.putString(
                "pp_taxi_upto6_upto1000_date_value",
                pp_taxi_upto6_upto1000_date_value!!.text.toString()
            )
            b.putString(
                "pp_taxi_upto6_upto1000_cc_value",
                pp_taxi_upto6_upto1000_cc_value!!.text.toString()
            )
            b.putString(
                "pp_taxi_upto6_upto1000_nd_value",
                pp_taxi_upto6_upto1000_nd_value!!.text.toString()
            )
            b.putString(
                "pp_taxi_upto6_upto1000_ndd_value",
                pp_taxi_upto6_upto1000_ndd_value!!.text.toString()
            )
            b.putString(
                "pp_taxi_upto6_upto1000_uwd_value",
                pp_taxi_upto6_upto1000_uwd_value!!.text.toString()
            )
            b.putString(
                "pp_taxi_upto6_upto1000_paod_value",
                pp_taxi_upto6_upto1000_paod_value!!.text.toString()
            )
            b.putString(
                "pp_taxi_upto6_scpassengers_upto1000", (Integer.valueOf(
                    pp_taxi_upto6_scpassengers_upto1000!!.text.toString()
                ) * 1162).toString()
            )
            b.putString(
                "pp_taxi_upto6_upto1000_od_value",
                pp_taxi_upto6_upto1000.Companion.cost_total.toString()
            )
            b.putString(
                "pp_taxi_upto6_upto1000_ncb_value",
                ncb[pp_taxi_upto6_upto1000.Companion.pos]
            )
            b.putString("pp_taxi_upto6_upto1000_lpgkit_value", lpg_kit.toString())
            val id1 = pp_taxi_upto6_upto1000_zone!!.checkedRadioButtonId
            val radioButton1 = findViewById<View>(id1) as RadioButton
            b.putString("pp_taxi_upto6_upto1000_zone", radioButton1.text.toString())
            val id2 = pp_taxi_upto6_upto1000_lpg!!.checkedRadioButtonId
            val radioButton2 = findViewById<View>(id2) as RadioButton
            b.putString("pp_taxi_upto6_upto1000_lpg", radioButton2.text.toString())

            // Checking the lpg type based on yes and no properties
            // to enable and disable the options of lpg type
            val id3 = pp_taxi_upto6_upto1000_lpgtype!!.checkedRadioButtonId
            val radioButton3 = findViewById<View>(id3) as RadioButton
            if (pp_radiobutton_lp_no!!.isChecked) {
                b.putString("pp_taxi_upto6_upto1000_lpgtype", "-")
            } else {
                b.putString("pp_taxi_upto6_upto1000_lpgtype", radioButton3.text.toString())
            }
            val id4 = pp_taxi_upto6_upto1000_antitheft!!.checkedRadioButtonId
            val radioButton4 = findViewById<View>(id4) as RadioButton
            b.putString("pp_taxi_upto6_upto1000_antitheft", radioButton4.text.toString())

            //Add the bundle to the intent.
            intent.putExtras(b)
            //start the DisplayActivity
            startActivity(intent)
        }
    }

    //stop-passthevalues
    //Date-start
    private fun updateDisplay() {
        mDateDisplay!!.text = StringBuilder() // Month is 0 based so add 1
            .append(mDay).append("-")
            .append(mMonth + 1).append("-")
            .append(mYear).append(" ")
    }

    private val mDateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        updateDisplay()
        nd()
        //score_time_update(mYear);
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            pp_taxi_upto6_upto1000.Companion.DATE_DIALOG_ID -> return DatePickerDialog(
                this,
                mDateSetListener,
                mYear, mMonth, mDay
            )
        }
        return null!!
    }

    //Date-end
    //BackButton in title bar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    //Spinner
    var ncb = arrayOf("0", "20", "25", "35", "45", "50")

    //Performing action onItemSelected and onNothing selected
    override fun onItemSelected(arg0: AdapterView<*>?, arg1: View, position: Int, id: Long) {
        //Toast.makeText(getApplicationContext(), ncb[position], Toast.LENGTH_LONG).show();
        pp_taxi_upto6_upto1000.Companion.pos = position
    }

    override fun onNothingSelected(arg0: AdapterView<*>?) {
// TODO Auto-generated method stub
    }

    //To check NDD value
    var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (ndd!!.text.length > 0) {
                val num = ndd!!.text.toString().toInt()
                num1 = if (num >= 0 && num <= 65) {
                    //save the number
                    num
                } else {
                    Toast.makeText(
                        this@pp_taxi_upto6_upto1000,
                        "NDD range should be of 1-65",
                        Toast.LENGTH_SHORT
                    ).show()
                    ndd!!.setText("")
                    -1
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    //this function checks for date and provides the zone percentage
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

    companion object {
        private const val TAG = "pp_taxi_upto6_upto1000"
        const val DATE_DIALOG_ID = 0
        var cost_total = 0.0
        var pos = 0
    }
}