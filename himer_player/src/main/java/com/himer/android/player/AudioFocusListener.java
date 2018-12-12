package com.himer.android.player;

import android.media.AudioManager;

import com.himer.android.player.constants.PlayerState;
import com.himer.android.player.impl.PlayerStub;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/11.
 */
public class AudioFocusListener implements AudioManager.OnAudioFocusChangeListener {

    private PlayerStub mPlayer;

    private boolean mShouldPlay;
    private boolean mShouldVolumUp;

    public AudioFocusListener(PlayerStub player) {
        mPlayer = player;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if (mShouldPlay) {
                    mPlayer.play();
                }
                if (mShouldVolumUp) {
                    mPlayer.setVolume(1f, 1f);
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (PlayerState.STARTED == mPlayer.getPlayerState()) {
                    mPlayer.pause();
                    mShouldPlay = true;
                }
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // 暂时失去 audio focus，但是允许持续播放音频(以很小的声音)，不需要完全停止播放。
                if (PlayerState.STARTED == mPlayer.getPlayerState()) {
                    mPlayer.setVolume(0.5f, 0.5f);
                    mShouldVolumUp = true;
                }
                break;
        }
    }
}
