// IPlayerListener.aidl
package com.himer.android.player;

// Declare any non-default types here with import statements

interface PlayerListener {

    void onPlay();

    void onPause();

    void onStop();

    void onComplete();

    void onPlayChange(int index);

    void onPositionChange(int position, int duration);

    void onBufferingChange(int percent);

    void onError(int errorCode, String errorMessage);

}
