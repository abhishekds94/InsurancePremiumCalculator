package com.avidprogrammers.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Details
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notification_db";
    private static final String  TABLE_NAME = "noti_table";
    private static final String COLUMN1 = "news_title";
    private static final String COLUMN2 = "news_url";
    private static final String COLUMN3 = "notification_time";

    //Database Queries
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" (" +
            COLUMN1+" TEXT, "+
            COLUMN2+" TEXT, "+
            COLUMN3+" DATETIME DEFAULT CURRENT_TIMESTAMP)";

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    //Method to insert Notification Details
    public long addNotification(String title, String  url)
    {
        int count = getNotificationCount();
        Log.e("Notification count", ""+count );
        if(count < 10) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN1, title);
            values.put(COLUMN2, url);
            values.put(COLUMN3,getDateTime());

            long id = db.insert(TABLE_NAME, null, values);
            getAllNotifications();
            db.close();

            return id;
        }
        else
        {
            List<NotificationBean> notificationBeanList  = getAllNotifications();
            int size = notificationBeanList.size()-1;
            Log.e("Notification size", ""+size );
            for(int i=size; i > 8 ; i--)
            {
                deleteNotification(notificationBeanList.get(i));
            }
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN1, title);
            values.put(COLUMN2, url);

            long id = db.insert(TABLE_NAME, null, values);
            getAllNotifications();
            db.close();

            return id;
        }
    }

    //Methods to get Notification Count
    public int getNotificationCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    @SuppressLint("Range")
    public List<NotificationBean> getAllNotifications() {
        List<NotificationBean> notifications = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                COLUMN3 + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NotificationBean notification = new NotificationBean();
                notification.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN1)));
                notification.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN2)));
                notification.setTime(cursor.getString(cursor.getColumnIndex(COLUMN3)));

                Log.e("Notification", notification.toString() );

                notifications.add(notification);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notifications list
        return notifications;
    }

    public void deleteNotification(NotificationBean notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN3 + " = ?",
                new String[]{String.valueOf(notification.getTime())});
        db.close();
        Log.e("Notification deleted", notification.toString());
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
