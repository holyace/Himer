package com.himer.android.databinding.event;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.himer.android.databinding.BindingListAdapter;
import com.himer.android.databinding.IEventHandler;
import com.himer.android.modle.SearchSound;
import com.himer.android.player.Audio;
import com.himer.android.player.PlayerManager;

import java.util.ArrayList;
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

        final Audio audio = new Audio();
        audio.setPath(sound.getPlay_path_aac_v224());
        PlayerManager player = PlayerManager.getInstance(view.getContext());
        player.setAudioList(new ArrayList<Audio>(){{add(audio);}});
        player.playIndex(0);
    }
}
