package com.himer.android.init;

import android.app.Application;

import com.chad.android.common.service.ServiceConfig;
import com.chad.android.common.service.ServiceManager;
import com.chad.android.common.service.impl.ImageServiceImpl;
import com.chad.android.common.service.shell.IDBService;
import com.chad.android.common.service.shell.IImageService;
import com.himer.android.Global;
import com.himer.android.db.DBServiceImpl;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public class AppInit {

    public static void init(Application app) {

        Global.setApplication(app);

        ServiceConfig config = new ServiceConfig();
        ServiceManager.init(config);

        ImageServiceImpl is = new ImageServiceImpl();
        is.init(app, null);
        ServiceManager.setService(IImageService.class, is);

        DBServiceImpl dbService = new DBServiceImpl();
        dbService.init(app, null);
        ServiceManager.setService(IDBService.class, dbService);
    }
}
