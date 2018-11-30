package com.himer.android.player;

import android.os.RemoteException;

import com.himer.android.player.util.CollectionUtil;

import java.io.IOException;
import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/30.
 */
public class PlayerStub extends Player.Stub {

    private MiniPlayer mPlayer;
    private List<Audio> mAudioList;
    private int mCurrentIndex = -1;
    private Audio mCurrentAudio;

    private PlayerListener mPlayerListener;

    public PlayerStub() {
        mPlayer = new MiniPlayer();
    }

    @Override
    public void playIndex(int index) {
        if (!CollectionUtil.isIndexInRange(mAudioList, index)) {
            return;
        }
        mCurrentIndex = index;
        Audio audio = mAudioList.get(index);
        mCurrentAudio = audio;
        try {
            stop();
            mPlayer.reset();
            mPlayer.setDataSource(audio.getPath());
            mPlayer.asyncStart();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setAudioList(List<Audio> audioList) throws RemoteException {
        mAudioList = audioList;
    }

    @Override
    public void pause() {
        if (PlayerState.STARTED == mPlayer.getPlayerState()) {
            mPlayer.pause();
        }
    }

    @Override
    public void play() {
        PlayerState state = mPlayer.getPlayerState();
        if (PlayerState.PREPARED == state ||
                PlayerState.PAUSED == state ||
                PlayerState.COMPLETED == state) {
            mPlayer.start();
        }
    }

    @Override
    public void stop() {
        PlayerState state = mPlayer.getPlayerState();
        if (PlayerState.PREPARED == state ||
                PlayerState.STARTED == state ||
                PlayerState.PAUSED == state ||
                PlayerState.COMPLETED == state) {
            mPlayer.stop();
        }
    }

    private int getNextIndex() {
        int index = mCurrentIndex + 1;
        if (!CollectionUtil.isIndexInRange(mAudioList, index)) {
            index = 0;
        }
        return index;
    }

    private int getPreviousIndex() {
        int index = mCurrentIndex - 1;
        if (!CollectionUtil.isIndexInRange(mAudioList, index)) {
            index = mAudioList.size() - 1;
        }
        return index;
    }

    @Override
    public void next() {
        if (CollectionUtil.isEmpty(mAudioList)) {
            return;
        }
        playIndex(getNextIndex());
    }

    @Override
    public void previous() {
        if (CollectionUtil.isEmpty(mAudioList)) {
            return;
        }
        playIndex(getPreviousIndex());
    }

    @Override
    public void setMode(int mode) {

    }

    @Override
    public void seekTo(int position) {
        PlayerState state = mPlayer.getPlayerState();
        if (PlayerState.PREPARED == state ||
                PlayerState.STARTED == state ||
                PlayerState.PAUSED == state ||
                PlayerState.COMPLETED == state) {
            mPlayer.seekTo(position);
        }
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public Audio getCurrentAudio() {
        return mCurrentAudio;
    }

    @Override
    public void registePlayerListener(PlayerListener listener) throws RemoteException {
        mPlayerListener = listener;
    }
}
