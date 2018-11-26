package com.himer.android.databinding;

import android.view.View;

import com.himer.android.util.HLog;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/26.
 */
public class BindingAdapter {

    private static final String TAG = BindingAdapter.class.getSimpleName();

    public void onClick(View view) {
        HLog.w(TAG, "onClick");
    }

    public int getMode() {
        return 0;
    }
}
