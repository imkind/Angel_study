package com.chen.angel_study.util;

import android.widget.Toast;

import com.chen.angel_study.Appication;

public class Toasts {
    public static void showToast(String msg) {
        Toast.makeText(Appication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
