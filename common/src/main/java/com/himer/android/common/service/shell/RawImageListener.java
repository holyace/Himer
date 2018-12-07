package com.himer.android.common.service.shell;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public interface RawImageListener {

    void onSuccess(byte[] bitmap);

    void onFaile(String errorCode, String errorMessage);
}
