package com.himer.android.common.service.shell;

import android.graphics.Bitmap;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public interface ImageListener {

    void onSuccess(Bitmap bitmap);

    void onFaile(String errorCode, String errorMessage);
}
