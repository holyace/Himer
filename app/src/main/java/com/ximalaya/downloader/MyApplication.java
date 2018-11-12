package com.ximalaya.downloader;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ximalaya.downloader.download.DownloadManager;

/**
 * Created by chad on 17/3/28.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        DownloadManager.getInstance().setAppDir(getExternalFilesDir(null).getAbsolutePath());
    }

}
