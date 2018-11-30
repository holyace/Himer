package com.himer.android.player;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.himer.android.player.util.CollectionUtil;

import java.io.IOException;
import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class PlayerService extends Service {

    private PlayerStub mPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return mPlayer;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new PlayerStub();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}
