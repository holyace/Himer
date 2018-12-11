package com.himer.android.downloader;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/7.
 */
public class DownloadWorker implements Runnable {

    private IDownloadTask mDownloadTask;
    private IDownloadListener mDownloadListener;

    DownloadWorker(IDownloadListener listener) {
        mDownloadListener = listener;
    }

    public void setDownloadTask(IDownloadTask task) {
        mDownloadTask = task;
    }

    @Override
    public void run() {
        if (mDownloadTask == null) {
            return;
        }
        Req
    }


}
