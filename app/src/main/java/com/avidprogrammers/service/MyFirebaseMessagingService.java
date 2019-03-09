package com.avidprogrammers.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.avidprogrammers.app.Config;
import com.avidprogrammers.database.DatabaseHelper;
import com.avidprogrammers.insurancepremiumcalculator.MainActivity;
import com.avidprogrammers.insurancepremiumcalculator.NotificationActivity;
import com.avidprogrammers.insurancepremiumcalculator.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private DatabaseHelper databaseHelper;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        int notificationCount = pref.getInt("newNoti",0) + 1;
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("newNoti", notificationCount);
        editor.commit();

        if(remoteMessage.getData() != null)
        {
            sendNotification(remoteMessage);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendNotification(RemoteMessage remoteMessage) {
        Map<String , String> data = remoteMessage.getData();
        String channelId = data.get("channelId");
        String title = data.get("title");
        String content  = data.get("content");
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.addNotification(title,content);
        //databaseHelper.getAllNotifications();
        databaseHelper.close();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            //Configure the notification channel
            NotificationChannel notificationChannel =  new NotificationChannel(channelId,
                    "News",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This Notification  is for getting news");
            notificationChannel.enableVibration(true);

            //create notification channel
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent contentIntent = new Intent(this, NotificationActivity.class);
        contentIntent.putExtra("NOTIFICATION_ID", 1);
        PendingIntent pendingContentIntent = PendingIntent.getActivity(this, 0, contentIntent, 0);

        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId);
        builder.setAutoCancel(true)
                .setLargeIcon(drawableToBitmap(getDrawable(R.drawable.applogo)))
                .setSmallIcon(R.drawable.notification_small_icon2)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingContentIntent)
                .setSound(sound);

        notificationManager.notify(1,builder.build());

        NotificationUtil notificationUtils = new NotificationUtil(getApplicationContext());
        notificationUtils.playNotificationSound();
    }

    public Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
