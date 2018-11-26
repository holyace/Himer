package com.himer.android.fragment;

import android.content.Context;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/26.
 */
public class ViewFactory {

    public BaseItemViewHolder makeView(Context context, int viewType) {
        BaseItemViewHolder holder = new SoundItemViewHolder();
        holder.makeView(context);
        return holder;
    }
}
