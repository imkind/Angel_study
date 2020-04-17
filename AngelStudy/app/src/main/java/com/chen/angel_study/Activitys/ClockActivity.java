package com.chen.angel_study.Activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chen.angel_study.Manager.PlanManager;
import com.chen.angel_study.Plans.Plan;
import com.chen.angel_study.R;
import com.chen.angel_study.service.ClockService;
import com.chen.angel_study.util.MyDialogUtil;

public class ClockActivity extends BaseActivity {
    private static final String TAG = "ClockActivity";
    public static final String EXTRA_CLOCK_EVENT = "clock.event";
    //闹铃
    private MediaPlayer mediaPlayer;
    //震动
    private Vibrator mVibrator;
    private Plan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void setListener() {
    }
    @Override
    protected void initView() {
    }
    @Override
    public int getContentView() {
        return R.layout.activity_clock;
    }


    private void clock() {
        mediaPlayer.start();
        long[] pattern = new long[]{1500, 1000};
        mVibrator.vibrate(pattern, 0);

        View rootview = LayoutInflater.from(this).inflate(R.layout.dialog_alarm_layout, null);
        TextView textView = rootview.findViewById(R.id.tv_event);
        textView.setText(String.format(getString(R.string.clock_event_msg_template), plan.getmTitle()));
        Button btn = rootview.findViewById(R.id.btn_confirm);

        final AlertDialog alertDialog = MyDialogUtil.showDialog(this, rootview);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mVibrator.cancel();
                alertDialog.dismiss();
                finish();
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        clock();
    }

    @Override
    protected void initData() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clock);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Intent intent = getIntent();
        plan = getIntent().getParcelableExtra(ClockService.EXTRA_PLAN);
        if (plan == null) {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        clock();
    }
}