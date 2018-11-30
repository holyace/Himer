package com.himer.android.databinding;

import android.support.v4.util.LruCache;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class EventDispatcher {

    private Map<String, IEventHandler> mEventHandlers = new HashMap<>();

    public void handleEvent(View view, String type) {
        if (view == null || TextUtils.isEmpty(type)) {
            return;
        }
        IEventHandler handler = getHandler(type);
        Object tag = view.getTag();
        handler.handleEvent(null, view, null);
    }

    public void registeEventHandle(String type, IEventHandler handler) {
        if (TextUtils.isEmpty(type) || handler == null) {
            return;
        }
        mEventHandlers.put(type, handler);
    }

    private IEventHandler getHandler(String type) {
        return mEventHandlers.get(type);
    }
}
