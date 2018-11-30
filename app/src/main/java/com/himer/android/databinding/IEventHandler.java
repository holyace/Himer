package com.himer.android.databinding;

import android.view.View;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public interface IEventHandler<T> {

    void handleEvent(T context, View view, Map<String, Object> param);
}
