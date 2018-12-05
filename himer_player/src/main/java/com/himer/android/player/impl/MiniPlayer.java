package com.himer.android.player.impl;

import android.media.MediaPlayer;

import com.himer.android.player.constants.PlayerState;

import java.io.IOException;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class MiniPlayer extends MediaPlayer implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener{

    private PlayerState mState;

    private OnPreparedListener mOnPreparedListener;
    private OnErrorListener mOnErrorListener;
    private OnCompletionListener mOnCompletionListener;

    private boolean mNeedPlay;

    public MiniPlayer() {
        mState = PlayerState.IDLE;
        mNeedPlay = false;
        super.setOnPreparedListener(this);
        super.setOnErrorListener(this);
        super.setOnCompletionListener(this);
    }

    @Override
    public void setOnPreparedListener(OnPreparedListener listener) {
        mOnPreparedListener = listener;
    }

    @Override
    public void setOnErrorListener(OnErrorListener listener) {
        mOnErrorListener = listener;
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        mOnCompletionListener = listener;
    }

    public PlayerState getPlayerState() {
        return mState;
    }

    @Override
    public void reset() {
        super.reset();
        mState = PlayerState.IDLE;
        mNeedPlay = false;
    }

    @Override
    public void release() {
        super.release();
        mState = PlayerState.RELEASED;
        mNeedPlay = false;
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        super.setDataSource(path);
        mState = PlayerState.INITIALIZED;
        mNeedPlay = false;
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        super.prepareAsync();
        mState = PlayerState.PREPARING;
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
        mState = PlayerState.STARTED;
        mNeedPlay = false;
    }

    public void asyncStart() throws IllegalStateException {
        mNeedPlay = true;
        prepareAsync();
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        mState = PlayerState.STOPPED;
        mNeedPlay = false;
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        mState = PlayerState.PAUSED;
        mNeedPlay = false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mState = PlayerState.PREPARED;
        if (mOnPreparedListener != null) {
            mOnPreparedListener.onPrepared(mp);
        }
        if (mNeedPlay) {
            start();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mState = PlayerState.ERROR;
        mNeedPlay = false;
        if (mOnErrorListener != null) {
            return mOnErrorListener.onError(mp, what, extra);
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mState = PlayerState.COMPLETED;
        mNeedPlay = false;
        if (mOnCompletionListener != null) {
            mOnCompletionListener.onCompletion(mp);
        }
    }
}
