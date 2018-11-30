// IPlayerListener.aidl
package com.himer.android.player;

// Declare any non-default types here with import statements

interface PlayerListener {

    void onPlay(int index);

    void onPause();

    void onStop();

    void onComplete();

    void onPositionChange(int position, int duration);

    void onBufferingChange(int bufferPosition);

    void onError(int errorCode, String errorMessage);

}
