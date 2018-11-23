package com.himer.android.appadapter.service;

import android.graphics.Bitmap;

import com.chad.android.common.service.shell.IImageService;
import com.chad.android.common.service.shell.ImageListener;
import com.himer.android.common_ui.widget.HMImageView;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public class ImageServiceImpl implements IImageService {

    public ImageServiceImpl() {

    }

    @Override
    public void bindImage(HMImageView view, String url) {

    }

    @Override
    public void asyncDownload(String url, ImageListener listener) {

    }

    @Override
    public Bitmap syncDownload(String url) {
        return null;
    }
}
