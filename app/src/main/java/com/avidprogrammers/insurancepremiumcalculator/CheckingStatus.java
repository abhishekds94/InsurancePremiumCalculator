package com.avidprogrammers.insurancepremiumcalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Abhishek on 26-Mar-17.
 */
class CheckingStatus{
    static int x=-1;
    AlertDialog dialog=null;
    AlertDialog.Builder builder;
    public void notification(boolean isConnected, final Context context){
        builder=new AlertDialog.Builder(context);

        if (!isConnected&&x!=0){
            x=0;
            builder.setTitle("Motor Insurance Premium Calculator");
            builder.setMessage("No Internet Connection.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    /*int pid = android.os.Process.myPid();

                    android.os.Process.killProcess(pid);
//                    System.exit(0);
                    */

                 /*   if (Build.VERSION.SDK_INT >= 15 && Build.VERSION.SDK_INT < 21) {
                        Toast.makeText(context, "1 ", Toast.LENGTH_SHORT).show();
                        ((Activity) context).finishAffinity();
                        ((Activity) context).finish();
                    } else {
                  */      Toast.makeText(context, "2 ", Toast.LENGTH_SHORT).show();
                    //((Activity) context).finish();
                    //System.exit(0);
                    ((Activity) context).finishAndRemoveTask();
                    ((Activity) context).finishAffinity();
                    //  }

                    System.exit(0);
                    //android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            builder.setCancelable(false);
            dialog=builder.create();
            dialog.show();
        }else if(isConnected&&x!=1){
            x=1;
//            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            if(dialog!=null){
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                }
            }
        }
    }
}
