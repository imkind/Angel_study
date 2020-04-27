package com.chen.angel_study.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.os.IBinder;
import android.util.Log;


import com.chen.angel_study.Activitys.ClockActivity;
import com.chen.angel_study.Plans.Plan;
import com.chen.angel_study.Plans.PlanShow;

public class ClockService extends Service {
    private static final String TAG = "ClockService";
    public static final String EXTRA_PLAN_ID = "extra.plan.id";
    public static final String EXTRA_REMIND_TIME = "extra.remind.time";
    public static final String EXTRA_PLAN = "extra.plan";
    private PlanShow mPlanShow = PlanShow.getInstance();
    public ClockService() {
        Log.d(TAG, "ClockService: Constructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: onStartCommand");
        ToClockActivity(getApplicationContext(), intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void ToClockActivity(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClass(context, ClockActivity.class);
        i.putExtra(EXTRA_PLAN_ID, intent.getIntExtra(EXTRA_PLAN_ID, -1));
        Plan plan = mPlanShow.findId(intent.getIntExtra(EXTRA_PLAN_ID, -1));
        if (plan == null) {
            return;
        }
        i.putExtra(EXTRA_REMIND_TIME, intent.getStringExtra(EXTRA_REMIND_TIME));
        i.putExtra(EXTRA_PLAN, plan);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
