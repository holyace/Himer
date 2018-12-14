// Player.aidl
package com.himer.android.player;

import com.himer.android.player.Audio;
import com.himer.android.player.PlayerListener;
import com.himer.android.player.ErrorHandler;

// Declare any non-default types here with import statements

interface Player {

    void playIndex(int index);

    void play();

    void setAudioList(in List<Audio> audioList);

    void pause();

    void stop();

    void next();

    void previous();

    void setMode(int mode);

    void seekTo(int position);

    int getDuration();

    void setVolume(float left, float right);

    Audio getCurrentAudio();

    void registePlayerListener(PlayerListener listener);

    void registeErrorHandler(ErrorHandler errorHandler);
}
