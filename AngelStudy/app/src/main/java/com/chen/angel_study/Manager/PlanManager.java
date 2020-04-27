package com.chen.angel_study.Manager;

import android.util.Log;

import com.chen.angel_study.Plans.Plan;
import com.chen.angel_study.Plans.PlanShow;
import com.chen.angel_study.util.DateUtil;
import com.chen.angel_study.util.StringUtil;
import com.chen.angel_study.util.Toasts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PlanManager {
    public static final String TAG = "PlanManager";
    private static PlanManager mPlanManager = new PlanManager();
    private PlanShow mPlan = PlanShow.getInstance();
    //保存一份数据

    private List<Plan> plans = new ArrayList<>();

    private PlanManager(){
    }

    public static PlanManager getInstance() {
        return mPlanManager;
    }

    public List<Plan> findAll() {
        plans =  mPlan.findAll();
        return plans;
    }

    public void SetData() {
        plans = mPlan.findAll();
    }

    public List<Plan> getPlans() {
        return plans;
    }


    public boolean removeEvent(Integer id) {
        return mPlan.remove(Collections.singletonList(id)) != 0;
    }

    public boolean SavePlan(Plan event) {
        try {
            if (event.getmId() != null) {
                mPlan.update(event);
            } else {
                mPlan.create(event);
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Save: ", e);
            return false;
        }
    }

    public int getLatestPlanId() {
        return mPlan.getLatestPlanId();
    }

    //提醒完成全部Plan编辑
    public boolean checkAllPlan(Plan event) {
        if (event == null) {
            return false;
        }
        if (StringUtil.isBlank(event.getmTitle())) {
            Toasts.showToast("事件不能为空");
            return false;
        }
        if (StringUtil.isBlank(event.getmContent())) {
            Toasts.showToast("内容不能为空");
            return false;
        }
        if (StringUtil.isBlank(event.getmRemindTime())) {
            Toasts.showToast("时间不能为空");
            return false;
        }
        if (DateUtil.str2Date(event.getmRemindTime()) == null) {
            Toasts.showToast("提醒时间格式错误");
            return false;
        }
        if (new Date().getTime() > DateUtil.str2Date(event.getmRemindTime()).getTime()) {
            Toasts.showToast("提醒时间无效");
            return false;
        }
        return true;
    }
}