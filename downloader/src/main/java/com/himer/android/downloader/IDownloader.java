package com.himer.android.downloader;

import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/7.
 */
public interface IDownloader {

    void download(IDownloadTask task);

    void download(List<IDownloadTask> list);

    void pauseTask(IDownloadTask task);

    void restartTask(IDownloadTask task);

    void pauseAll();

    void restartAll();

    void deleteTask(IDownloadTask task);

    void deleteAllTask();

    List<IDownloadTask> getDownloadedTask();

    List<IDownloadTask> getTaskQueue();

    void registeDownloadListener(IDownloadListener listener);

    void unregisteDownloadListener(IDownloadListener listener);
}
