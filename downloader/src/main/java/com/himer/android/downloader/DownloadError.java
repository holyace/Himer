package com.himer.android.downloader;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/10.
 */
public enum DownloadError {

    ILEGALL_INFO("", ""),
    NETERROR("", ""),
    IOERROR("", "");


    private String errorCode;
    private String errorMessage;

    DownloadError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
