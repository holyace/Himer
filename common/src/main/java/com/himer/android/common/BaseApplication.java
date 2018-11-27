package com.himer.android.common;

import android.app.Application;

import com.himer.android.common.util.HLog;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/27.
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        HLog.w(TAG, "onCreate");
    }
}
