package com.himer.android.util;

import android.text.TextUtils;

import com.himer.android.modle.SearchSound;
import com.himer.android.player.Audio;
import com.himer.android.player.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/5.
 */
public class AudioConverter {

    public static Audio convert(SearchSound sound) {
        Audio audio = new Audio();
        audio.setTitle(sound.getTitle());
        String localPath = sound.getDownload_path();
        if (!TextUtils.isEmpty(localPath) && !"todo".equals(localPath)) {
            audio.setPath(localPath);
        }
        else {
            audio.setPath(sound.getPlay_path_aac_v224());
        }
        audio.setCoverPath(sound.getCover_path());
        return audio;
    }

    public static List<Audio> convert(List<SearchSound> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        List<Audio> result = new ArrayList<>(list.size());
        for (SearchSound sound : list) {
            Audio audio = convert(sound);
            if (audio != null) {
                result.add(audio);
            }
        }
        return result;
    }
}
