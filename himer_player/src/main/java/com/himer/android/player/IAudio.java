package com.himer.android.player;

import android.os.Parcelable;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public interface IAudio extends Parcelable {

    String getPath();

    String getTitle();

    String getCoverPath();
}
