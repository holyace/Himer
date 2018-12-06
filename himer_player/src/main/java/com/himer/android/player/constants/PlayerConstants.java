package com.himer.android.player.constants;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/30.
 */
public interface PlayerConstants {

    String ACTION_SERVICE = "com.himer.android.action.PlayerService";
    String ACTION_PLAY_OR_PAUSE = "com.himer.android.action.ACTION_PLAY_OR_PAUSE";
    String ACTION_NEXT = "com.himer.android.action.ACTION_NEXT";
    String ACTION_PREVIOUS = "com.himer.android.action.ACTION_PREVIOUS";
    String ACTION_CLOSE = "com.himer.android.action.ACTION_CLOSE";
    String SERVICE_PACKAGE = "com.himer.android.player";
    int NOTIFCATION_ID = 12313134;

    String CHANNEL_ID = "com.himer.android";
    String CHANNEL_NAME = "player";

    int REQUEST_CODE_PLAY_OR_PAUSE = 0X00009;
    int REQUEST_CODE_PLAY_NEXT = REQUEST_CODE_PLAY_OR_PAUSE + 1;
    int REQUEST_CODE_PLAY_PREVIOUS = REQUEST_CODE_PLAY_OR_PAUSE + 2;
    int REQUEST_CODE_CLOSE = REQUEST_CODE_PLAY_OR_PAUSE + 3;
}
