package com.himer.android.util;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class UIUtils {

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean isAlive(Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            return ((Activity) context).isFinishing();
        }
        return true;
    }
}
