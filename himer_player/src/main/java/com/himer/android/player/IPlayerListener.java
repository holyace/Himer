package com.himer.android.player;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/30.
 */
public interface IPlayerListener {

    void onPlay();

    void onPause();

    void onStop();

    void onComplete();

    void onPlayChange(int index);

    void onPositionChange(int position, int duration);

    void onBufferingChange(int bufferPosition);

    void onError(int errorCode, String errorMessage);
}
