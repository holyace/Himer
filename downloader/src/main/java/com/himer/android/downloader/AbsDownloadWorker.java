package com.himer.android.downloader;

import android.text.TextUtils;

import org.w3c.dom.Text;

import static com.himer.android.downloader.DownloadError.ILEGALL_INFO;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/7.
 */
public abstract class AbsDownloadWorker implements Runnable {

    protected IDownloadListener mDownloadListener;
    protected IDownloadTask mDownloadTask;

    public AbsDownloadWorker(IDownloadListener listener) {
        mDownloadListener = listener;
    }

    public void setDownloadTask(IDownloadTask task) {
        mDownloadTask = task;
    }

    @Override
    public void run() {

        if (!checkDownloadTask()) {
            mDownloadListener.onDownloadFail(mDownloadTask,
                    ILEGALL_INFO.getErrorCode(),
                    ILEGALL_INFO.getErrorMessage());
            return;
        }

        if (!checkDownloaded()) {
            return;
        }


    }

    private boolean checkDownloadTask() {
        return mDownloadTask != null &&
                !TextUtils.isEmpty(mDownloadTask.getDownloadUrl()) &&
                !TextUtils.isEmpty(mDownloadTask.getSavePath());
    }


    protected boolean checkDownloaded() {
        return mDownloadListener.onRedownload(mDownloadTask);
    }

    protected abstract String getDefaultPath();
}
