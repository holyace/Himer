package com.himer.android.player.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.himer.android.player.constants.PlayerConstants;
import com.himer.android.player.impl.PlayerStub;
import com.himer.android.player.R;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class PlayerService extends Service implements PlayerConstants {

    private static final String TAG = PlayerService.class.getSimpleName();
    private static final String CHANNEL_ID = "com.himer.android";
    private static final String CHANNEL_NAME = "player";

    private PlayerStub mPlayer;
    private Context mContext;
    private RemoteViews mNotificationContent;

    private int mNotificationId;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind ");
        return mPlayer;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mPlayer = new PlayerStub();
        Log.e(TAG, "onCreate ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (enableForeground()) {
            startForeground(PlayerConstants.NOTIFCATION_ID, getNotification());
        }
        Log.e(TAG, "onStartCommand ");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
        }
        stopForeground(true);
        Log.e(TAG, "onDestroy ");
    }

    protected boolean enableForeground() {
        return true;
    }

    protected Notification getNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);

            notification = new Notification.Builder(this, channel.getId())
                    .setContentTitle("嗨马")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .build();
        } else {
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle("嗨马")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .build();
        }

        return notification;
    }

}
