package com.chen.angel_study.Plans;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.chen.angel_study.Data.PlanCallbackk;
import com.chen.angel_study.Data.PlanContract.PlanEntry;

public class PlanCallback implements PlanCallbackk<Plan> {
    @Override
    public Plan cursorToInstance(Cursor cursor) {
        Plan plan = new Plan();
        plan.setmId(cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)));
        plan.setmTitle(cursor.getString(cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_TITLIE)));
        plan.setmContent(cursor.getString(cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_PLAN)));
        plan.setmCreatedTime(cursor.getString(cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_TIMECREAT)));
        plan.setmUpdatedTime(cursor.getString(cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_TIMEUPDATE)));
        plan.setmRemindTime(cursor.getString(cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_TIMEREMIND)));
        plan.setmIsImportant(cursor.getInt(cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_IMPORTANT)));
        plan.setmIsClocked(cursor.getInt(cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_CLOCK)));
        plan.setmChose(cursor.getInt(cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_CHECK)));
        return  plan;
    }
}