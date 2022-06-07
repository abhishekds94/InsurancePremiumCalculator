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
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.ads.InterstitialAdManager
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView

/**
 * Created by Abhishek on 26-Mar-17.
 */
class pp_passauto_upto6 : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    View.OnClickListener, ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    private var mDateDisplay: TextView? = null
    private var mPickDate: Button? = null
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    var diffInDays: Long = 0
    var diff_days: Long = 0
    var spin_val: String? = null
    var pp_passauto_upto6_btn: Button? = null
    var pp_passauto_upto6_idv_value: EditText? = null
    var pp_passauto_upto6_date_value: EditText? = null
    var pp_passauto_upto6_cc_value: EditText? = null
    var pp_passauto_upto6_nd_value: EditText? = null
    var pp_passauto_upto6_ndd_value: EditText? = null
    var pp_passauto_upto6_lpgtype_value: EditText? = null
    var pp_passauto_upto6_ncb_value: Spinner? = null
    var pp_passauto_upto6_uwd_value: EditText? = null
    var pp_passauto_scpassengers_upto6: EditText? = null
    var pp_passauto_upto6_paod_value: EditText? = null
    var pp_passauto_upto6_zone: RadioGroup? = null
    var pp_passauto_upto6_lpg: RadioGroup? = null
    var pp_passauto_upto6_lpgtype: RadioGroup? = null

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
        checkfunction(this@pp_passauto_upto6)
        setContentView(R.layout.pp_passauto_upto6)
        getSupportActionBar()!!.setTitle("Passenger Auto Package Policy")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        interstitialAdManager = InterstitialAdManager(this)

        val pa_no: RadioButton = findViewById<RadioButton>(R.id.pp_passauto_upto6_paod_value_no)
        pa_no.setOnClickListener {
            pp_passauto_upto6_paod_value!!.setText("0")
            pp_passauto_upto6_paod_value!!.isEnabled = false
        }
        val pa_yes: RadioButton = findViewById<RadioButton>(R.id.pp_passauto_upto6_paod_value_yes)
        pa_yes.setOnClickListener {
            pp_passauto_upto6_paod_value!!.setText("320")
            pp_passauto_upto6_paod_value!!.isEnabled = true
        }


        //Date-start
        mDateDisplay = findViewById<View>(R.id.pp_passauto_upto6_date_value) as TextView?
        mPickDate = findViewById<View>(R.id.pp_passauto_upto6_date_btn) as Button?
        mPickDate!!.setOnClickListener { showDialog(pp_passauto_upto6.Companion.DATE_DIALOG_ID) }
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        updateDisplay()
        //Date-end


        //spinner-start
        val spin = findViewById<View>(R.id.pp_passauto_upto6_ncb_value) as Spinner
        spin.onItemSelectedListener = this
        val aa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ncb)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = aa

        //spinner-end

        //to check if CNG is yes or no
        val cng_no = findViewById<View>(R.id.pp_passauto_upto6_lpg_value_no) as RadioButton
        cng_no.isChecked = true
        cng_no.setOnClickListener(no_cng)
        val cng_yes = findViewById<View>(R.id.pp_passauto_upto6_lpg_value_yes) as RadioButton
        cng_yes.isChecked = false
        cng_yes.setOnClickListener(yes_cng)


        //setting default value of cngtype to fixed
        val cng_inbuilt =
            findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value_inbuilt) as RadioButton
        cng_inbuilt.isChecked = false

        //For N/d
        val nd_yes = findViewById<View>(R.id.pp_passauto_upto6_nd_value_yes) as RadioButton
        nd_yes.isChecked = true
        nd_yes.setOnClickListener { nd_calculate_diff() }
        val nd_no = findViewById<View>(R.id.pp_passauto_upto6_nd_value_no) as RadioButton
        nd_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_passauto_upto6_nd_value) as EditText
            ed1.isEnabled = false
            ed1.setText("0")
        }

        //Find diff in days which will be used to calculate Idv using DOP in display
        diff_days = CalculateDifferenceInDays()
        findViewById<View>(R.id.pp_passauto_upto6_btn).setOnClickListener(
            listener_pp_passauto_upto6_btn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        pp_passauto_upto6_btn!!.setOnClickListener(this)
        //stop-passthevalues
        ndd = findViewById<View>(R.id.pp_passauto_upto6_ndd_value) as EditText?
        ndd!!.addTextChangedListener(textWatcher)
    }

    var listener_pp_passauto_upto6_btn = View.OnClickListener {
        val intent = Intent(this@pp_passauto_upto6, ppdisplay_passauto_upto6::class.java)
        startActivity(intent)
    }

    //listener if cng is yes
    var yes_cng = View.OnClickListener {
        val rb1 = findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value_fixed) as RadioButton
        val rb2 = findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value_inbuilt) as RadioButton
        rb1.isEnabled = true
        rb2.isEnabled = true
        val ed1 = findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value) as EditText
        ed1.visibility = View.VISIBLE
        val lpg_sym = findViewById<View>(R.id.cng_rsym) as TextView
        lpg_sym.visibility = View.VISIBLE
        ed1.isEnabled = true
        rb1.isChecked = true
        rb2.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value) as EditText
            ed1.visibility = View.INVISIBLE
            val lpg_sym = findViewById<View>(R.id.cng_rsym) as TextView
            lpg_sym.visibility = View.INVISIBLE
        }
        rb1.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value) as EditText
            ed1.visibility = View.VISIBLE
            val lpg_sym = findViewById<View>(R.id.cng_rsym) as TextView
            lpg_sym.visibility = View.VISIBLE
            ed1.isEnabled = true
        }
    }

    //listener if cng is no
    var no_cng = View.OnClickListener {
        val rb1 = findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value_fixed) as RadioButton
        val rb2 = findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value_inbuilt) as RadioButton
        rb1.isEnabled = false
        rb2.isEnabled = false
        val ed1 = findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value) as EditText
        ed1.isEnabled = false
        ed1.visibility = View.INVISIBLE
        val lpg_sym = findViewById<View>(R.id.cng_rsym) as TextView
        lpg_sym.visibility = View.INVISIBLE
    }

    fun nd_calculate_diff() {
        val ed1 = findViewById<View>(R.id.pp_passauto_upto6_nd_value) as EditText
        val diffInDays = CalculateDifferenceInDays()
        var nd_value1 = 0.00
        if (diffInDays < 365) {
            nd_value1 = 15.0
        } else if (diffInDays >= 365) {
            if (diffInDays < 1825) {
                nd_value1 = 25.0
            } else if (diffInDays >= 1825) {
                nd_value1 = 0.0
            }
        }
        val nd_value1_int = nd_value1.toInt()
        ed1.setText(nd_value1_int.toString())
        ed1.isEnabled = false
        ed1.visibility = View.VISIBLE
        val nd_sym = findViewById<View>(R.id.percent_sym) as TextView
        nd_sym.visibility = View.VISIBLE
    }

    //start-passthevalues
    private fun findAllViewsId() {
        pp_passauto_upto6_btn = findViewById<View>(R.id.pp_passauto_upto6_btn) as Button?
        pp_passauto_upto6_idv_value =
            findViewById<View>(R.id.pp_passauto_upto6_idv_value) as EditText?
        pp_passauto_upto6_date_value =
            findViewById<View>(R.id.pp_passauto_upto6_date_value) as EditText?
        pp_passauto_upto6_cc_value =
            findViewById<View>(R.id.pp_passauto_upto6_cc_value) as EditText?
        pp_passauto_upto6_nd_value =
            findViewById<View>(R.id.pp_passauto_upto6_nd_value) as EditText?
        pp_passauto_upto6_ndd_value =
            findViewById<View>(R.id.pp_passauto_upto6_ndd_value) as EditText?
        pp_passauto_upto6_lpgtype_value =
            findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value) as EditText?
        pp_passauto_upto6_ncb_value =
            findViewById<View>(R.id.pp_passauto_upto6_ncb_value) as Spinner?
        pp_passauto_upto6_uwd_value =
            findViewById<View>(R.id.pp_passauto_upto6_uwd_value) as EditText?
        pp_passauto_scpassengers_upto6 =
            findViewById<View>(R.id.pp_passauto_scpassengers_upto6) as EditText?
        pp_passauto_upto6_paod_value = findViewById<EditText>(R.id.pp_passauto_upto6_paod)
        val scrollview = findViewById<View>(R.id.pp_passauto_upto6_sv) as ScrollView
        pp_passauto_upto6_uwd_value!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_passauto_upto6_btn!!.requestFocus()
            }
            false
        }
        pp_passauto_upto6_zone = findViewById<View>(R.id.pp_passauto_upto6_zone) as RadioGroup?
        pp_passauto_upto6_lpg = findViewById<View>(R.id.pp_passauto_upto6_lpg) as RadioGroup?
        pp_passauto_upto6_lpgtype =
            findViewById<View>(R.id.pp_passauto_upto6_lpgtype) as RadioGroup?
    }

    fun CalculateDifferenceInDays(): Long {
        val mYear_now: Int
        val mMonth_now: Int
        val mDay_now: Int
        // Create Calendar instance
        val calendar1 = java.util.Calendar.getInstance()
        val calendar2 = java.util.Calendar.getInstance()
        mYear_now = calendar1[java.util.Calendar.YEAR]
        mMonth_now = calendar1[java.util.Calendar.MONTH]
        mDay_now = calendar1[java.util.Calendar.DAY_OF_MONTH]

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

    //Validation function
    fun validation(): Int {
        if (pp_passauto_upto6_idv_value!!.text.toString()
                .isEmpty() || pp_passauto_upto6_nd_value!!.text.toString().isEmpty()
            || pp_passauto_upto6_uwd_value!!.text.toString()
                .isEmpty() || pp_passauto_upto6_ndd_value!!.text.toString().isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "Enter All Fields First", Toast.LENGTH_SHORT)
                .show()
            return 0
        }
        val radioButton1 = findViewById<View>(R.id.pp_passauto_upto6_lpg_value_yes) as RadioButton
        val radioButton2 =
            findViewById<View>(R.id.pp_passauto_upto6_lpgtype_value_fixed) as RadioButton
        if (radioButton1.isChecked && radioButton2.isChecked) {
            if (pp_passauto_upto6_lpgtype_value!!.text.toString().isEmpty()) {
                Toast.makeText(
                    getApplicationContext(),
                    "Enter The Fixed Value Of CNG First",
                    Toast.LENGTH_SHORT
                ).show()
                return 0
            }
        }
        return 1
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onClick(v: View) {
        val intent = Intent(getApplicationContext(), ppdisplay_passauto_upto6::class.java)

        //validation check to check if all entries are entered
        val valid = validation()
        if (valid == 1) {
            //Create a bundle object
            val b = Bundle()

            //Inserts a String value into the mapping of this Bundle
            b.putString(
                "pp_passauto_upto6_idv_value",
                pp_passauto_upto6_idv_value!!.text.toString()
            )
            b.putString(
                "pp_passauto_upto6_date_value",
                pp_passauto_upto6_date_value!!.text.toString()
            )
            b.putString("pp_passauto_upto6_cc_value", pp_passauto_upto6_cc_value!!.text.toString())
            b.putString("pp_passauto_upto6_nd_value", pp_passauto_upto6_nd_value!!.text.toString())
            b.putString(
                "pp_passauto_upto6_ndd_value",
                pp_passauto_upto6_ndd_value!!.text.toString()
            )
            b.putString(
                "pp_passauto_upto6_uwd_value",
                pp_passauto_upto6_uwd_value!!.text.toString()
            )
            b.putString(
                "pp_passauto_upto6_paod_value",
                pp_passauto_upto6_paod_value!!.text.toString()
            )
            b.putString(
                "pp_passauto_scpassengers_upto6",
                pp_passauto_scpassengers_upto6!!.text.toString()
            )

            //For chnaging the default value of cng type if NO is selected
            val r1 = findViewById<View>(R.id.pp_passauto_upto6_lpg_value_no) as RadioButton
            if (r1.isChecked) {
                pp_passauto_upto6_lpgtype_value!!.setText("-")
            }
            b.putString("lpgtype_value", pp_passauto_upto6_lpgtype_value!!.text.toString())

            //For NCB spinner
            val spin = findViewById<View>(R.id.pp_passauto_upto6_ncb_value) as Spinner
            spin_val = spin.selectedItem.toString()
            b.putString("pp_passauto_upto6_ncb_value", spin_val)

            //put diff in days
            b.putLong("diff_in_days", diff_days)
            val id1 = pp_passauto_upto6_zone!!.checkedRadioButtonId
            val radioButton1 = findViewById<View>(id1) as RadioButton
            b.putString("pp_passauto_upto6_zone", radioButton1.text.toString())
            val id2 = pp_passauto_upto6_lpg!!.checkedRadioButtonId
            val radioButton2 = findViewById<View>(id2) as RadioButton
            b.putString("pp_passauto_upto6_lpg", radioButton2.text.toString())
            val id3 = pp_passauto_upto6_lpgtype!!.checkedRadioButtonId
            val radioButton3 = findViewById<View>(id3) as RadioButton
            b.putString("pp_passauto_upto6_lpgtype", radioButton3.text.toString())


            //Add the bundle to the intent.
            intent.putExtras(b)

            //start the DisplayActivity
            startActivity(intent)
        }
    }

    //stop-passthevalues
    //refresh function to reset all the fields once calculate button is clicked
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun refresh() {
        pp_passauto_upto6_idv_value!!.setText("")
        val c1 = Calendar.getInstance()
        mYear = c1[Calendar.YEAR]
        mMonth = c1[Calendar.MONTH]
        mDay = c1[Calendar.DAY_OF_MONTH]
        updateDisplay()
        val r1 = findViewById<View>(R.id.pp_passauto_upto6_idv_value_C) as RadioButton
        r1.isChecked = true
        pp_passauto_upto6_lpgtype_value!!.setText("")
        pp_passauto_upto6_uwd_value!!.setText("")
    }

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
        //to auto update Nil Depreciation when date changes(only when yes was selected in n/d)
        val r1 = findViewById<View>(R.id.pp_passauto_upto6_nd_value_yes) as RadioButton
        if (r1.isChecked) nd_calculate_diff()
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            pp_passauto_upto6.Companion.DATE_DIALOG_ID -> return DatePickerDialog(
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
        Toast.makeText(getApplicationContext(), ncb[position], Toast.LENGTH_LONG)
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
                num1 = if (num >= 0 && num <= 35) {
                    //save the number
                    num
                } else {
                    Toast.makeText(
                        this@pp_passauto_upto6,
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
        private const val TAG = "pp_passauto_upto6"
        const val DATE_DIALOG_ID = 0
    }
}