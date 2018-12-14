package com.himer.android;

import android.app.Application;

import com.himer.android.common.util.HLog;
import com.himer.android.init.AppInit;
import com.himer.android.util.AppUtil;


/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public class HApplication extends Application {

    private static final String TAG = HApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        HLog.w(TAG, "onCreate", AppUtil.currentProcessName());

        AppInit.init(this);
    }
}
