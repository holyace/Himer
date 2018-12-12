package com.himer.android.downloader;

public interface IDownloadListener {

    void onDownloadStart(IDownloadTask task);
    void onDownloadPause(IDownloadTask task);
    void onProgressUpdate(IDownloadTask task, float percent);
    void onDownloadSucess(IDownloadTask task);
    void onDownloadFail(IDownloadTask task, String errorCode, String errorMessage);
    void onTaskCancel(IDownloadTask task);
    /**
     * we will ask user if there is need to redownload the task,
     * when the task already downloaded.
     * @param task downloaded task
     * @return true we will redownload, otherwise false
     */
    boolean onRedownload(IDownloadTask task);
}

