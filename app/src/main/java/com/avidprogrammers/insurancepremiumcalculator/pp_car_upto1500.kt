package com.avidprogrammers.insurancepremiumcalculator

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.ads.InterstitialAdManager
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import com.google.android.material.snackbar.Snackbar
import java.util.*

/**
 * Created by Abhishek on 26-Mar-17.
 */
class pp_car_upto1500 : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    View.OnClickListener, ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    var selected: String? = null
    private var mDateDisplay: TextView? = null
    private var mPickDate: Button? = null
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    var diffInDays: Long = 0
    var pp_car_upto1500_btn: Button? = null
    var pp_car_upto1500_idv_value: EditText? = null
    var pp_car_upto1500_date_value: EditText? = null
    var pp_car_upto1500_cc_value: EditText? = null
    var pp_car_upto1500_nd_value: EditText? = null
    var pp_car_upto1500_ndd_value: EditText? = null
    var pp_car_upto1500_uwd_value: EditText? = null
    var pp_car_scpassengers_upto1500: EditText? = null
    var pp_car_upto1500_lpgtype_value: EditText? = null
    var pp_car_upto1500_paod_value: EditText? = null
    var pp_car_upto1500_zone: RadioGroup? = null
    var pp_car_upto1500_lpg: RadioGroup? = null
    var pp_car_upto1500_lpgtype: RadioGroup? = null
    var pp_car_upto1500_antitheft: RadioGroup? = null
    var pp_car_patooccupants_upto1500: RadioGroup? = null
    var pp_car_upto1500_nd: RadioGroup? = null
    var radioButton2: RadioButton? = null
    var radioButton3: RadioButton? = null
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
        checkfunction(this@pp_car_upto1500)
        setContentView(R.layout.pp_car_upto1500)
        getSupportActionBar()!!.setTitle("Car Package Policy")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        interstitialAdManager = InterstitialAdManager(this)

        val pa_no: RadioButton = findViewById<RadioButton>(R.id.pp_car_upto1500_paod_value_no)
        pa_no.setOnClickListener {
            pp_car_upto1500_paod_value!!.setText("0")
            pp_car_upto1500_paod_value!!.isEnabled = false
        }
        val pa_yes: RadioButton = findViewById<RadioButton>(R.id.pp_car_upto1500_paod_value_yes)
        pa_yes.setOnClickListener {
            pp_car_upto1500_paod_value!!.setText(R.string.paod_val_s)
            pp_car_upto1500_paod_value!!.isEnabled = true
        }
        val lpg_no = findViewById<View>(R.id.pp_car_upto1500_lpg_value_no) as RadioButton
        lpg_no.setOnClickListener(no_lpg_listener)
        val lpg_yes = findViewById<View>(R.id.pp_car_upto1500_lpg_value_yes) as RadioButton
        lpg_yes.setOnClickListener(yes_lpg_listener)
        val nd_no = findViewById<View>(R.id.pp_car_upto1500_nd_value_no) as RadioButton
        nd_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_car_upto1500_nd_value) as EditText
            ed1.isEnabled = false
            ed1.setText("0")
        }
        val nd_yes = findViewById<View>(R.id.pp_car_upto1500_nd_value_yes) as RadioButton
        nd_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_car_upto1500_nd_value) as EditText
            val diffInDays = CalculateDifferenceInDays()
            var nd_value1 = 0.00
            if (diffInDays < 182) {
                nd_value1 = 10.0
            } else if (diffInDays in 182..729) {
                nd_value1 = 20.0
            } else if (diffInDays in 730..1824) {
                nd_value1 = 30.0
            } else if (diffInDays >= 1825) {
                nd_value1 = 0.0
            }
            val nd_value1_int = nd_value1.toInt()
            ed1.setText(nd_value1_int.toString())
            ed1.isEnabled = false
            ed1.visibility = View.VISIBLE
            val nd_sym = findViewById<View>(R.id.nd_sym) as TextView
            nd_sym.visibility = View.VISIBLE
        }
        val rb1 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value_fixed) as RadioButton
        val rb2 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value_inbuilt) as RadioButton
        rb1.isEnabled = false
        rb2.isEnabled = false
        rb2.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value) as EditText
            ed1.visibility = View.INVISIBLE
            val lpg_sym = findViewById<View>(R.id.lpg_sym) as TextView
            lpg_sym.visibility = View.INVISIBLE
        }
        rb1.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value) as EditText
            ed1.visibility = View.VISIBLE
            val lpg_sym = findViewById<View>(R.id.lpg_sym) as TextView
            lpg_sym.visibility = View.VISIBLE
            ed1.isEnabled = true
        }
        //Date-start
        mDateDisplay = findViewById<View>(R.id.pp_car_upto1500_date_value) as TextView?
        mPickDate = findViewById<View>(R.id.pp_car_upto1500_date_btn) as Button?
        mPickDate!!.setOnClickListener { showDialog(pp_car_upto1500.Companion.DATE_DIALOG_ID) }
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        updateDisplay()
        //Date-end


        //spinner-start
        val spin = findViewById<View>(R.id.pp_car_upto1500_ncb_value) as Spinner
        spin.onItemSelectedListener = this
        val aa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ncb)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = aa
        //spinner-end
        findViewById<View>(R.id.pp_car_upto1500_btn).setOnClickListener(listener_pp_car_upto1500_btn)


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        pp_car_upto1500_btn!!.setOnClickListener(this)
        //stop-passthevalues
        ndd = findViewById<View>(R.id.pp_car_upto1500_ndd_value) as EditText?
        ndd!!.addTextChangedListener(textWatcher)
    }

    var no_lpg_listener = View.OnClickListener {
        val rb1 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value_fixed) as RadioButton
        val rb2 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value_inbuilt) as RadioButton
        rb1.isClickable = false
        rb2.isClickable = false
        rb1.alpha = 0.5.toFloat()
        rb2.alpha = 0.5.toFloat()
        val ed1 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value) as EditText
        ed1.isEnabled = false
        ed1.visibility = View.INVISIBLE
    }
    var yes_lpg_listener = View.OnClickListener {
        val rb1 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value_fixed) as RadioButton
        val rb2 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value_inbuilt) as RadioButton
        rb1.isEnabled = true
        rb2.isEnabled = true
        rb1.isClickable = true
        rb2.isClickable = true
        rb1.alpha = 1f
        rb2.alpha = 1f
        rb2.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value) as EditText
            ed1.visibility = View.INVISIBLE
            val lpg_sym = findViewById<View>(R.id.lpg_sym) as TextView
            lpg_sym.visibility = View.INVISIBLE
        }
        rb1.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value) as EditText
            ed1.visibility = View.VISIBLE
            val lpg_sym = findViewById<View>(R.id.lpg_sym) as TextView
            lpg_sym.visibility = View.VISIBLE
            ed1.isEnabled = true
        }
    }
    var yes_nd_listener = View.OnClickListener {
        val ed1 = findViewById<View>(R.id.pp_car_upto1500_nd_value) as EditText
        val diffInDays = CalculateDifferenceInDays()
        var nd_value1 = 0.00
        if (diffInDays < 365) {
            nd_value1 = 15.0
        } else if (diffInDays >= 365 && diffInDays < 730) {
            nd_value1 = 25.0
        } else if (diffInDays >= 730 && diffInDays < 1825) {
            nd_value1 = 35.0
        } else if (diffInDays >= 1825 && diffInDays < 3650) {
            nd_value1 = 40.0
        } else if (diffInDays >= 3650) {
            nd_value1 = 0.0
        }
        val nd_value1_int = nd_value1.toInt()
        ed1.setText(nd_value1_int.toString())
        ed1.isEnabled = false
    }
    var listener_pp_car_upto1500_btn = View.OnClickListener {
        val intent = Intent(this@pp_car_upto1500, ppdisplay_car_upto1500::class.java)
        startActivity(intent)
    }

    fun CalculateDifferenceInDays(): Long {
        val mYear_now: Int
        val mMonth_now: Int
        val mDay_now: Int
        // Create Calendar instance
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        mYear_now = calendar1[Calendar.YEAR]
        mMonth_now = calendar1[Calendar.MONTH]
        mDay_now = calendar1[Calendar.DAY_OF_MONTH]

        // Set the values for the calendar fields YEAR, MONTH, and DAY_OF_MONTH.
        calendar2[mYear, mMonth] = mDay
        calendar1[mYear_now, mMonth_now] = mDay_now

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
        return diffInDays
    }

    //start-passthevalues
    private fun findAllViewsId() {
        pp_car_upto1500_btn = findViewById<View>(R.id.pp_car_upto1500_btn) as Button?
        pp_car_upto1500_idv_value = findViewById<View>(R.id.pp_car_upto1500_idv_value) as EditText?
        pp_car_upto1500_date_value =
            findViewById<View>(R.id.pp_car_upto1500_date_value) as EditText?
        pp_car_upto1500_cc_value = findViewById<View>(R.id.pp_car_upto1500_cc_value) as EditText?
        pp_car_upto1500_nd_value = findViewById<View>(R.id.pp_car_upto1500_nd_value) as EditText?
        pp_car_upto1500_ndd_value = findViewById<View>(R.id.pp_car_upto1500_ndd_value) as EditText?
        pp_car_upto1500_uwd_value = findViewById<View>(R.id.pp_car_upto1500_uwd_value) as EditText?
        pp_car_scpassengers_upto1500 =
            findViewById<View>(R.id.pp_car_scpassengers_upto1500) as EditText?
        pp_car_upto1500_lpgtype_value =
            findViewById<View>(R.id.pp_car_upto1500_lpgtype_value) as EditText?
        pp_car_upto1500_paod_value = findViewById<EditText>(R.id.pp_car_upto1500_paod)
        val scrollview = findViewById<View>(R.id.pp_car_upto1500_sv) as ScrollView
        pp_car_upto1500_uwd_value!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_NEXT) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_car_scpassengers_upto1500!!.requestFocus()
            }
            false
        }
        pp_car_scpassengers_upto1500!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_car_upto1500_btn!!.requestFocus()
            }
            false
        }
        pp_car_upto1500_zone = findViewById<View>(R.id.pp_car_upto1500_zone) as RadioGroup?
        pp_car_upto1500_lpg = findViewById<View>(R.id.pp_car_upto1500_lpg) as RadioGroup?
        pp_car_upto1500_lpgtype = findViewById<View>(R.id.pp_car_upto1500_lpgtype) as RadioGroup?
        pp_car_upto1500_antitheft =
            findViewById<View>(R.id.pp_car_upto1500_antitheft) as RadioGroup?
        pp_car_upto1500_nd = findViewById<View>(R.id.pp_car_upto1500_nd) as RadioGroup?
        pp_car_patooccupants_upto1500 =
            findViewById<View>(R.id.pp_car_patooccupants_upto1500) as RadioGroup?
    }

    override fun onClick(v: View) {
        //Check for any EditText being null
        val idv_value = pp_car_upto1500_idv_value!!.text.toString()
        val nd_value = pp_car_upto1500_nd_value!!.text.toString()
        val ndd_value = pp_car_upto1500_ndd_value!!.text.toString()
        val uwd_value = pp_car_upto1500_uwd_value!!.text.toString()
        val scpass_value = pp_car_scpassengers_upto1500!!.text.toString()
        val pp_lpgtype_value = pp_car_upto1500_lpgtype_value!!.text.toString()
        val rb2 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value_fixed) as RadioButton
        val rb1 = findViewById<View>(R.id.pp_car_upto1500_lpgtype_value_inbuilt) as RadioButton
        if (idv_value == "" || uwd_value == "" || scpass_value == "" || ndd_value == "") {
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
            if (pp_car_upto1500_lpgtype_value!!.visibility == View.VISIBLE && pp_lpgtype_value == "") {
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
                val intent = Intent(getApplicationContext(), ppdisplay_car_upto1500::class.java)
                //Create a bundle object
                val b = Bundle()
                b.putString(
                    "pp_car_upto1500_idv_value",
                    pp_car_upto1500_idv_value!!.text.toString()
                )
                b.putString(
                    "pp_car_upto1500_date_value",
                    pp_car_upto1500_date_value!!.text.toString()
                )
                b.putString("pp_car_upto1500_cc_value", pp_car_upto1500_cc_value!!.text.toString())
                b.putString("pp_car_upto1500_nd_value", pp_car_upto1500_nd_value!!.text.toString())
                b.putString(
                    "pp_car_upto1500_ndd_value",
                    pp_car_upto1500_ndd_value!!.text.toString()
                )
                b.putString(
                    "pp_car_upto1500_uwd_value",
                    pp_car_upto1500_uwd_value!!.text.toString()
                )
                b.putString(
                    "pp_car_scpassengers_upto1500",
                    pp_car_scpassengers_upto1500!!.text.toString()
                )
                b.putString(
                    "pp_car_upto1500_paod_value",
                    pp_car_upto1500_paod_value!!.text.toString()
                )
                b.putString(
                    "pp_car_scpassengers_lpgtype_value",
                    pp_car_upto1500_lpgtype_value!!.text.toString()
                )
                b.putString("pp_car_spinner_value", selected)
                val id1 = pp_car_upto1500_zone!!.checkedRadioButtonId
                val radioButton1 = findViewById<View>(id1) as RadioButton
                b.putString("pp_car_upto1500_zone", radioButton1.text.toString())
                val id2 = pp_car_upto1500_lpg!!.checkedRadioButtonId
                radioButton2 = findViewById<View>(id2) as RadioButton?
                b.putString("pp_car_upto1500_lpg", radioButton2!!.text.toString())
                val id3 = pp_car_upto1500_lpgtype!!.checkedRadioButtonId
                radioButton3 = findViewById<View>(id3) as RadioButton?
                b.putString("pp_car_upto1500_lpgtype", radioButton3!!.text.toString())
                val id6 = pp_car_upto1500_nd!!.checkedRadioButtonId
                val radioButton6 = findViewById<View>(id6) as RadioButton
                b.putString("pp_car_upto1500_nd", radioButton6.text.toString())
                val id4 = pp_car_upto1500_antitheft!!.checkedRadioButtonId
                val radioButton4 = findViewById<View>(id4) as RadioButton
                b.putString("pp_car_upto1500_antitheft", radioButton4.text.toString())
                val id5 = pp_car_patooccupants_upto1500!!.checkedRadioButtonId
                val radioButton5 = findViewById<View>(id5) as RadioButton
                b.putString("pp_car_patooccupants_upto1500", radioButton5.text.toString())
                b.putInt("year", mYear)
                b.putInt("month", mMonth)
                b.putInt("day", mDay)
                //Add the bundle to the intent.
                intent.putExtras(b)

                //start the DisplayActivity
                startActivity(intent)
            }
        }
    }

    //stop-passthevalues
    //Date-start
    private fun updateDisplay() {
        mDateDisplay!!.text = StringBuilder() // Month is 0 based so add 1
            .append(mDay).append("-")
            .append(mMonth + 1).append("-")
            .append(mYear).append(" ")
        val ed1 = findViewById<View>(R.id.pp_car_upto1500_nd_value) as EditText
        val diffInDays = CalculateDifferenceInDays()
        var nd_value1 = 0.00
        if (diffInDays < 365) {
            nd_value1 = 15.0
        } else if (diffInDays >= 365 && diffInDays < 730) {
            nd_value1 = 25.0
        } else if (diffInDays >= 730 && diffInDays < 1825) {
            nd_value1 = 35.0
        } else if (diffInDays >= 1825 && diffInDays < 3650) {
            nd_value1 = 40.0
        } else if (diffInDays >= 3650) {
            nd_value1 = 0.0
        }
        val nd_value1_int = nd_value1.toInt()
        ed1.setText(nd_value1_int.toString())
        ed1.isEnabled = false
    }

    private val mDateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        updateDisplay()
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            pp_car_upto1500.Companion.DATE_DIALOG_ID -> return DatePickerDialog(
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
    override fun onItemSelected(parent: AdapterView<*>, arg1: View, position: Int, id: Long) {
        Toast.makeText(getApplicationContext(), ncb[position], Toast.LENGTH_LONG)
        selected = parent.getItemAtPosition(position).toString()
    }

    //To check NDD value
    var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (ndd!!.text.length > 0) {
                val num = ndd!!.text.toString().toInt()
                num1 = if (num >= 0 && num <= 35) {
                    //save the number
                    num
                } else {
                    Toast.makeText(
                        this@pp_car_upto1500,
                        "NDD range should be of 1-35",
                        Toast.LENGTH_SHORT
                    ).show()
                    ndd!!.setText("")
                    -1
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onNothingSelected(arg0: AdapterView<*>?) {
// TODO Auto-generated method stub
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

    companion object {
        private const val TAG = "pp_car_upto1500"
        const val DATE_DIALOG_ID = 0
    }
}