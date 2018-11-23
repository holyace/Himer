package com.chad.android.common.concurrent;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public abstract class SafeJob extends Job {

    @Override
    public void run() {
        super.run();
        try {
            safeRun();
        }
        catch (Exception e) {

        }
    }

    public abstract void safeRun();
}
