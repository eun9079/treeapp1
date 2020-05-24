package com.tree.treeapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;

public class WaterCycleManager {

    private final static String TAG = WaterCycleManager.class.getSimpleName();

    public static void registerAlarm(Context context, int cycle, String name, String nickname) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CyclerReciver.class);
        intent.putExtra(TreeItem.TREE_NAME, name);
        intent.putExtra(TreeItem.TREE_NICKNAME, nickname);

        long now = System.currentTimeMillis();

        int hour = Integer.parseInt(String.valueOf(DateFormat.format("HH", now))) + 1;
        String minute = String.valueOf(DateFormat.format("mm", now));

        hour = 24 == hour ? 0 : hour;
        Log.d(TAG, "registerAlarm: " + hour);

        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                60 * 1000 * 60 * cycle, alarmIntent);
    }



}
