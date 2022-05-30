package com.avidprogrammers.insurancepremiumcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.avidprogrammers.app.Config;
import com.avidprogrammers.database.DatabaseHelper;
import com.avidprogrammers.utils.BadgeDrawable;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class home_activity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {


    //Create New Variable of type InterstitialAd
    private Button btn_longterm;
    private Button btn_motorcycle;
    private Button btn_privatecar;
    private Button btn_taxi_upto6;
    private Button btn_bus;
    private Button btn_passauto;
    private Button btn_goodsauto_public;
    private Button btn_goodsauto_private;
    private Button btn_commercialvehiclepublic;
    private Button btn_commercialvehicleprivate;
    private Button btn_agri;
    private Button btn_terms;
    private Button btn_privacy;


    ConnectivityReceiver conn;

    CheckingStatus checkingStatus;

    private static final String TAG = "home_activity";
    private AdView mAdView;
    SharedPreferences pref;
    DatabaseHelper databaseHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        int count = pref.getInt("newNoti",0);
        if( count != 0)
        {
            MenuItem itemCart = menu.findItem(R.id.notification_menu);
            LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
            setBadgeCount(this, icon, ""+count);
            Log.e(TAG, "onCreateOptionsMenu: Setting Count" );

            // Obtain the FirebaseAnalytics instance.
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mipc_open");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.notification_menu:
                databaseHelper = new DatabaseHelper(home_activity.this);
                int count = databaseHelper.getNotificationCount();
                if(count == 0)
                {
                    Toast.makeText(home_activity.this,"No Notification to display!",Toast.LENGTH_LONG).show();
                }
                else {
                    startActivity(new Intent(home_activity.this, NotificationActivity.class));
                    finish();
                }
                break;
        }
        return true;
    }

    public void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getBooleanExtra("EXIT",false)){
            finishAndRemoveTask();
        }
        checkingStatus=new CheckingStatus();
        conn=new ConnectivityReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(conn, intentFilter);
        checkfunction(home_activity.this);

        setContentView(R.layout.activity_home);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        if(pref.getString("regID","NA").equals("NA"));
        {
            SharedPreferences.Editor editor = pref.edit();
            Task<String> refreshedToken = FirebaseMessaging.getInstance().getToken();
            FirebaseMessaging.getInstance().subscribeToTopic("all");
            editor.putString("regId", String.valueOf(refreshedToken));
            editor.commit();
        }

        btn_longterm = (Button) findViewById(R.id.longterm);
        btn_longterm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_longterm();
            }
        });
        btn_motorcycle = (Button) findViewById(R.id.motorcycle);
        btn_motorcycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_motorcycle();
            }
        });

        btn_privatecar = (Button) findViewById(R.id.privatecar);
        btn_privatecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_privatecar();
            }
        });

        btn_taxi_upto6 = (Button) findViewById(R.id.taxi_upto6);
        btn_taxi_upto6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_taxi_upto6();
            }
        });

        btn_bus = (Button) findViewById(R.id.bus);
        btn_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_bus();
            }
        });

        btn_passauto = (Button) findViewById(R.id.passauto);
        btn_passauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_passauto();
            }
        });

        btn_goodsauto_public = (Button) findViewById(R.id.goodsauto_public);
        btn_goodsauto_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_goodsauto_public();
            }
        });

        btn_goodsauto_private = (Button) findViewById(R.id.goodsauto_private);
        btn_goodsauto_private.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_goodsauto_private();
            }
        });

        btn_commercialvehiclepublic = (Button) findViewById(R.id.commercialvehiclepublic);
        btn_commercialvehiclepublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_commercialvehiclepublic();
            }
        });

        btn_commercialvehicleprivate = (Button) findViewById(R.id.commercialvehicleprivate);
        btn_commercialvehicleprivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_commercialvehicleprivate();
            }
        });

        btn_agri = (Button) findViewById(R.id.agri);
        btn_agri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_agri();
            }
        });

        btn_privacy = (Button) findViewById(R.id.privacy);
        btn_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_privacy();
            }
        });

        btn_terms = (Button) findViewById(R.id.terms);
        btn_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial_btn_terms();

            }
        });
    }

    public void showInterstitial_btn_longterm() {

        Intent inte = new Intent(home_activity.this, longterm_vehicle.class);
        startActivity(inte);
    }

    public void showInterstitial_btn_motorcycle() {
        Intent inte = new Intent(home_activity.this, CC_motorcycle.class);
        startActivity(inte);
    }

    public void showInterstitial_btn_privatecar() {
        Intent inte = new Intent(home_activity.this, CC_car.class);
        startActivity(inte);
    }

    public void showInterstitial_btn_taxi_upto6() {
        Intent inte = new Intent(home_activity.this, CC_taxi_upto6.class);
        startActivity(inte);
    }

    public void showInterstitial_btn_bus() {
        Intent inte = new Intent(home_activity.this, CC_bus.class);
        startActivity(inte);
    }

    public void showInterstitial_btn_passauto() {
        Intent inte = new Intent(home_activity.this, CC_passauto.class);
        startActivity(inte);
    }

    public void showInterstitial_btn_goodsauto_public() {
        Intent inte = new Intent(home_activity.this, pt_goodsauto_public.class);
        startActivity(inte);
    }

    public void showInterstitial_btn_goodsauto_private() {
        Intent inte = new Intent(home_activity.this, pt_goodsauto_private.class);
        startActivity(inte);
    }

    public void showInterstitial_btn_commercialvehiclepublic() {
        Intent inte = new Intent(home_activity.this, CC_commercialvehiclepublic.class);
        startActivity(inte);
    }


    public void showInterstitial_btn_commercialvehicleprivate() {
        Intent inte = new Intent(home_activity.this, CC_commercialvehicleprivate.class);
        startActivity(inte);
    }


    public void showInterstitial_btn_agri() {
        Intent inte = new Intent(home_activity.this, pt_agri.class);
        startActivity(inte);
    }


    public void showInterstitial_btn_terms() {
        Intent inte = new Intent(home_activity.this, terms.class);
        inte.putExtra("url", "http://anugrahacomputers.co.in/avidprogrammers/terms.html");
        startActivity(inte);
    }


    public void showInterstitial_btn_privacy() {
        Intent inte = new Intent(home_activity.this, privacy.class);
        inte.putExtra("url", "http://anugrahacomputers.co.in/avidprogrammers/privacy.html");
        startActivity(inte);
    }


    public void checkfunction(Context context){
        boolean isConnected=ConnectivityReceiver.isConnected();
        //notification(isConnected,lp_taxi_upto18pass.this);
        checkingStatus.notification(isConnected,context);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkingStatus.notification(isConnected,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(conn);
    }
}