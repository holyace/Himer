package com.himer.android.databinding.event;

import android.view.View;

import com.himer.android.databinding.BindingListAdapter;
import com.himer.android.databinding.IEventHandler;
import com.himer.android.download.DownloadManager;
import com.himer.android.modle.SearchSound;
import com.himer.android.util.HMToast;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class DownloadSoundHandler implements
        IEventHandler<BindingListAdapter<SearchSound>> {

    @Override
    public void handleEvent(BindingListAdapter<SearchSound> context,
                            View view, Map<String, Object> param) {

        SearchSound sound = (SearchSound) param.get("sound");
        HMToast.show(view.getContext(), String.format("开始下载\"%s\"", sound.getTitle()));
        DownloadManager.getInstance().downloadSound(sound, false);
    }
}
