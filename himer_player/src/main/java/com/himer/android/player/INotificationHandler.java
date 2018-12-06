package com.himer.android.player;

import android.app.Notification;
import android.widget.RemoteViews;

import com.himer.android.player.constants.PlayerState;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/5.
 */
public interface INotificationHandler {

    void update(PlayerState state, Audio audio);
}
