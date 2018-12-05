package com.himer.android.player;

import android.app.Notification;
import android.widget.RemoteViews;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/5.
 */
public interface INotificationHandler {

    Notification getNotification();

    RemoteViews getRemoteViews();

    void update(RemoteViews remoteViews, Audio audio);
}
