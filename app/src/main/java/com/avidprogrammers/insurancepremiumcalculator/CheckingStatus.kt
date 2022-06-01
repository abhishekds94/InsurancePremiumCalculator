package com.avidprogrammers.insurancepremiumcalculator

import android.app.Activity
import android.app.AlertDialog
import android.content.Context

/**
 * Created by Abhishek on 26-Mar-17.
 */
class CheckingStatus {
    var dialog: AlertDialog? = null
    var builder: AlertDialog.Builder? = null
    fun notification(isConnected: Boolean, context: Context) {
        builder = AlertDialog.Builder(context)
        if (!isConnected && CheckingStatus.Companion.x != 0) {
            CheckingStatus.Companion.x = 0
            builder!!.setTitle("Motor Insurance Premium Calculator")
            builder!!.setMessage("No Internet Connection.")
            builder!!.setPositiveButton("OK") { dialogInterface, i ->
                /*int pid = android.os.Process.myPid();
        
                            android.os.Process.killProcess(pid);
        //                    System.exit(0);
                            */

                /*   if (Build.VERSION.SDK_INT >= 15 && Build.VERSION.SDK_INT < 21) {
                                Toast.makeText(context, "1 ", Toast.LENGTH_SHORT).show();
                                ((Activity) context).finishAffinity();
                                ((Activity) context).finish();
                            } else {
                          */
                //((Activity) context).finish();
                //System.exit(0);
                (context as Activity).finishAndRemoveTask()
                context.finishAffinity()
                //  }
                System.exit(0)
                //android.os.Process.killProcess(android.os.Process.myPid());
            }
            builder!!.setCancelable(false)
            dialog = builder!!.create()
            dialog!!.show()
        } else if (isConnected && CheckingStatus.Companion.x != 1) {
            CheckingStatus.Companion.x = 1
            //            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            if (dialog != null) {
                if (dialog!!.isShowing) {
                    dialog!!.dismiss()
                } else {
                    dialog!!.dismiss()
                }
            }
        }
    }

    companion object {
        var x = -1
    }
}