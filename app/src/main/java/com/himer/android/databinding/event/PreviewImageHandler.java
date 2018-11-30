package com.himer.android.databinding.event;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.himer.android.databinding.BindingListAdapter;
import com.himer.android.databinding.IEventHandler;
import com.himer.android.modle.SearchSound;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class PreviewImageHandler implements
        IEventHandler<BindingListAdapter<SearchSound>> {

    @Override
    public void handleEvent(BindingListAdapter<SearchSound> context,
                            View view, Map<String, Object> param) {

        SearchSound sound = (SearchSound) param.get("sound");
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(sound.getCover_path()), "image/*");
            view.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
