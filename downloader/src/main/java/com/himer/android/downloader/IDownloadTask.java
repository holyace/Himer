package com.himer.android.downloader;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/7.
 */
public interface IDownloadTask {

    String getDownloadUrl();

    String getTitle();

    String getFileName();

    String getSavePath();

    DownloadState getDownloadState();

    void setDownloadState(DownloadState state);
}
