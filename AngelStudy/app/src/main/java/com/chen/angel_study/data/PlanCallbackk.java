package com.chen.angel_study.data;

import android.database.Cursor;

public interface PlanCallbackk<T> {

    T cursorToInstance(Cursor cursor);
}
