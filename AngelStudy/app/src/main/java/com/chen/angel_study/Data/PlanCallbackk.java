package com.chen.angel_study.Data;

import android.database.Cursor;

public interface PlanCallbackk<T> {
    T cursorToInstance(Cursor cursor);
}
