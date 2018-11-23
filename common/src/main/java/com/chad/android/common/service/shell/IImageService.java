package com.chad.android.common.service.shell;

import android.graphics.Bitmap;

import com.chad.android.common.service.IService;
import com.chad.android.common.widget.HMImageView;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public interface IImageService extends IService {

    void bindImage(HMImageView view, String url);

    void asyncDownload(String url, ImageListener listener);

    Bitmap syncDownload(String url);
}
