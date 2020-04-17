package com.chen.angel_study.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.chen.angel_study.Activitys.ClockActivity;
import com.chen.angel_study.Plans.Plan;
import com.chen.angel_study.Plans.PlanShow;

public class ClockReceive extends BroadcastReceiver {
    private static final String TAG = "ClockService";
    public static final String EXTRA_PLAN_ID = "extra.plan.id";
    public static final String EXTRA_REMIND_TIME = "extra.remind.time";
    public static final String EXTRA_PLAN = "extra.plan";
    private PlanShow mPlanShow = PlanShow.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
        ToClockActivity(context, intent);
    }

    private void ToClockActivity(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClass(context, ClockActivity.class);
        i.putExtra(EXTRA_PLAN_ID, intent.getIntExtra(EXTRA_PLAN_ID, -1));
        Plan event = mPlanShow.findId(intent.getIntExtra(EXTRA_PLAN_ID, -1));
        if (event == null) {
            return;
        }
        i.putExtra(EXTRA_REMIND_TIME, intent.getStringExtra(EXTRA_REMIND_TIME));
        i.putExtra(EXTRA_PLAN, event);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public ClockReceive() {
        super();
        Log.d(TAG, "ClockReceiver: Constructor");
    }
}