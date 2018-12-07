package com.himer.android.common.service.shell;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public abstract class ImageListener implements RawImageListener {

    public abstract void onSuccess(Bitmap bitmap);

    public abstract void onFaile(String errorCode, String errorMessage);

    @Override
    public void onSuccess(byte[] data) {
        if (data != null && data.length > 0) {
            onSuccess(BitmapFactory.decodeByteArray(data, 0, data.length));
        } else {
            onFaile("UNKNOWN", "Empty Bitmap Byte Data");
        }
    }
}
