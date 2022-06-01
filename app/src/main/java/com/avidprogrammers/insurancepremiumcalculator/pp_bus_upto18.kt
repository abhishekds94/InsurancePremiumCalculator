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
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.avidprogrammers.insurancepremiumcalculator.ConnectivityReceiver.ConnectivityReceiverListener
import com.google.android.gms.ads.AdView
import java.util.concurrent.TimeUnit

/**
 * Created by Abhishek on 26-Mar-17.
 */
class pp_bus_upto18 : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener,
    ConnectivityReceiverListener {
    var conn: ConnectivityReceiver? = null
    var checkingStatus: CheckingStatus? = null
    private val mAdView: AdView? = null
    private var mDateDisplay: TextView? = null
    private var mPickDate: Button? = null
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    var pp_bus_upto18_btn: Button? = null
    var pp_bus_upto18_idv_value: EditText? = null
    var pp_bus_upto18_date_value: EditText? = null
    var pp_bus_upto18_cc_value: EditText? = null
    var pp_bus_upto18_nd_value: EditText? = null

    //EditText pp_bus_upto18_ndd_value;
    var pp_bus_upto18_uwd_value: EditText? = null
    var pp_bus_scpassengers_upto18: EditText? = null
    var pp_bus_driver_upto18: EditText? = null
    var pp_bus_conductor_upto18: EditText? = null
    var pp_bus_upto18_ndd_value: EditText? = null
    var pp_bus_upto18_paod_value: EditText? = null
    var pp_bus_upto18_zone: RadioGroup? = null

    //Radio Button Declaring
    var pp_bus_upto18_nd_value_no: RadioButton? = null
    var pp_bus_upto18_nd_value_yes: RadioButton? = null
    var pp_bus_upto18_idv_value_c: RadioButton? = null
    var pp_bus_upto18_idv_value_b: RadioButton? = null
    var pp_bus_upto18_idv_value_a: RadioButton? = null
    var nild_value = 0.0
    protected override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(conn)
    }

    //EditText ndd;
    //int num1;
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingStatus = CheckingStatus()
        conn = ConnectivityReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(conn, intentFilter)
        checkfunction(this@pp_bus_upto18)
        setContentView(R.layout.pp_bus_upto18)
        getSupportActionBar()!!.setTitle("Passenger Vehicle Package Policy")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

/*        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        val pa_no: RadioButton = findViewById<RadioButton>(R.id.pp_bus_upto18_paod_value_no)
        pa_no.setOnClickListener {
            pp_bus_upto18_paod_value!!.setText("0")
            pp_bus_upto18_paod_value!!.isEnabled = false
        }
        val pa_yes: RadioButton = findViewById<RadioButton>(R.id.pp_bus_upto18_paod_value_yes)
        pa_yes.setOnClickListener {
            pp_bus_upto18_paod_value!!.setText("320")
            pp_bus_upto18_paod_value!!.isEnabled = true
        }


        //Date-start
        mDateDisplay = findViewById<View>(R.id.pp_bus_upto18_date_value) as TextView?
        mPickDate = findViewById<View>(R.id.txtbtn) as Button?
        mPickDate!!.setOnClickListener { showDialog(pp_bus_upto18.Companion.DATE_DIALOG_ID) }
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]

        //Date-end


        //spinner-start
        val spin = findViewById<View>(R.id.pp_bus_upto18_ncb_value) as Spinner
        spin.onItemSelectedListener = this
        val aa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ncb)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = aa
        //spinner-end
        findViewById<View>(R.id.pp_bus_upto18_btn).setOnClickListener(listener_pp_bus_upto18_btn)


        //start-passthevalues
        //Get the ids of view objects
        findAllViewsId()
        updateDisplay()
        radio_listener()
        pp_bus_upto18_btn!!.setOnClickListener(this)
        //stop-passthevalues

        //ndd= (EditText) findViewById(R.id.pp_bus_upto18_ndd_value);
        //ndd.addTextChangedListener(textWatcher);
    }

    var listener_pp_bus_upto18_btn = View.OnClickListener {
        val intent = Intent(this@pp_bus_upto18, ppdisplay_bus_upto18::class.java)
        startActivity(intent)
    }

    //start-passthevalues
    private fun findAllViewsId() {
        pp_bus_upto18_btn = findViewById<View>(R.id.pp_bus_upto18_btn) as Button?
        pp_bus_upto18_idv_value = findViewById<View>(R.id.pp_bus_upto18_idv_value) as EditText?
        pp_bus_upto18_date_value = findViewById<View>(R.id.pp_bus_upto18_date_value) as EditText?
        pp_bus_upto18_cc_value = findViewById<View>(R.id.pp_bus_upto18_cc_value) as EditText?
        pp_bus_upto18_nd_value = findViewById<View>(R.id.pp_bus_upto18_nd_value) as EditText?
        //pp_bus_upto18_ndd_value = (EditText) findViewById(R.id.pp_bus_upto18_ndd_value);
        pp_bus_upto18_uwd_value = findViewById<View>(R.id.pp_bus_upto18_uwd_value) as EditText?
        pp_bus_scpassengers_upto18 =
            findViewById<View>(R.id.pp_bus_scpassengers_upto18) as EditText?
        pp_bus_driver_upto18 = findViewById<View>(R.id.pp_bus_driver_upto18) as EditText?
        pp_bus_conductor_upto18 = findViewById<View>(R.id.pp_bus_conductor_upto18) as EditText?
        pp_bus_upto18_ndd_value = findViewById<EditText>(R.id.pp_bus_upto18_ndd_value)
        pp_bus_upto18_paod_value = findViewById<EditText>(R.id.pp_bus_upto18_paod)
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (pp_bus_upto18_ndd_value!!.text.length > 0) {
                    val num = pp_bus_upto18_ndd_value!!.text.toString().toInt()
                    if (num >= 0 && num <= 35) {
                        //save the number
                        //num1=num;
                    } else {
                        Toast.makeText(
                            this@pp_bus_upto18,
                            "NDD range should be of 1-35",
                            Toast.LENGTH_SHORT
                        ).show()
                        pp_bus_upto18_ndd_value!!.setText("")
                        //num1=-1;
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        pp_bus_upto18_ndd_value!!.addTextChangedListener(textWatcher)
        val scrollview = findViewById<View>(R.id.pp_bus_upto18_sv) as ScrollView
        pp_bus_conductor_upto18!!.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) scrollview.post {
                scrollview.scrollTo(0, scrollview.bottom)
                pp_bus_upto18_btn!!.requestFocus()
            }
            false
        }
        pp_bus_upto18_zone = findViewById<View>(R.id.pp_bus_upto18_zone) as RadioGroup?

        //radiobutton
        // zones a,b,c
        pp_bus_upto18_idv_value_a =
            findViewById<View>(R.id.pp_bus_upto18_idv_value_a) as RadioButton?
        pp_bus_upto18_idv_value_b =
            findViewById<View>(R.id.pp_bus_upto18_idv_value_b) as RadioButton?
        pp_bus_upto18_idv_value_c =
            findViewById<View>(R.id.pp_bus_upto18_idv_value_c) as RadioButton?
        // ND Yes or No
        pp_bus_upto18_nd_value_yes =
            findViewById<View>(R.id.pp_bus_upto18_nd_value_yes) as RadioButton?
        pp_bus_upto18_nd_value_no =
            findViewById<View>(R.id.pp_bus_upto18_nd_value_no) as RadioButton?
    }

    fun radio_listener() {
        pp_bus_upto18_nd_value_yes!!.setOnClickListener { nd() }
        pp_bus_upto18_nd_value_no!!.setOnClickListener {
            pp_bus_upto18_nd_value!!.setText(
                "0",
                TextView.BufferType.EDITABLE
            )
        }
    }

    var ncb_value = 0
    var zone = arrayOf(
        doubleArrayOf(1.680, 1.722, 1.764),
        doubleArrayOf(1.672, 1.714, 1.756),
        doubleArrayOf(1.656, 1.697, 1.739)
    )

    fun value(): Double {
        var Totalcost = 350.0
        if (!pp_bus_upto18_idv_value!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            if (!pp_bus_upto18_uwd_value!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                if (!pp_bus_scpassengers_upto18!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                    if (!pp_bus_driver_upto18!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                        if (!pp_bus_upto18_ndd_value!!.text.toString().trim { it <= ' ' }
                                .isEmpty()) {
                            if (!pp_bus_conductor_upto18!!.text.toString().isEmpty()) {
                                Totalcost += pp_bus_upto18_idv_value!!.text.toString()
                                    .toInt() * zone_checking() / 100
                                if (pp_bus_upto18_nd_value_yes!!.isChecked) {
                                    Totalcost *= 1.18
                                }
                                Totalcost = Math.ceil(Totalcost)
                                val temp = Totalcost * pp_bus_upto18_nd_value!!.text.toString()
                                    .toInt() / 100
                                if (pp_bus_upto18_nd_value_yes!!.isChecked) {
                                    nild_value = temp
                                }
                                val temp2 =
                                    temp * pp_bus_upto18_ndd_value!!.text.toString().toInt() / 100
                                Totalcost += temp - temp2
                                //Totalcost+=(Totalcost*(Integer.parseInt(pp_bus_upto18_nd_value.getText().toString()))/100);
                                Totalcost -= Totalcost * pp_bus_upto18_uwd_value!!.text.toString()
                                    .toInt() / 100
                                Totalcost -= Totalcost * ncb_value / 100
                                return Math.floor(Totalcost)
                            } else {
                                pp_bus_conductor_upto18!!.error = "Empty Field"
                            }
                        } else {
                            pp_bus_upto18_ndd_value!!.error = "Empty Field"
                        }
                    } else {
                        pp_bus_driver_upto18!!.error = "Empty Field"
                    }
                } else {
                    pp_bus_scpassengers_upto18!!.error = "Empty Field"
                }
            } else {
                pp_bus_upto18_uwd_value!!.error = "Empty Field"
            }
        } else {
            pp_bus_upto18_idv_value!!.error = "Empty Field"
        }
        return 0.0
    }

    override fun onClick(v: View) {
        val x = value()
        if (x != 0.0) {
            val intent = Intent(getApplicationContext(), ppdisplay_bus_upto18::class.java)
            //Create a bundle object
            val b = Bundle()

            //Inserts a String value into the mapping of this Bundle
            b.putString("pp_bus_upto18_idv_value", pp_bus_upto18_idv_value!!.text.toString())
            b.putString("pp_bus_upto18_date_value", pp_bus_upto18_date_value!!.text.toString())
            b.putString("pp_bus_upto18_cc_value", pp_bus_upto18_cc_value!!.text.toString())
            b.putString("pp_bus_upto18_nd_value", pp_bus_upto18_nd_value!!.text.toString())
            b.putString("pp_bus_upto18_ndd_value", pp_bus_upto18_ndd_value!!.text.toString())
            b.putString("pp_bus_upto18_uwd_value", pp_bus_upto18_uwd_value!!.text.toString())
            b.putString("pp_bus_upto18_paod_value", pp_bus_upto18_paod_value!!.text.toString())
            b.putString("pp_bus_scpassengers_upto18", pp_bus_scpassengers_upto18!!.text.toString())
            val z = pp_bus_driver_upto18!!.text.toString().toInt() * 50
            val z1 = pp_bus_conductor_upto18!!.text.toString().toInt() * 50
            b.putString("pp_bus_driver_upto18", z.toString())
            b.putString("pp_bus_conductor_upto18", z1.toString())

            //external inputs
            if (pp_bus_upto18_nd_value_yes!!.isChecked) {
                b.putString("pp_bus_imt_value", 15.toString())
            } else {
                b.putString("pp_bus_imt_value", 0.toString())
            }
            b.putString("pp_bus_ncb_value", ncb_value.toString())
            b.putString("pp_bus_total_value", x.toString())
            val id1 = pp_bus_upto18_zone!!.checkedRadioButtonId
            val radioButton1 = findViewById<View>(id1) as RadioButton
            b.putString("pp_bus_upto18_zone", radioButton1.text.toString())


            //Add the bundle to the intent.
            intent.putExtras(b)

            //start the DisplayActivity
            startActivity(intent)
        }
    }

    //stop-passthevalues
    fun nd() {
        val x = score_time_update(mDay, mMonth + 1, mYear)
        if (pp_bus_upto18_nd_value_yes!!.isChecked) {
            if (x < 1) {
                pp_bus_upto18_nd_value!!.setText("15", TextView.BufferType.EDITABLE)
            } else if (x >= 1 && x < 5) {
                pp_bus_upto18_nd_value!!.setText("25", TextView.BufferType.EDITABLE)
            } else if (x >= 5) {
                pp_bus_upto18_nd_value!!.setText("0", TextView.BufferType.EDITABLE)
            }
        } else {
            pp_bus_upto18_nd_value!!.setText("0", TextView.BufferType.EDITABLE)
        }
    }

    fun zone_checking(): Double {
        val l = score_time_update(mDay, mMonth + 1, mYear)
        var x: String? = null
        if (pp_bus_upto18_idv_value_a!!.isChecked) {
            x = "A"
        } else if (pp_bus_upto18_idv_value_b!!.isChecked) {
            x = "B"
        } else if (pp_bus_upto18_idv_value_c!!.isChecked) {
            x = "C"
        }
        if (l < 5) {
            assert(x != null)
            if (x == "A") {
                return zone[0][0]
            } else if (x == "B") {
                return zone[1][0]
            } else if (x == "C") {
                return zone[2][0]
            }
        } else if (l >= 5 && l < 7) {
            if (x == "A") {
                return zone[0][1]
            } else if (x == "B") {
                return zone[1][1]
            } else if (x == "C") {
                return zone[2][1]
            }
        } else if (l > 7) {
            if (x == "A") {
                return zone[0][2]
            } else if (x == "B") {
                return zone[1][2]
            } else if (x == "C") {
                return zone[2][2]
            }
        }
        return 0.0
    }

    fun score_time_update(x: Int, y: Int, z: Int): Long {
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
        return daydiff / 365
    }

    //Date-start
    private fun updateDisplay() {
        mDateDisplay!!.text = StringBuilder() // Month is 0 based so add 1
            .append(mDay).append("-")
            .append(mMonth + 1).append("-")
            .append(mYear).append(" ")
        nd()
    }

    private val mDateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        updateDisplay()
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            pp_bus_upto18.Companion.DATE_DIALOG_ID -> return DatePickerDialog(
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
        ncb_value = ncb[position].toInt()
        Toast.makeText(getApplicationContext(), ncb[position], Toast.LENGTH_LONG)
    }

    /*    //To check NDD value
    TextWatcher textWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(ndd.getText().length()>0) {
                int num = Integer.parseInt(ndd.getText().toString());
                if (num>=0 && num<=35)
                {
                    //save the number
                    num1=num;
                }
                else{
                    Toast.makeText(pp_bus_upto18.this,"NDD range should be of 1-35",Toast.LENGTH_SHORT).show();
                    ndd.setText("");
                    num1=-1;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };*/
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
        private const val TAG = "pp_bus_upto18"
        const val DATE_DIALOG_ID = 0
    }
}