package com.himer.android.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public class HMImageView extends SimpleDraweeView {
    public HMImageView(Context context) {
        super(context);
    }

    public HMImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HMImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }
}
