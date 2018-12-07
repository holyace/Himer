package com.himer.android.common.concurrent;

import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public class HMExecutor {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    private static Executor sDefExecutor;

    private static Handler mUiHandler = new Handler(Looper.getMainLooper());

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "HMExecutor #" + mCount.getAndIncrement());
        }
    };

    private static Timer sTimer = new Timer();

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        sDefExecutor = threadPoolExecutor;
    }

    public static void runNow(Job job) {
        runNow(sDefExecutor, job);
    }

    public static void runNow(Executor executor, Job job) {
        executor.execute(job);
    }

    public static void runDelay(final Job job, long delay) {

        if (delay <= 0) {
            runNow(job);
            return;
        }

        sTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runNow(job);
            }
        }, delay);
    }

    public static void runUiThread(Job job) {
        runUiThread(job, 0);
    }

    public static void runUiThread(Job job, long delay) {
        if (delay <= 0) {
            mUiHandler.post(job);
        } else {
            mUiHandler.postDelayed(job, delay);
        }
    }
}
