package com.himer.android.player.impl;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.himer.android.common.service.ServiceManager;
import com.himer.android.common.service.shell.IImageService;
import com.himer.android.common.service.shell.ImageListener;
import com.himer.android.player.Audio;
import com.himer.android.player.INotificationHandler;
import com.himer.android.player.IPlayerListener;
import com.himer.android.player.PlayerManager;
import com.himer.android.player.R;
import com.himer.android.player.constants.PlayerConstants;
import com.himer.android.player.constants.PlayerState;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/5.
 */
public class NotificationHandler implements INotificationHandler,
        PlayerConstants, IPlayerListener {

    private Context mContext;
    private Notification mNotification;
    private RemoteViews mRemoteViews;

    private Intent mServiceIntent;

    private NotificationManager mNotificationManager;

    public NotificationHandler(Context context) {
        mContext = context;

        mNotificationManager = (NotificationManager) mContext.
                getSystemService(Context.NOTIFICATION_SERVICE);

        mServiceIntent = new Intent(ACTION_SERVICE);
        mServiceIntent.setPackage(mContext.getPackageName());
    }

    @Override
    public void update(PlayerState state, Audio audio) {

        if (mNotification == null) {
            initNotification();
        }

        if (audio != null) {
            mRemoteViews.setTextViewText(R.id.title, audio.getTitle());
            IImageService is = ServiceManager.getService(IImageService.class);
            is.asyncDownload(audio.getCoverPath(), new ImageListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    mRemoteViews.setImageViewBitmap(R.id.icon, bitmap);
                    mNotificationManager.notify(NOTIFCATION_ID, mNotification);
                }

                @Override
                public void onFaile(String errorCode, String errorMessage) {

                }
            });
        }

        if (state != null) {
            mRemoteViews.setImageViewResource(R.id.play_or_pause, getPlayStateIcon(state));
        }

        mNotificationManager.notify(NOTIFCATION_ID, mNotification);
    }

    private int getPlayStateIcon(PlayerState state) {
        if (PlayerState.STARTED == state) {
            return R.drawable.pause;
        } else {
            return R.drawable.play;
        }

    }

    private void initNotification() {
        NotificationManager manager = (NotificationManager) mContext.
                getSystemService(Context.NOTIFICATION_SERVICE);

        RemoteViews remoteViews = new RemoteViews(
                mContext.getPackageName(), R.layout.notification);

        remoteViews.setOnClickPendingIntent(R.id.play_or_pause,
                getPendingIntent(REQUEST_CODE_PLAY_OR_PAUSE, ACTION_PLAY_OR_PAUSE));

        remoteViews.setOnClickPendingIntent(R.id.next,
                getPendingIntent(REQUEST_CODE_PLAY_NEXT, ACTION_NEXT));

        remoteViews.setOnClickPendingIntent(R.id.previous,
                getPendingIntent(REQUEST_CODE_PLAY_PREVIOUS, ACTION_PREVIOUS));

        remoteViews.setOnClickPendingIntent(R.id.close,
                getPendingIntent(REQUEST_CODE_CLOSE, ACTION_CLOSE));

        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(null, null);
            manager.createNotificationChannel(channel);

            notification = new Notification.Builder(mContext, channel.getId())
                    .setCustomContentView(remoteViews)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .build();
        } else {
            notification = new NotificationCompat.Builder(mContext)
                    .setCustomContentView(remoteViews)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .build();
        }

        mRemoteViews = remoteViews;
        mNotification = notification;
    }

    private PendingIntent getPendingIntent(int reqCode, String action) {
        mServiceIntent.setAction(action);
        return PendingIntent.getService(
                mContext,
                reqCode,
                mServiceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onPlay() {
        update(PlayerState.STARTED, null);
    }

    @Override
    public void onPause() {
        update(PlayerState.PAUSED, null);
    }

    @Override
    public void onStop() {
        update(PlayerState.STOPPED, null);
    }

    @Override
    public void onComplete() {
        update(PlayerState.COMPLETED, null);
    }

    @Override
    public void onPlayChange(int index) {
        update(PlayerState.STOPPED, PlayerManager.getInstance(mContext).getAudio(index));
    }

    @Override
    public void onPositionChange(int position, int duration) {

    }

    @Override
    public void onBufferingChange(int bufferPosition) {

    }

    @Override
    public void onError(int errorCode, String errorMessage) {
        update(PlayerState.STOPPED, null);
    }
}
