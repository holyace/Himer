package com.himer.android.common.concurrent;

import com.himer.android.common.util.HLog;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public abstract class SafeJob extends Job {

    private static final String TAG = SafeJob.class.getSimpleName();

    @Override
    public void run() {
        super.run();
        try {
            safeRun();
        } catch (Exception e) {
            HLog.exception(TAG, e);
        }
    }

    public abstract void safeRun();
}
