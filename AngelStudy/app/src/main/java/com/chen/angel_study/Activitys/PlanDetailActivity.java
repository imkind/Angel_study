package com.chen.angel_study.Activitys;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chen.angel_study.MainActivity;
import com.chen.angel_study.Manager.ClockManager;
import com.chen.angel_study.Manager.PlanManager;
import com.chen.angel_study.Plans.Constants;
import com.chen.angel_study.Plans.Plan;
import com.chen.angel_study.R;
import com.chen.angel_study.receive.ClockReceive;
import com.chen.angel_study.service.ClockService;
import com.chen.angel_study.util.DateUtil;
import com.chen.angel_study.util.MyDialogUtil;
import com.chen.angel_study.util.StringUtil;
import com.chen.angel_study.util.Toasts;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class PlanDetailActivity extends BaseActivity {
    public static final String EXTRA_EDIT_EVENT = "extra.is.edit.event";
    public static final String EXTRA_ADD_EVENT = "extra.is.create.event";
    public static final String EXTRA_PLAN_DATA = "extra.event.data";
    //编辑操作
    private boolean isEditEvent;
    //添加操作
    private boolean isAddEvent;

    private PlanManager mPlanManager = PlanManager.getInstance();
    private ClockManager mClockManager = ClockManager.getInstance();
    //上次更新时间栏
    @BindView(R.id.layout_update_time)
    LinearLayout LayoutUpdateTime;
    //计划标题
    @BindView(R.id.Plan_title)
    EditText PlanTitle;
    //计划时间选择
    @BindView(R.id.remind_time_picker)
    EditText RemindTimePick;
    //输入计划内容
    @BindView(R.id.Plan_content)
    EditText PlanContent;
    //上次更新时间
    @BindView(R.id.last_edit_time)
    TextView UpdateTime;

    @BindView(R.id.back)
    ImageView Back;

    @BindView(R.id.confirm)
    TextView Confirm;

    @BindView(R.id.delete)
    ImageView Delete;
    @BindView(R.id.edit)
    ImageView Edit;
    @BindView(R.id.is_important)
    CheckBox IsImportant;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initView() {
        isEditEvent = getIntent().getBooleanExtra(EXTRA_EDIT_EVENT, false);
        isAddEvent = getIntent().getBooleanExtra(EXTRA_ADD_EVENT, false);
        //是否显示上方上次编辑时间
        LayoutUpdateTime.setVisibility(isAddEvent ? View.GONE : View.VISIBLE);
        //是否能够编辑标题
        setEditTextRead(PlanTitle, !isEditEvent && !isAddEvent);
        //是否能够编辑内容
        setEditTextRead(PlanContent, !isEditEvent && !isAddEvent);
        //设置提醒时间不能手动输入
        setEditTextRead(RemindTimePick, true);
        //设置提醒时间是否能够点击：弹出时间选择器
        RemindTimePick.setClickable(isEditEvent || isAddEvent);
        //设置右上角确定按钮是否可见
        Confirm.setVisibility(isEditEvent || isAddEvent ? View.VISIBLE : View.GONE);
        //设置右下角编辑按钮是否可见
        Edit.setVisibility(!isEditEvent && !isAddEvent ? View.VISIBLE : View.GONE);
        //设置左下角删除按钮是否可见
        Delete.setVisibility(!isAddEvent ? View.VISIBLE : View.GONE);
        //设置checkbox能不能点击
        IsImportant.setClickable(isEditEvent || isAddEvent);
    }


    @Override
    protected void initData() {
        if (!isAddEvent) {
            Plan event = getIntent().getParcelableExtra(EXTRA_PLAN_DATA);

            UpdateTime.setText(event.getmUpdatedTime());
            PlanTitle.setText(event.getmTitle());
            PlanContent.setText(event.getmContent());
            RemindTimePick.setText(event.getmRemindTime());
            IsImportant.setChecked(event.getmIsImportant() == Constants.PlanFlag.IMPORTANT);
        }
    }

    @OnClick(R.id.back)
    public void backImageClick(View view) {
        finish();
    }

    @OnClick(R.id.delete)
    public void deleteImageClick(View vew) {
        if (!isAddEvent) {
            MyDialogUtil.showDialog(this, "确定删除吗？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Plan plan = getIntent().getParcelableExtra(PlanDetailActivity.EXTRA_PLAN_DATA);
                    if (mPlanManager.removeEvent(plan.getmId())) {
                        Toasts.showToast("删除成功");
                        mClockManager.cancelAlarm(buildIntent(plan.getmId()));
                        mPlanManager.SetData();

                        Intent intent = new Intent();
                        intent.setClass(PlanDetailActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toasts.showToast("删除失败");
                    }
                }
            });
        }
    }


    @Override
    public int getContentView() {
        return R.layout.plan_in_activity;
    }

   //提醒时间选择
    @OnClick(R.id.remind_time_picker)
    public void datePickClick(View view) {
        if (isEditEvent || isAddEvent) {
            final Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(PlanDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String time = year + "-" + StringUtil.getLocalMonth(month) + "-" +
                                    StringUtil.getNumber(dayOfMonth) + " " + StringUtil.getNumber(hourOfDay) + ":" +
                                    StringUtil.getNumber(minute);
                            RemindTimePick.setText(time);
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                    timePickerDialog.show();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            dialog.show();
        }
    }

    @OnClick(R.id.edit)
    public void editImageClick(View view) {
        if (!isEditEvent) {
            Toasts.showToast("进入编辑模式");
            Edit.setVisibility(View.GONE);
            isEditEvent = true;
            //是否显示上方上次编辑时间
            LayoutUpdateTime.setVisibility(isAddEvent ? View.GONE : View.VISIBLE);
            //是否能够编辑标题
            setEditTextRead(PlanTitle, !isEditEvent && !isAddEvent);
            //是否能够编辑内容
            setEditTextRead(PlanContent, !isEditEvent && !isAddEvent);
            //设置提醒时间不能手动输入
            setEditTextRead(RemindTimePick, true);
            //设置提醒时间是否能够点击：弹出时间选择器
            RemindTimePick.setClickable(isEditEvent || isAddEvent);
            //设置右上角确定按钮是否可见
            Confirm.setVisibility(isEditEvent || isAddEvent ? View.VISIBLE : View.GONE);
            //设置右下角编辑按钮是否可见
            Edit.setVisibility(!isEditEvent && !isAddEvent ? View.VISIBLE : View.GONE);
            //设置左下角删除按钮是否可见
            Delete.setVisibility(!isAddEvent ? View.VISIBLE : View.GONE);
            //设置checkbox能不能点击
            IsImportant.setClickable(isEditEvent || isAddEvent);
        }
    }

    @OnClick(R.id.confirm)
    public void confirmClick(View view) {
        if (isEditEvent || isAddEvent) {
            Plan plan = buildPlan();
            if (!mPlanManager.checkAllPlan(plan)) {
                return;
            }
            if (mPlanManager.SavePlan(plan)) {
                if (isEditEvent) {
                    Toasts.showToast("更新计划成功");
                    Log.d("sssss","2222222222222222222222222222222");
                } else if (isAddEvent) {
                    Toasts.showToast("添加计划成功");
                    Log.d("sssss","5555555555555555555555555555555");
                    plan.setmId(mPlanManager.getLatestPlanId());
                }
                //添加闹钟
                if(plan.getmIsClocked() == 0) {
                    mClockManager.addAlarm(buildIntent(plan.getmId()), DateUtil.str2Date(plan.getmRemindTime()));//日期转化
                }
                mPlanManager.SetData();
                //返回主界面
                Intent intent = new Intent();
                intent.setClass(PlanDetailActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                if (isEditEvent) {
                    Toasts.showToast("更新失败");
                    Log.d("sssss","11111111111111111111111111111111111");

                } else if (isAddEvent) {
                    Log.d("sssss","33333333333333333333333333333");
                    Toasts.showToast("添加失败");

                }
            }
        }
    }

    private PendingIntent buildIntent(int id) {
        Intent intent = new Intent();
        intent.putExtra(ClockReceive.EXTRA_PLAN_ID, id);
        intent.setClass(this, ClockService.class);

        return PendingIntent.getService(this, 0x001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @OnClick(R.id.scroll_view)
    public void scrollViewClick(View view) {
        if (isAddEvent || isEditEvent) {
            //打开软键盘
            setEditTextRead(PlanContent, false);
        }
    }
//生成新计划Plan
    @NonNull
    private Plan buildPlan() {
        Plan plan = new Plan();
        if (isEditEvent) {
            plan.setmId(((Plan) getIntent().getParcelableExtra(EXTRA_PLAN_DATA)).getmId());
            plan.setmCreatedTime(((Plan) getIntent().getParcelableExtra(EXTRA_PLAN_DATA)).getmCreatedTime());
        }
        plan.setmRemindTime(RemindTimePick.getText().toString());
        plan.setmTitle(PlanTitle.getText().toString());
        plan.setmIsImportant(IsImportant.isChecked() ? Constants.PlanFlag.IMPORTANT : Constants.PlanFlag.NORMAL);
        plan.setmContent(PlanContent.getText().toString());
        plan.setmChose(Constants.PlanFlag.NORMAL);
        plan.setmUpdatedTime(DateUtil.dateToStr(new Date()));
        return plan;
    }

//设置文本可读，光标
    private void setEditTextRead(EditText editText, boolean readOnly) {
        editText.setFocusable(!readOnly);
        editText.setFocusableInTouchMode(!readOnly);
        editText.setCursorVisible(!readOnly);
        editText.setTextColor(ContextCompat.getColor(this,readOnly ? R.color.blue1: R.color.blue1));
    }
}