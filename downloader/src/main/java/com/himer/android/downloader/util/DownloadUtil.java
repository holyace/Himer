package com.himer.android.downloader.util;

import com.himer.android.downloader.IDownloadTask;

import java.io.File;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/12.
 */
public class DownloadUtil {

    public static File getDownloadFile(IDownloadTask task) {
        File file = new File(task.getSavePath(), task.getFileName());

        return file;
    }

    public static String getDownloadFilePath(IDownloadTask task) {
        return task.getSavePath() + File.separator + task.getFileName();
    }
}
