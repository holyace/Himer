package com.himer.android.util;

import com.himer.android.modle.SearchSound;
import com.himer.android.player.Audio;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/5.
 */
public class AudioConverter {

    public static Audio convert(SearchSound sound) {
        Audio audio = new Audio();
        audio.setTitle(sound.getTitle());
        audio.setPath(sound.getPlay_path_aac_v224());
        audio.setCoverPath(sound.getCover_path());
        return audio;
    }
}
