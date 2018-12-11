package com.himer.android.downloader;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/7.
 */
public interface IDownloader {

    void download(IDownloadTask task);

    void pauseTask(IDownloadTask task);

    void restartTask(IDownloadTask task);

    void pauseAll();

    void restartAll();

    void delete(IDownloadTask task);

    void deleteAll();

    void registeDownloadListener(IDownloadListener listener);

    void unregisteDownloadListener(IDownloadListener listener);
}
