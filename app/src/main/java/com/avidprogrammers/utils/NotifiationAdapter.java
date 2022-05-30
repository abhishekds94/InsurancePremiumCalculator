package com.avidprogrammers.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avidprogrammers.database.DatabaseHelper;
import com.avidprogrammers.database.NotificationBean;
import com.avidprogrammers.insurancepremiumcalculator.CC_motorcycle;
import com.avidprogrammers.insurancepremiumcalculator.MainActivity;
import com.avidprogrammers.insurancepremiumcalculator.NotificationActivity;
import com.avidprogrammers.insurancepremiumcalculator.R;
import com.avidprogrammers.insurancepremiumcalculator.WebViewActivity;
import com.avidprogrammers.insurancepremiumcalculator.home_activity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotifiationAdapter extends RecyclerView.Adapter<NotifiationAdapter.MyViewHolder> {
    ArrayList<NotificationBean> arrayList;
    DatabaseHelper databaseHelper;
    private Context context;
    private InterstitialAd interstitialAd;

    public NotifiationAdapter(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        int count = databaseHelper.getNotificationCount();
        if(count == 0)
        {
            Toast.makeText(context,"No Notification to display!",Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context,home_activity.class));
            ((AppCompatActivity)context).finish();
        }
        else
        arrayList = (ArrayList<NotificationBean>) databaseHelper.getAllNotifications();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_recyclerview_layout,
                parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.content.setText(arrayList.get(position).getUrl());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldFormatedDate = null;
        try {
            oldFormatedDate = formatter.parse(arrayList.get(position).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.time.setText(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(oldFormatedDate));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,WebViewActivity.class);
                intent.putExtra("url",arrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title,content,time;
        public View itemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.notification_title);
            content = itemView.findViewById(R.id.notification_url);
            time = itemView.findViewById(R.id.notification_time);
        }
    }
}
