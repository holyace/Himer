package com.himer.android;

import android.app.Application;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public class Global {
    private static Application sAppCtx;

    public static void setApplication(Application app) {
        sAppCtx = app;
    }

    public static Application getApplication() {
        return sAppCtx;
    }
}
