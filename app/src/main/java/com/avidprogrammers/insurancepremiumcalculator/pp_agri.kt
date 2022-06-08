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

/**
 * Created by Abhishek on 26-Mar-17.
 */
class pp_agri : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener,
    ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    private var mDateDisplay: TextView? = null
    private var mPickDate: Button? = null
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    var diffInDays: Long = 0
    var pp_agri_btn: Button? = null
    var pp_agri_idv_tractor_value: EditText? = null
    var pp_agri_idv_trailer_value: EditText? = null
    var pp_agri_date_value: EditText? = null
    var pp_agri_cc_value: EditText? = null
    var pp_agri_nd_value: EditText? = null
    var pp_agri_uwd_value: EditText? = null
    var pp_agri_nfpp: EditText? = null
    var pp_agri_coolie: EditText? = null
    var pp_agri_gvw_value: EditText? = null
    var pp_agri_paod_value: EditText? = null
    var pp_agri_zone: RadioGroup? = null
    var pp_agri_nd: RadioGroup? = null
    var pp_agri_trailer: RadioGroup? = null
    var selected: String? = null

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
        checkfunction(this@pp_agri)
        setContentView(R.layout.pp_agri)
        getSupportActionBar()!!.setTitle("Tractors & Trailers Package Policy")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        interstitialAdManager = InterstitialAdManager(this)

        val pa_no: RadioButton = findViewById<RadioButton>(R.id.pp_agri_paod_value_no)
        pa_no.setOnClickListener {
            pp_agri_paod_value!!.setText("0")
            pp_agri_paod_value!!.isEnabled = false
        }
        val pa_yes: RadioButton = findViewById<RadioButton>(R.id.pp_agri_paod_value_yes)
        pa_yes.setOnClickListener {
            pp_agri_paod_value!!.setText("320")
            pp_agri_paod_value!!.isEnabled = true
        }
        val trailer_no = findViewById<View>(R.id.pp_agri_trailer_no) as RadioButton
        trailer_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_agri_trailer_value_act) as EditText
            ed1.isEnabled = false
            ed1.setText("0")
        }
        val trailer_yes = findViewById<View>(R.id.pp_agri_trailer_yes) as RadioButton
        trailer_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_agri_trailer_value_act) as EditText
            ed1.isEnabled = true
            ed1.setText("")
            ed1.visibility = View.VISIBLE
            val tv = findViewById<View>(R.id.rupee) as TextView
            tv.visibility = View.VISIBLE
        }
        val nd_no = findViewById<View>(R.id.pp_agri_nd_value_no) as RadioButton
        nd_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_agri_nd_value) as EditText
            ed1.isEnabled = false
            ed1.setText("0")
        }
        val nd_yes = findViewById<View>(R.id.pp_agri_nd_value_yes) as RadioButton
        nd_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_agri_nd_value) as EditText
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
            val tv = findViewById<View>(R.id.percen) as TextView
            tv.visibility = View.VISIBLE
            display_nd()
        }


        //Date-start
        mDateDisplay = findViewById<View>(R.id.pp_agri_date_value) as TextView?
        mPickDate = findViewById<View>(R.id.pp_agri_date_btn) as Button?
        mPickDate!!.setOnClickListener { showDialog(pp_agri.Companion.DATE_DIALOG_ID) }
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]


        //Date-end


        //spinner-start
        val spin = findViewById<View>(R.id.pp_agri_ncb_value) as Spinner
        spin.onItemSelectedListener = this
        val aa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ncb)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = aa
        //spinner-end
        findViewById<View>(R.id.pp_agri_btn).setOnClickListener(listener_pp_agri_btn)


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        updateDisplay()
        pp_agri_btn!!.setOnClickListener(this)
        //stop-passthevalues
    }

    var listener_pp_agri_btn = View.OnClickListener {
        val intent = Intent(this@pp_agri, ppdisplay_agri::class.java)
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
        pp_agri_btn = findViewById<View>(R.id.pp_agri_btn) as Button?
        pp_agri_idv_tractor_value = findViewById<View>(R.id.pp_agri_tractor_act) as EditText?
        pp_agri_idv_trailer_value = findViewById<View>(R.id.pp_agri_trailer_value_act) as EditText?
        pp_agri_date_value = findViewById<View>(R.id.pp_agri_date_value) as EditText?
        pp_agri_nd_value = findViewById<View>(R.id.pp_agri_nd_value) as EditText?
        pp_agri_uwd_value = findViewById<View>(R.id.pp_agri_uwd_value) as EditText?
        pp_agri_coolie = findViewById<View>(R.id.pp_agri_coolie) as EditText?
        pp_agri_paod_value = findViewById<EditText>(R.id.pp_agri_paod)
        val scrollview = findViewById<View>(R.id.pp_agri_sv) as ScrollView
        pp_agri_coolie!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_agri_btn!!.requestFocus()
            }
            false
        }
        pp_agri_zone = findViewById<View>(R.id.pp_agri_zone) as RadioGroup?
        pp_agri_trailer = findViewById<View>(R.id.pp_agri_trailer) as RadioGroup?
        pp_agri_nd = findViewById<View>(R.id.pp_agri_nd) as RadioGroup?
    }

    override fun onClick(v: View) {
        val idv_value = pp_agri_idv_tractor_value!!.text.toString()
        val idv_trailer_value = pp_agri_idv_trailer_value!!.text.toString()
        val nd_value = pp_agri_nd_value!!.text.toString()
        val uwd_value = pp_agri_uwd_value!!.text.toString()
        val coolie_value = pp_agri_coolie!!.text.toString()
        val rb2 = findViewById<View>(R.id.pp_agri_trailer_yes) as RadioButton
        val rb1 = findViewById<View>(R.id.pp_agri_trailer_no) as RadioButton
        if (idv_value == "" || uwd_value == "" || coolie_value == "") {
            Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (pp_agri_idv_tractor_value!!.visibility == View.VISIBLE && idv_trailer_value == "") {
                Toast.makeText(
                    getApplicationContext(),
                    "Please enter all fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(getApplicationContext(), ppdisplay_agri::class.java)
                //Create a bundle object
                val b = Bundle()

                //Inserts a String value into the mapping of this Bundle
                b.putString(
                    "pp_agri_idv_tractor_value",
                    pp_agri_idv_tractor_value!!.text.toString()
                )
                b.putString("pp_agri_trailer_value", pp_agri_idv_trailer_value!!.text.toString())
                b.putString("pp_agri_date_value", pp_agri_date_value!!.text.toString())
                b.putString("pp_agri_nd_value", pp_agri_nd_value!!.text.toString())
                b.putString("pp_agri_uwd_value", pp_agri_uwd_value!!.text.toString())
                b.putString("pp_agri_paod_value", pp_agri_paod_value!!.text.toString())
                b.putString("pp_agri_coolie", pp_agri_coolie!!.text.toString())
                b.putString("pp_agri_spinner_value", selected)
                val id1 = pp_agri_zone!!.checkedRadioButtonId
                val radioButton1 = findViewById<View>(id1) as RadioButton
                b.putString("pp_agri_zone", radioButton1.text.toString())
                val id2 = pp_agri_trailer!!.checkedRadioButtonId
                val radioButton2 = findViewById<View>(id2) as RadioButton
                b.putString("pp_agri_trailer", radioButton2.text.toString())
                val id3 = pp_agri_nd!!.checkedRadioButtonId
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
    fun display_nd() {
        val ed1 = findViewById<View>(R.id.pp_agri_nd_value) as EditText
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
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            pp_agri.Companion.DATE_DIALOG_ID -> return DatePickerDialog(
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
        private const val TAG = "pp_agri"
        const val DATE_DIALOG_ID = 0
    }
}