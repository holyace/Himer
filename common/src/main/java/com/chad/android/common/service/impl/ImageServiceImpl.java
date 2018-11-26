package com.chad.android.common.service.impl;

import android.app.Application;
import android.graphics.Bitmap;

import com.chad.android.common.service.shell.IImageService;
import com.chad.android.common.service.shell.ImageListener;
import com.chad.android.common.widget.HMImageView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public class ImageServiceImpl implements IImageService {

    @Override
    public void bindImage(HMImageView view, String url) {
        if (view instanceof SimpleDraweeView) {
            view.setImageURI(url);
            return;
        }
    }

    @Override
    public void asyncDownload(String url, ImageListener listener) {

    }

    @Override
    public Bitmap syncDownload(String url) {
        return null;
    }

    @Override
    public void init(Application app, Map<String, Object> params) {
        Fresco.initialize(app);
    }

    @Override
    public void destory() {

    }
}
