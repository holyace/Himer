package com.himer.android.util;

import android.util.Log;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/23.
 */
public class HLog {

    private static boolean sEnable = true;

    private static boolean isEnable() {
        return sEnable;
    }

    private static String getLog(Object... content) {
        if (content.length == 0) {
            return "empty log";
        }
        StringBuilder sb = new StringBuilder();
        for (Object s : content) {
            sb.append(String.valueOf(s)).append("|");
        }
        return sb.toString();
    }

    private static String getLog(Exception e) {
        return e.getMessage();
    }

    public static void w(String tag, Object... content) {
        if (isEnable()) {
            Log.w(tag, getLog(content));
        }
    }

    public static void i(String tag, Object... content) {
        if (isEnable()) {
            Log.i(tag, getLog(content));
        }
    }

    public static void d(String tag, Object... content) {
        if (isEnable()) {
            Log.d(tag, getLog(content));
        }
    }

    public static void e(String tag, Object... content) {
        if (isEnable()) {
            Log.e(tag, getLog(content));
        }
    }

    public static void exception(String tag, Exception e) {
        if (isEnable()) {
            Log.e(tag, getLog(e));
        }
    }
}
