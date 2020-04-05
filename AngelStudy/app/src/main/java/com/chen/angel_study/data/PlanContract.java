package com.chen.angel_study.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class PlanContract {

    public static final String CONTENT_AUTHORITY = "com.chen.android.plan";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PLANS = "plan";


    public static abstract class PlanEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PLANS);

        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "plan";
        public static final String COLUMN_NAME_TITLIE= "title";
        public static final String COLUMN_NAME_PLAN = "things";
        public static final String COLUMN_NAME_TIMECREAT = "creat_time";
        public static final String COLUMN_NAME_TIMEUPDATE = "update_time";
        public static final String COLUMN_NAME_TIMEREMIND = "remind_time";
        public static final String COLUMN_NAME_IMPORTANT = "important";
        public static final String COLUMN_NAME_CLOCK = "is_clocked";


        public static final int THINGS_OPEN= 1;
        public static final int THINGS_CLOSE = 0;


    }
}
