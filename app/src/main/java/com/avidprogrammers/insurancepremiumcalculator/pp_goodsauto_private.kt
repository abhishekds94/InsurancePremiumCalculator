package com.avidprogrammers.insurancepremiumcalculator

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.ads.InterstitialAdManager
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView

//import static com.avidprogrammers.pp_goodsauto_private.R.id.pp_goodsauto_private_lpgtype_value;
/**
 * Created by Abhishek on 26-Mar-17.
 */
class pp_goodsauto_private : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    View.OnClickListener, ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    private var mDateDisplay: TextView? = null
    private var mPickDate: Button? = null
    private var mYear = 0
    private var mMonth = 0
    var diffInDays: Long = 0
    var diff_days: Long = 0
    private var mDay = 0
    var pp_goodsauto_private_btn: Button? = null
    var pp_goodsauto_private_idv_value: EditText? = null
    var pp_goodsauto_private_date_value: EditText? = null
    var pp_goodsauto_private_cc_value: EditText? = null
    var pp_goodsauto_private_nd_value: EditText? = null
    var pp_goodsauto_private_uwd_value: EditText? = null
    var pp_goodsauto_private_nfpp: EditText? = null
    var pp_goodsauto_private_coolie: EditText? = null
    var pp_goodsauto_private_gvw_value: EditText? = null
    var pp_goodsauto_private_lpgtype_value: EditText? = null
    var pp_goodsauto_private_paod_value: EditText? = null
    var pp_goodsauto_private_zone: RadioGroup? = null
    var pp_goodsauto_private_lpg: RadioGroup? = null
    var pp_goodsauto_private_lpgtype: RadioGroup? = null

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
        checkfunction(this@pp_goodsauto_private)
        setContentView(R.layout.pp_goodsauto_private)
        getSupportActionBar()!!.setTitle("Private Goods Auto Package Policy")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        interstitialAdManager = InterstitialAdManager(this)

        val pa_no: RadioButton = findViewById<RadioButton>(R.id.pp_goodsauto_private_paod_value_no)
        pa_no.setOnClickListener {
            pp_goodsauto_private_paod_value!!.setText("0")
            pp_goodsauto_private_paod_value!!.isEnabled = false
        }
        val pa_yes: RadioButton =
            findViewById<RadioButton>(R.id.pp_goodsauto_private_paod_value_yes)
        pa_yes.setOnClickListener {
            pp_goodsauto_private_paod_value!!.setText("320")
            pp_goodsauto_private_paod_value!!.isEnabled = true
        }


        //Date-start
        mDateDisplay = findViewById<View>(R.id.pp_goodsauto_private_date_value) as TextView?
        mPickDate = findViewById<View>(R.id.pp_goodsauto_private_date_btn) as Button?
        mPickDate!!.setOnClickListener { showDialog(pp_goodsauto_private.Companion.DATE_DIALOG_ID) }
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        updateDisplay()
        //Date-end


        //spinner-start
        val spin = findViewById<View>(R.id.pp_goodsauto_private_ncb_value) as Spinner
        spin.onItemSelectedListener = this
        val aa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ncb)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = aa
        //spinner-end

        //to check if CNG is yes or no
        val cng_no = findViewById<View>(R.id.pp_goodsauto_private_lpg_value_no) as RadioButton
        cng_no.isChecked = true
        cng_no.setOnClickListener(no_cng)
        val cng_yes = findViewById<View>(R.id.pp_goodsauto_private_lpg_value_yes) as RadioButton
        cng_yes.isChecked = false
        cng_yes.setOnClickListener(yes_cng)


        //setting default value of cngtype to fixed
        val cng_inbuilt =
            findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value_inbuilt) as RadioButton
        cng_inbuilt.isChecked = false

        //For N/d
        val nd_yes = findViewById<View>(R.id.pp_goodsauto_private_nd_value_yes) as RadioButton
        nd_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_goodsauto_private_nd_value) as EditText
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
            val nd_sym = findViewById<View>(R.id.percent_sym) as TextView
            nd_sym.visibility = View.VISIBLE
        }
        val nd_no = findViewById<View>(R.id.pp_goodsauto_private_nd_value_no) as RadioButton
        nd_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_goodsauto_private_nd_value) as EditText
            ed1.isEnabled = false
            ed1.setText("0")
        }

        //Find diff in days which will be used to calculate Idv using DOP in display
        diff_days = CalculateDifferenceInDays()
        findViewById<View>(R.id.pp_goodsauto_private_btn).setOnClickListener(
            listener_pp_goodsauto_private_btn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        pp_goodsauto_private_btn!!.setOnClickListener(this)
        //stop-passthevalues
    }

    var listener_pp_goodsauto_private_btn = View.OnClickListener {
        val intent = Intent(this@pp_goodsauto_private, ppdisplay_goodsauto_private::class.java)
        startActivity(intent)
    }

    //listener if cng is yes
    var yes_cng = View.OnClickListener {
        val rb1 = findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value_fixed) as RadioButton
        val rb2 = findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value_inbuilt) as RadioButton
        rb1.isEnabled = true
        rb2.isEnabled = true
        val ed1 = findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value) as EditText
        ed1.visibility = View.VISIBLE
        val lpg_sym = findViewById<View>(R.id.r_sym) as TextView
        lpg_sym.visibility = View.VISIBLE
        ed1.isEnabled = true
        rb1.isChecked = true
        rb2.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value) as EditText
            ed1.visibility = View.INVISIBLE
            val lpg_sym = findViewById<View>(R.id.r_sym) as TextView
            lpg_sym.visibility = View.INVISIBLE
        }
        rb1.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value) as EditText
            ed1.visibility = View.VISIBLE
            val lpg_sym = findViewById<View>(R.id.r_sym) as TextView
            lpg_sym.visibility = View.VISIBLE
            ed1.isEnabled = true
        }
    }

    //listener if cng is no
    var no_cng = View.OnClickListener {
        val rb1 = findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value_fixed) as RadioButton
        val rb2 = findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value_inbuilt) as RadioButton
        rb1.isEnabled = false
        rb2.isEnabled = false
        val ed1 = findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value) as EditText
        ed1.isEnabled = false
        ed1.visibility = View.INVISIBLE
        val lpg_sym = findViewById<View>(R.id.r_sym) as TextView
        lpg_sym.visibility = View.INVISIBLE
    }

    fun nd_calculate_diff() {
        val ed1 = findViewById<View>(R.id.pp_goodsauto_private_nd_value) as EditText
        val diffInDays = CalculateDifferenceInDays()
        var nd_value1 = 0.00
        if (diffInDays < 182) {
            nd_value1 = 10.0
        } else if (diffInDays in 182..729) {
            nd_value1 = 20.0
        } else if (diffInDays in 730..1824) {
            nd_value1 = 30.0
        } else if (diffInDays >= 3650) {
            nd_value1 = 0.0
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
        pp_goodsauto_private_btn = findViewById<View>(R.id.pp_goodsauto_private_btn) as Button?
        pp_goodsauto_private_idv_value =
            findViewById<View>(R.id.pp_goodsauto_private_idv_value) as EditText?
        pp_goodsauto_private_date_value =
            findViewById<View>(R.id.pp_goodsauto_private_date_value) as EditText?
        pp_goodsauto_private_cc_value =
            findViewById<View>(R.id.pp_goodsauto_private_cc_value) as EditText?
        pp_goodsauto_private_nd_value =
            findViewById<View>(R.id.pp_goodsauto_private_nd_value) as EditText?
        pp_goodsauto_private_uwd_value =
            findViewById<View>(R.id.pp_goodsauto_private_uwd_value) as EditText?
        pp_goodsauto_private_nfpp = findViewById<View>(R.id.pp_goodsauto_private_nfpp) as EditText?
        pp_goodsauto_private_coolie =
            findViewById<View>(R.id.pp_goodsauto_private_coolie) as EditText?
        pp_goodsauto_private_paod_value = findViewById<EditText>(R.id.pp_goodsauto_private_paod)
        val scrollview = findViewById<View>(R.id.pp_goodsauto_private_sv) as ScrollView
        pp_goodsauto_private_uwd_value!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_goodsauto_private_nfpp!!.requestFocus()
            }
            false
        }
        pp_goodsauto_private_coolie!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_goodsauto_private_btn!!.requestFocus()
            }
            false
        }
        pp_goodsauto_private_gvw_value =
            findViewById<View>(R.id.pp_goodsauto_private_gvw_value) as EditText?
        pp_goodsauto_private_lpgtype_value =
            findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value) as EditText?
        pp_goodsauto_private_zone =
            findViewById<View>(R.id.pp_goodsauto_private_zone) as RadioGroup?
        pp_goodsauto_private_lpg = findViewById<View>(R.id.pp_goodsauto_private_lpg) as RadioGroup?
        pp_goodsauto_private_lpgtype =
            findViewById<View>(R.id.pp_goodsauto_private_lpgtype) as RadioGroup?
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
        if (pp_goodsauto_private_idv_value!!.text.toString()
                .isEmpty() || pp_goodsauto_private_nd_value!!.text.toString().isEmpty()
            || pp_goodsauto_private_uwd_value!!.text.toString().isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "Enter All Fields First", Toast.LENGTH_SHORT)
                .show()
            return 0
        }
        val radioButton1 =
            findViewById<View>(R.id.pp_goodsauto_private_lpg_value_yes) as RadioButton
        val radioButton2 =
            findViewById<View>(R.id.pp_goodsauto_private_lpgtype_value_fixed) as RadioButton
        if (radioButton1.isChecked && radioButton2.isChecked) {
            if (pp_goodsauto_private_lpgtype_value!!.text.toString().isEmpty()) {
                Toast.makeText(
                    getApplicationContext(),
                    "Enter The Fixed Value Of CNG First",
                    Toast.LENGTH_SHORT
                ).show()
                return 0
            }
        }
        if (pp_goodsauto_private_nfpp!!.text.toString()
                .isEmpty() || pp_goodsauto_private_coolie!!.text.toString().isEmpty()
        ) {
            Toast.makeText(
                getApplicationContext(),
                "Enter NFPP or Coolie First ",
                Toast.LENGTH_SHORT
            ).show()
            return 0
        }
        return 1
    }

    override fun onClick(v: View) {
        val intent = Intent(getApplicationContext(), ppdisplay_goodsauto_private::class.java)
        //Create a bundle object
        val valid = validation()
        if (valid == 1) {
            val b = Bundle()

            //Inserts a String value into the mapping of this Bundle
            b.putString(
                "pp_goodsauto_private_idv_value",
                pp_goodsauto_private_idv_value!!.text.toString()
            )
            b.putString(
                "pp_goodsauto_private_date_value",
                pp_goodsauto_private_date_value!!.text.toString()
            )
            b.putString(
                "pp_goodsauto_private_cc_value",
                pp_goodsauto_private_cc_value!!.text.toString()
            )
            b.putString(
                "pp_goodsauto_private_nd_value",
                pp_goodsauto_private_nd_value!!.text.toString()
            )
            b.putString(
                "pp_goodsauto_private_uwd_value",
                pp_goodsauto_private_uwd_value!!.text.toString()
            )
            b.putString("pp_goodsauto_private_nfpp", pp_goodsauto_private_nfpp!!.text.toString())
            b.putString(
                "pp_goodsauto_private_coolie",
                pp_goodsauto_private_coolie!!.text.toString()
            )
            b.putString(
                "pp_goodsauto_private_paod_value",
                pp_goodsauto_private_paod_value!!.text.toString()
            )
            b.putString(
                "pp_goodsauto_private_gvw_value",
                pp_goodsauto_private_gvw_value!!.text.toString()
            )


            //For changing the default value of cng type if NO is selected
            val r1 = findViewById<View>(R.id.pp_goodsauto_private_lpg_value_no) as RadioButton
            if (r1.isChecked) {
                pp_goodsauto_private_lpgtype_value!!.setText("-")
            }
            b.putString("lpgtype_value", pp_goodsauto_private_lpgtype_value!!.text.toString())

            //For NCB spinner
            val spin = findViewById<View>(R.id.pp_goodsauto_private_ncb_value) as Spinner
            val spin_val = spin.selectedItem.toString()
            b.putString("pp_goodsauto_private_ncb_value", spin_val)

            //put diff in days
            b.putLong("diff_in_days", diff_days)
            val id1 = pp_goodsauto_private_zone!!.checkedRadioButtonId
            val radioButton1 = findViewById<View>(id1) as RadioButton
            b.putString("pp_goodsauto_private_zone", radioButton1.text.toString())
            val id2 = pp_goodsauto_private_lpg!!.checkedRadioButtonId
            val radioButton2 = findViewById<View>(id2) as RadioButton
            b.putString("pp_goodsauto_private_lpg", radioButton2.text.toString())
            val id3 = pp_goodsauto_private_lpgtype!!.checkedRadioButtonId
            val radioButton3 = findViewById<View>(id3) as RadioButton
            b.putString("pp_goodsauto_private_lpgtype", radioButton3.text.toString())

            //refresh();
            //Add the bundle to the intent.
            intent.putExtras(b)

            //start the DisplayActivity
            startActivity(intent)
        }
    }

    //stop-passthevalues
    //refresh function to reset all the fields once calculate button is clicked
    fun refresh() {
        pp_goodsauto_private_idv_value!!.setText("")
        val c1 = Calendar.getInstance()
        mYear = c1[Calendar.YEAR]
        mMonth = c1[Calendar.MONTH]
        mDay = c1[Calendar.DAY_OF_MONTH]
        updateDisplay()
        val r1 = findViewById<View>(R.id.pp_goodsauto_private_idv_value_C) as RadioButton
        r1.isChecked = true
        pp_goodsauto_private_lpgtype_value!!.setText("")
        pp_goodsauto_private_uwd_value!!.setText("")
        pp_goodsauto_private_nfpp!!.setText("")
        pp_goodsauto_private_coolie!!.setText("")
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
        val r1 = findViewById<View>(R.id.pp_goodsauto_private_nd_value_yes) as RadioButton
        if (r1.isChecked) nd_calculate_diff()
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            pp_goodsauto_private.Companion.DATE_DIALOG_ID -> return DatePickerDialog(
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
        private const val TAG = "pp_goodsauto_private"
        const val DATE_DIALOG_ID = 0
    }
}