package com.chen.angel_study.Plans;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.chen.angel_study.Data.PlanContract.PlanEntry;
import com.chen.angel_study.Data.PlanProvider;
import com.chen.angel_study.util.DateUtil;

import java.util.Date;
import java.util.List;

public class PlanShow {

    private PlanProvider<Plan> mTemplate = new PlanProvider<>();
    private PlanCallback mCallback = new PlanCallback();
    private static PlanShow mPlan = new PlanShow();

    private PlanShow() {
    }

    public static PlanShow getInstance() {
        return mPlan;
    }

    public List<Plan> findAll() {
        String sql = "SELECT * FROM " + PlanEntry.TABLE_NAME + " ORDER BY " + PlanEntry.COLUMN_NAME_IMPORTANT + " DESC, " + PlanEntry.COLUMN_NAME_TIMECREAT + " DESC";
        return mTemplate.query(sql, mCallback);
    }

    public Plan findId(Integer id) {
        String sql = "SELECT * FROM " + PlanEntry.TABLE_NAME + " WHERE " + BaseColumns._ID + " = ?";
        return mTemplate.queryOne(sql, mCallback, Integer.toString(id));
    }

    public int remove(List<Integer> ids) {
        StringBuilder whereConditions = new StringBuilder(BaseColumns._ID + " IN(");
        for (Integer id : ids) {
            whereConditions.append(id).append(",");
        }
        whereConditions.deleteCharAt(whereConditions.length() - 1).append(")");
        return mTemplate.remove(PlanEntry.TABLE_NAME, whereConditions.toString());
    }

    public int create(Plan event) {
        return (int) mTemplate.create(PlanEntry.TABLE_NAME, GetValues(event, false));
    }

    public int update(Plan event) {
        return mTemplate.update(PlanEntry.TABLE_NAME, GetValues(event, true), BaseColumns._ID + "  = ?", Integer.toString(event.getmId()));
    }

    public int getLatestPlanId() {
        return mTemplate.getLatestId(PlanEntry.TABLE_NAME);
    }
//ContentValues进行sql的存储
    private ContentValues GetValues(Plan event, boolean isUpdate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlanEntry.COLUMN_NAME_TITLIE, event.getmTitle());
        contentValues.put(PlanEntry.COLUMN_NAME_PLAN, event.getmContent());
        if (!isUpdate) {
            contentValues.put(PlanEntry.COLUMN_NAME_TIMECREAT, DateUtil.dateToStr(new Date()));
        } else {
            contentValues.put(PlanEntry.COLUMN_NAME_TIMECREAT, event.getmCreatedTime());
        }
        contentValues.put(PlanEntry.COLUMN_NAME_CLOCK, event.getmIsClocked());
        contentValues.put(PlanEntry.COLUMN_NAME_TIMEUPDATE, DateUtil.dateToStr(new Date()));
        contentValues.put(PlanEntry.COLUMN_NAME_TIMEREMIND, event.getmRemindTime());
        contentValues.put(PlanEntry.COLUMN_NAME_IMPORTANT, event.getmIsImportant());
        return contentValues;
    }

}
