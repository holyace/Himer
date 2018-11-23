package com.himer.android.init;

import com.himer.android.Global;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public class ImageServiceInit extends BaseInitTask {

    @Override
    void execute() {
        Fresco.initialize(Global.getApplication());
    }
}
