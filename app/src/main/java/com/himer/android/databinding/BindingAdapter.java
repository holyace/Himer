package com.himer.android.databinding;

import android.text.TextUtils;
import android.view.View;

import com.himer.android.common.util.HLog;
import com.himer.android.databinding.event.PreviewImageHandler;
import com.himer.android.modle.SearchSound;

import java.util.HashMap;
import java.util.Map;


/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/26.
 */
public class BindingAdapter<T> implements EventConstants {

    private T mContext;

    public BindingAdapter(T context) {
        mContext = context;
    }

    private static final String TAG = BindingAdapter.class.getSimpleName();

    public void onClick(View view, String type) {
        HLog.w(TAG, "onClick ", type);
        if (view == null || TextUtils.isEmpty(type)) {
            return;
        }
        final Object tag = view.getTag();
        if (!(tag instanceof SearchSound)) {
            return;
        }
        IEventHandler handler = EventFactory.getEventHandler(type);
        if (handler != null) {
            handler.handleEvent(mContext, view,
                    new HashMap<String, Object>() {{put("sound", tag);}});
        }
    }

    private Map<String, String> transferField(SearchSound sound) {
        Map<String, String> field = new HashMap<>();
        field.put("cover_path", sound.cover_path);
        field.put("play_path_aac_v224", sound.play_path_aac_v224);
        field.put("download_path", sound.download_path);
        return field;
    }
}
