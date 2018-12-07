package com.himer.android.common.service.shell;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.himer.android.common.service.IService;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public interface IImageService extends IService {

    void bindImage(ImageView view, String url);

    void asyncDownload(String url, ImageListener listener);

    Bitmap syncDownload(String url);
}
