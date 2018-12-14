package com.himer.android.player;

import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public interface IPlayer {

    void playIndex(int index);

    void play();

    void setAudioList(List<Audio> audioList);

    void pause();

    void stop();

    void next();

    void previous();

    void setMode(int mode);

    void seekTo(int position);

    int getDuration();

    Audio getCurrentAudio();

    void addPlayerListener(IPlayerListener listener);
}
