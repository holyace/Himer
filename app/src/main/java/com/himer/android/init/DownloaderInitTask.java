package com.himer.android.init;

import com.himer.android.Global;
import com.himer.android.download.DownloadManager;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public class DownloaderInitTask extends BaseInitTask {
    @Override
    void execute() {
        DownloadManager.getInstance().setAppDir(
                Global.getApplication().getExternalFilesDir(null).
                        getAbsolutePath());
    }
}
