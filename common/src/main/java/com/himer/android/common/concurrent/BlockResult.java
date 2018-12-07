package com.himer.android.common.concurrent;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/7.
 */
public class BlockResult<RESULT> {

    private byte[] mLock = new byte[0];

    private RESULT mResult;

    public void set(RESULT ret) {
        mResult = ret;
    }

    public RESULT get(long timeoutMillis) {

        try {
            synchronized (mLock) {
                mLock.wait(timeoutMillis);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mResult;
    }
}
