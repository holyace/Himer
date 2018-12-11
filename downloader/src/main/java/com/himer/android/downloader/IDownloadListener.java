package com.himer.android.downloader;

public interface IDownloadListener {

    void onDownloadStart(IDownloadTask task);
    void onDownloadPause(IDownloadTask task);
    void onProgressUpdate(IDownloadTask task, int percent);
    void onDownloadSucess(IDownloadTask task);
    void onDownloadFail(IDownloadTask task, String errorCode, String errorMessage);
    boolean onRedownload(IDownloadTask task);
}

