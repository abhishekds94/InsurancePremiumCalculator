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
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.ads.InterstitialAdManager
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Abhishek on 26-Mar-17.
 */
class pp_com_goods_private_40000 : AppCompatActivity(), AdapterView.OnItemSelectedListener,
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
    var pp_com_goods_private_40000_btn: Button? = null
    var pp_com_goods_private_40000_idv_tractor_value: EditText? = null
    var pp_com_goods_private_40000_gvw_value: EditText? = null
    var pp_com_goods_private_40000_date_value: EditText? = null
    var pp_com_goods_private_40000_paod_value: EditText? = null
    var pp_com_goods_private_40000_nd_value: EditText? = null
    var pp_com_goods_private_40000_uwd_value: EditText? = null
    var pp_com_goods_private_40000_nfpp: EditText? = null
    var pp_com_goods_private_40000_coolie: EditText? = null
    var pp_com_goods_private_40000_zone: RadioGroup? = null
    var pp_com_goods_private_40000_lpg: RadioGroup? = null
    var pp_com_goods_private_40000_lpgtype: RadioGroup? = null
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
        checkfunction(this@pp_com_goods_private_40000)
        setContentView(R.layout.pp_com_goods_private_40000)
        getSupportActionBar()!!.setTitle("Private Commercial Vehicle - Package Policy")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        interstitialAdManager = InterstitialAdManager(this)

        //Date-start
        mDateDisplay = findViewById<View>(R.id.pp_com_goods_private_40000_date_value) as TextView?
        mPickDate = findViewById<View>(R.id.pp_com_goods_private_40000_date_btn) as Button?
        mPickDate!!.setOnClickListener { showDialog(pp_com_goods_private_40000.Companion.DATE_DIALOG_ID) }
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        updateDisplay()


        //Date-end


        //spinner-start
        val spin = findViewById<View>(R.id.pp_com_goods_private_40000_ncb_value) as Spinner
        spin.onItemSelectedListener = this
        val aa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ncb)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = aa
        //spinner-end
        findViewById<View>(R.id.pp_com_goods_private_40000_btn).setOnClickListener(
            listener_pp_com_goods_private_40000_btn
        )


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        pp_com_goods_private_40000_btn!!.setOnClickListener(this)
        //stop-passthevalues
        val nd_no = findViewById<View>(R.id.pp_com_goods_private_40000_nd_value_no) as RadioButton
        nd_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_com_goods_private_40000_nd_value) as EditText
            ed1.isEnabled = false
            ed1.setText("0")
            ed1.visibility = View.VISIBLE
            val tv = findViewById<View>(R.id.percen) as TextView
            tv.visibility = View.VISIBLE
        }
        val nd_yes = findViewById<View>(R.id.pp_com_goods_private_40000_nd_value_yes) as RadioButton
        nd_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_com_goods_private_40000_nd_value) as EditText
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
            val tv = findViewById<View>(R.id.percen) as TextView
            tv.visibility = View.VISIBLE
            display_nd()
        }
        val pa_no = findViewById<View>(R.id.pp_com_goods_private_40000_paod_value_no) as RadioButton
        pa_no.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_com_goods_private_40000_paod_value) as EditText
            ed1.setText("0")
            ed1.isEnabled = false
        }
        val pa_yes =
            findViewById<View>(R.id.pp_com_goods_private_40000_paod_value_yes) as RadioButton
        pa_yes.setOnClickListener {
            val ed1 = findViewById<View>(R.id.pp_com_goods_private_40000_paod_value) as EditText
            ed1.setText("320")
            ed1.isEnabled = true
        }
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

    fun display_nd() {
        val ed1 = findViewById<View>(R.id.pp_com_goods_private_40000_nd_value) as EditText
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

    var listener_pp_com_goods_private_40000_btn = View.OnClickListener {
        val intent =
            Intent(this@pp_com_goods_private_40000, ppdisplay_com_goods_private_40000::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        pp_com_goods_private_40000_btn =
            findViewById<View>(R.id.pp_com_goods_private_40000_btn) as Button?
        pp_com_goods_private_40000_idv_tractor_value =
            findViewById<View>(R.id.pp_com_goods_private_40000_tractor_act) as EditText?
        pp_com_goods_private_40000_gvw_value =
            findViewById<View>(R.id.pp_com_goods_private_40000_gvw_value) as EditText?
        pp_com_goods_private_40000_date_value =
            findViewById<View>(R.id.pp_com_goods_private_40000_date_value) as EditText?
        pp_com_goods_private_40000_nd_value =
            findViewById<View>(R.id.pp_com_goods_private_40000_nd_value) as EditText?
        pp_com_goods_private_40000_uwd_value =
            findViewById<View>(R.id.pp_com_goods_private_40000_uwd_value) as EditText?
        pp_com_goods_private_40000_coolie =
            findViewById<View>(R.id.pp_com_goods_private_40000_coolie) as EditText?
        pp_com_goods_private_40000_nfpp =
            findViewById<View>(R.id.pp_com_goods_private_40000_nfpp) as EditText?
        val scrollview = findViewById<View>(R.id.pp_com_goods_private_40000_sv) as ScrollView
        pp_com_goods_private_40000_uwd_value!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_NEXT) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_com_goods_private_40000_coolie!!.requestFocus()
            }
            false
        }
        pp_com_goods_private_40000_nfpp!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_com_goods_private_40000_btn!!.requestFocus()
            }
            false
        }
        pp_com_goods_private_40000_zone =
            findViewById<View>(R.id.pp_com_goods_private_40000_zone) as RadioGroup?
        pp_com_goods_private_40000_paod_value =
            findViewById<View>(R.id.pp_com_goods_private_40000_paod_value) as EditText?
    }

    override fun onClick(v: View) {
        val intent = Intent(getApplicationContext(), ppdisplay_com_goods_private_40000::class.java)
        //Create a bundle object
        val b = Bundle()

        //Inserts a String value into the mapping of this Bundle
        b.putString(
            "pp_com_goods_private_40000_idv_value",
            pp_com_goods_private_40000_idv_tractor_value!!.text.toString()
        )
        b.putString(
            "pp_com_goods_private_40000_gvw_value",
            pp_com_goods_private_40000_gvw_value!!.text.toString()
        )
        b.putString(
            "pp_com_goods_private_40000_date_value",
            pp_com_goods_private_40000_date_value!!.text.toString()
        )
        b.putString(
            "pp_com_goods_private_40000_nd_value",
            pp_com_goods_private_40000_nd_value!!.text.toString()
        )
        b.putString(
            "pp_com_goods_private_40000_uwd_value",
            pp_com_goods_private_40000_uwd_value!!.text.toString()
        )
        b.putString(
            "pp_com_goods_private_40000_coolie",
            pp_com_goods_private_40000_coolie!!.text.toString()
        )
        b.putString(
            "pp_com_goods_private_40000_nfpp",
            pp_com_goods_private_40000_nfpp!!.text.toString()
        )
        b.putString(
            "pp_com_goods_private_40000_paod_value",
            pp_com_goods_private_40000_paod_value!!.text.toString()
        )
        b.putString("pp_com_goods_private_40000_ncb_value", selected)
        val id1 = pp_com_goods_private_40000_zone!!.checkedRadioButtonId
        val radioButton1 = findViewById<View>(id1) as RadioButton
        b.putString("pp_com_goods_private_40000_zone", radioButton1.text.toString())
        b.putInt("year", mYear)
        b.putInt("month", mMonth)
        b.putInt("day", mDay)
        //Add the bundle to the intent.
        intent.putExtras(b)
        val idv_value = pp_com_goods_private_40000_idv_tractor_value!!.text.toString()
        val gvw_value_val = pp_com_goods_private_40000_gvw_value!!.text.toString()
        val uwd_value = pp_com_goods_private_40000_uwd_value!!.text.toString()
        val coolie_value = pp_com_goods_private_40000_coolie!!.text.toString()
        val nfpp_value = pp_com_goods_private_40000_nfpp!!.text.toString()
        val date_value = pp_com_goods_private_40000_date_value!!.text.toString()
        if (idv_value == "" || gvw_value_val == "" || date_value == "" || uwd_value == "" || coolie_value == "" || nfpp_value == "") {
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
            val gvw_value = Integer.valueOf(pp_com_goods_private_40000_gvw_value!!.text.toString())
            if (gvw_value > 40000 || gvw_value < 20001) {
                Toast.makeText(
                    getApplicationContext(),
                    "PLease enter correct GVW value",
                    Toast.LENGTH_LONG
                ).show()
            } else {
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
    }

    private val mDateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        updateDisplay()
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            pp_com_goods_private_40000.Companion.DATE_DIALOG_ID -> return DatePickerDialog(
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
        private const val TAG = "pp_com_goods_private_40000"
        const val DATE_DIALOG_ID = 0
    }
}