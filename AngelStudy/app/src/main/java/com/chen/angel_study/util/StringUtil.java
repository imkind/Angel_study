package com.chen.angel_study.util;

public class StringUtil {
    public static String getNumber(int number) {
        return number < 10 ? "0" + number : Integer.toString(number);
    }

    public static String getLocalMonth(int month) {
        return getNumber(month + 1);
    }

    public static boolean isBlank(String src) {
        return src == null || src.trim().length() == 0;
    }
}
