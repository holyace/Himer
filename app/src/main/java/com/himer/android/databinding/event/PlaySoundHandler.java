package com.himer.android.databinding.event;

import android.view.View;

import com.himer.android.databinding.BindingListAdapter;
import com.himer.android.databinding.IEventHandler;
import com.himer.android.modle.SearchSound;
import com.himer.android.player.Audio;
import com.himer.android.player.PlayerManager;
import com.himer.android.player.util.CollectionUtil;
import com.himer.android.util.AudioConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class PlaySoundHandler implements
        IEventHandler<BindingListAdapter<SearchSound>> {

    @Override
    public void handleEvent(BindingListAdapter<SearchSound> context,
                            View view, Map<String, Object> param) {
        SearchSound sound = (SearchSound) param.get("sound");
//        try {
//            String audio = sound.getPlay_path_aac_v224();
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.parse(audio), "audio/*");
//            view.getContext().startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        List<SearchSound> list = context.getData();
        if (CollectionUtil.isEmpty(list) || sound == null) {
            return;
        }

        int index = list.indexOf(sound);
        List<Audio> playList = AudioConverter.convert(list);
        PlayerManager player = PlayerManager.getInstance(view.getContext());
        player.setAudioList(playList);
        player.playIndex(index);
    }
}
