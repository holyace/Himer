package com.himer.android.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/26.
 */
public abstract class BaseItemViewHolder {

    protected View mContent;

    public View getView() {
        return mContent;
    }

    public View makeView(Context context) {
        if (mContent == null) {
            mContent = createView(context, null);
        }
        return mContent;
    }

    public abstract View createView(Context context, ViewGroup parent);

    public abstract void bindData(Map<String, String> data);
}
