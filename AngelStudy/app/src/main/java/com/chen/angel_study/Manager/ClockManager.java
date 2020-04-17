package com.chen.angel_study.Manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import com.chen.angel_study.Appication;

import java.util.Date;

public class ClockManager {
    private static ClockManager instance = new ClockManager();

    private ClockManager() {
    }

    public static ClockManager getInstance() {
        return instance;
    }

     //获取系统闹钟服务
    private static AlarmManager getAlarmManager() {
        return (AlarmManager) Appication.getContext().getSystemService(Context.ALARM_SERVICE);
    }

     //取消闹钟
    public void cancelAlarm(PendingIntent pendingIntent) {
        getAlarmManager().cancel(pendingIntent);
    }

     //添加闹钟
    public void addAlarm(PendingIntent pendingIntent, Date performTime) {
        cancelAlarm(pendingIntent);
        getAlarmManager().set(AlarmManager.RTC_WAKEUP, performTime.getTime(), pendingIntent);
    }
}