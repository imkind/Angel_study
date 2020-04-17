package com.chen.angel_study.Data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.chen.angel_study.Appication;
import com.chen.angel_study.Data.PlanContract.PlanEntry;

public class PlanDpHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sleter.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + PlanEntry.TABLE_NAME + "( "
                    + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PlanEntry.COLUMN_NAME_TITLIE + " text, "
                    + PlanEntry.COLUMN_NAME_PLAN + " text, "
                    + PlanEntry.COLUMN_NAME_TIMECREAT + " datetime, "
                    + PlanEntry.COLUMN_NAME_TIMEUPDATE + " datetime, "
                    + PlanEntry.COLUMN_NAME_TIMEREMIND + " datetime, "
                    + PlanEntry.COLUMN_NAME_IMPORTANT + " INTEGER, "
                    + PlanEntry.COLUMN_NAME_CLOCK + " INTEGER"
                    + ")";

    public PlanDpHelper (){
        super(Appication.getContext(),DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);

        String sql = "INSERT INTO " + PlanEntry.TABLE_NAME + " VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)";
        db.beginTransaction();
        db.execSQL(sql, new Object[]{"简单的示例",
                "简易的记事本app",
                "2020-4-4 17:28:23",
                "2020-4-4 17:28",
                "2020-4-4 17:28",
                0, 0}
                );
        db.setTransactionSuccessful();
        db.endTransaction();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
