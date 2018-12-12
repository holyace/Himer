package com.himer.android.downloader;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/10.
 */
public enum DownloadError {

    ERR_ILLEGALL_INFO("ERR_ILLEGALL_INFO", "ERR_ILLEGALL_INFO"),
    ERR_NET("ERR_NET", "ERR_NET"),
    ERR_IO_MKDIR("ERR_IO_MKDIR", "ERR_IO_MKDIR"),
    ERR_IO_CREATE_FILE("ERR_IO_CREATE_FILE", "ERR_IO_CREATE_FILE"),
    ERR_IO_RENAME_FILE("ERR_IO_RENAME_FILE", "ERR_IO_RENAME_FILE"),
    ERR_IO_DELETE_FILE("ERR_IO_DELETE_FILE", "ERR_IO_DELETE_FILE");


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
