package com.himer.android;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.chad.android.common.service.ServiceConfig;
import com.chad.android.common.service.ServiceManager;
import com.himer.android.init.AppInit;


/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public class HApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppInit.init(this);
    }
}
