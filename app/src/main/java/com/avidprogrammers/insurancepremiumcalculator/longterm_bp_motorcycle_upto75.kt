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
class longterm_bp_motorcycle_upto75 : AppCompatActivity(), AdapterView.OnItemSelectedListener,
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
    var selected: String? = null
    var lt_bp_motorcycle_upto75: Button? = null
    var lt_bp_motorcycle_upto75_idv_value: EditText? = null
    var lt_bp_motorcycle_upto75_date_value: EditText? = null
    var lt_bp_motorcycle_upto75_cc_value: EditText? = null
    var lt_bp_motorcycle_upto75_nd_value: EditText? = null
    var lt_bp_motorcycle_upto75_ndd_value: EditText? = null
    var lt_bp_motorcycle_upto75_uwd_value: EditText? = null
    var lt_bp_motorcycle_upto75_paod_value: EditText? = null
    var zoneRadioGroup: RadioGroup? = null
    var ndRadioGroup: RadioGroup? = null
    var ndd: EditText? = null
    var num1 = 0

    private var interstitialAdManager: InterstitialAdManager? = null

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    override fun onPause() {
        super.onPause()
        interstitialAdManager!!.showInterstitial(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@longterm_bp_motorcycle_upto75)
        setContentView(R.layout.longterm_bp_motorcycle_upto75)
        supportActionBar!!.title = "Motorcycle Package Policy"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        interstitialAdManager = InterstitialAdManager(this)

        val nd_no = findViewById<View>(R.id.lt_bp_motorcycle_upto75_nd_no) as RadioButton
        nd_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lt_bp_motorcycle_upto75_nd_value) as EditText
            ed1.isEnabled = false
            ed1.setText("0")
        }
        val nd_yes = findViewById<View>(R.id.lt_bp_motorcycle_upto75_nd_yes) as RadioButton
        nd_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.lt_bp_motorcycle_upto75_nd_value) as EditText
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
            ed1.visibility = View.VISIBLE
        }
        val pa_no = findViewById<RadioButton>(R.id.lt_bp_motorcycle_upto75_paod_value_no)
        pa_no.setOnClickListener {
            lt_bp_motorcycle_upto75_paod_value!!.setText("0")
            lt_bp_motorcycle_upto75_paod_value!!.isEnabled = false
        }
        val pa_yes = findViewById<RadioButton>(R.id.lt_bp_motorcycle_upto75_paod_value_yes)
        pa_yes.setOnClickListener {
            lt_bp_motorcycle_upto75_paod_value!!.setText("1420")
            lt_bp_motorcycle_upto75_paod_value!!.isEnabled = true
        }


        //Date-start
        mDateDisplay = findViewById<View>(R.id.lt_bp_motorcycle_upto75_date_value) as TextView
        mPickDate = findViewById<View>(R.id.lt_bp_motorcycle_upto75_date_btn) as Button
        mPickDate!!.setOnClickListener { showDialog(DATE_DIALOG_ID) }
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        updateDisplay()
        //Date-end


        //spinner-start
        val spin = findViewById<View>(R.id.lt_bp_motorcycle_upto75_ncb) as Spinner
        spin.onItemSelectedListener = this
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, ncb)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = aa
        //spinner-end
        findViewById<View>(R.id.lt_bp_motorcycle_upto75_btn).setOnClickListener(
            listener_lt_bp_motorcycle_upto75
        )


        //start-passvalues
        //Get the ids of view objects
        findAllViewsId()
        lt_bp_motorcycle_upto75!!.setOnClickListener(this)
        //stop-passvalues
        ndd = findViewById<View>(R.id.lt_bp_motorcycle_upto75_ndd_value) as EditText
        ndd!!.addTextChangedListener(textWatcher)
    }

    var listener_lt_bp_motorcycle_upto75 = View.OnClickListener {
        val intent = Intent(
            this@longterm_bp_motorcycle_upto75,
            longterm_bpdisplay_motorcycle_upto75::class.java
        )
        startActivity(intent)
    }

    //start-passvalues
    private fun findAllViewsId() {
        lt_bp_motorcycle_upto75 = findViewById<View>(R.id.lt_bp_motorcycle_upto75_btn) as Button
        lt_bp_motorcycle_upto75_idv_value =
            findViewById<View>(R.id.lt_bp_motorcycle_upto75_idv_value) as EditText
        lt_bp_motorcycle_upto75_date_value =
            findViewById<View>(R.id.lt_bp_motorcycle_upto75_date_value) as EditText
        lt_bp_motorcycle_upto75_cc_value =
            findViewById<View>(R.id.lt_bp_motorcycle_upto75_cc_value) as EditText
        lt_bp_motorcycle_upto75_nd_value =
            findViewById<View>(R.id.lt_bp_motorcycle_upto75_nd_value) as EditText
        lt_bp_motorcycle_upto75_ndd_value =
            findViewById<View>(R.id.lt_bp_motorcycle_upto75_ndd_value) as EditText
        lt_bp_motorcycle_upto75_uwd_value =
            findViewById<View>(R.id.lt_bp_motorcycle_upto75_uwd_value) as EditText
        lt_bp_motorcycle_upto75_paod_value = findViewById(R.id.lt_bp_motorcycle_upto75_paod)
        zoneRadioGroup = findViewById<View>(R.id.lt_bp_motorcycle_upto75_zone) as RadioGroup
        ndRadioGroup = findViewById<View>(R.id.lt_bp_motorcycle_upto75_nd) as RadioGroup
    }

    override fun onClick(v: View) {
        //Check for any EditText being null
        val idv_value = lt_bp_motorcycle_upto75_idv_value!!.text.toString()
        val uwd_value = lt_bp_motorcycle_upto75_uwd_value!!.text.toString()
        val ndd_value = lt_bp_motorcycle_upto75_ndd_value!!.text.toString()
        if (idv_value == "" || uwd_value == "" || ndd_value == "") {
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
                    });*/bar.setActionTextColor(resources.getColor(R.color.colorSnackBarDismiss))
            val tv =
                bar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            tv.setTextColor(resources.getColor(R.color.colorSnackBar))
            val view = bar.view
            view.setBackgroundColor(resources.getColor(R.color.colorSnackBarBg))
            bar.show()
        } else {
            val intent =
                Intent(applicationContext, longterm_bpdisplay_motorcycle_upto75::class.java)
            //Create a bundle object
            val b = Bundle()

            //Inserts a String value into the mapping of this Bundle
            b.putString(
                "lt_bp_motorcycle_upto75_idv_value",
                lt_bp_motorcycle_upto75_idv_value!!.text.toString()
            )
            b.putString(
                "lt_bp_motorcycle_upto75_date_value",
                lt_bp_motorcycle_upto75_date_value!!.text.toString()
            )
            b.putString(
                "lt_bp_motorcycle_upto75_cc_value",
                lt_bp_motorcycle_upto75_cc_value!!.text.toString()
            )
            b.putString(
                "lt_bp_motorcycle_upto75_nd_value",
                lt_bp_motorcycle_upto75_nd_value!!.text.toString()
            )
            b.putString(
                "lt_bp_motorcycle_upto75_ndd_value",
                lt_bp_motorcycle_upto75_ndd_value!!.text.toString()
            )
            b.putString(
                "lt_bp_motorcycle_upto75_uwd_value",
                lt_bp_motorcycle_upto75_uwd_value!!.text.toString()
            )
            b.putString(
                "lt_bp_motorcycle_upto75_paod_value",
                lt_bp_motorcycle_upto75_paod_value!!.text.toString()
            )
            val id1 = zoneRadioGroup!!.checkedRadioButtonId
            val radioButton1 = findViewById<View>(id1) as RadioButton
            b.putString("lt_bp_motorcycle_upto75_zone", radioButton1.text.toString())
            val id2 = ndRadioGroup!!.checkedRadioButtonId
            val radioButton2 = findViewById<View>(id2) as RadioButton
            b.putString("lt_bp_motorcycle_upto75_nd", radioButton2.text.toString())

            //Spinner value selected
            b.putString("lt_bp_motorcycle_upto75_spinner_value", selected)

            //
            b.putInt("year", mYear)
            b.putInt("month", mMonth)
            b.putInt("day", mDay)
            //Add the bundle to the intent.
            intent.putExtras(b)

            //start the DisplayActivity
            startActivity(intent)
        }
    }

    //stop-passvalues
    //Date-start
    private fun updateDisplay() {
        mDateDisplay!!.text = StringBuilder() // Month is 0 based so add 1
            .append(mDay).append("-")
            .append(mMonth + 1).append("-")
            .append(mYear).append(" ")
        val ed1 = findViewById<View>(R.id.lt_bp_motorcycle_upto75_nd_value) as EditText
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

    private val mDateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        updateDisplay()
    }

    override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            DATE_DIALOG_ID -> return DatePickerDialog(
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
        Toast.makeText(applicationContext, ncb[position], Toast.LENGTH_LONG)
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
                        this@longterm_bp_motorcycle_upto75,
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

    override fun onResume() {
        super.onResume()
        MyApplication.Companion.instance!!.setConnectivityListener(this)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        checkingStatus!!.notification(isConnected, this)
    }

    companion object {
        private const val TAG = "lt_bp_motorcycle_upto75"
        const val DATE_DIALOG_ID = 0
    }
}