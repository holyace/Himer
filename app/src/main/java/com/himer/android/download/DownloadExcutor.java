/**
 * DownloadExcutor.java
 * com.ximalaya.downloader.download
 * <p>
 * <p>
 * ver     date      		author
 * 2014-2-18 		chadwii
 * <p>
 * Copyright (c) 2014, TNT All Rights Reserved.
 */

package com.himer.android.download;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:DownloadExcutor
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author chadwii
 * @version
 * @since Ver 1.1
 * @Date 2014-2-18 4:53:01
 *
 */
public class DownloadExcutor {

    private static DownloadExcutor sExcutor;
    private static byte[] sSyncLock = new byte[0];

    private ThreadPoolExecutor mThreadPool;
    private BlockingQueue<Runnable> mTaskQueue;
    private DownloadTask mCurentTask;

//	private DownloadManager mDownloadManager;

    private ArrayList<Runnable> mAllTask =
            new ArrayList<Runnable>();
    private ArrayList<Runnable> mPausedTask =
            new ArrayList<Runnable>();

    public static DownloadExcutor getInstance() {
        if (sExcutor == null) {
            synchronized (sSyncLock) {
                if (sExcutor == null) {
                    sExcutor = new DownloadExcutor();
                }
            }
        }
        return sExcutor;
    }

    private DownloadExcutor() {
        setup();
    }

    private void setup() {
        mTaskQueue = new LinkedBlockingQueue<Runnable>();
        mThreadPool = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, mTaskQueue);
        mThreadPool.setRejectedExecutionHandler(new RejectedExecutionHandler() {

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                synchronized (mPausedTask) {
                    if (r == null) {
                        return;
                    }
                    DownloadTask task = (DownloadTask) r;
                    Log.e("", "Xm reject task " + task.title);
                    task.status = DownloadTask.PAUSED;
                    mPausedTask.add(task);
                    DownloadManager.getInstance().
                            updateTask(DownloadManager.MSG_PAUSE_TASK, task);
                }
            }
        });
    }

    public void pauseAllTask() {
        synchronized (mPausedTask) {
            synchronized (mTaskQueue) {
                mPausedTask.addAll(mTaskQueue);
                mTaskQueue.clear();
                for (Runnable r : mPausedTask) {
                    ((DownloadTask) r).status = DownloadTask.PAUSED;
                }
                if (mCurentTask != null) {
                    mCurentTask.stopExcute();
                    mCurentTask.status = DownloadTask.PAUSED;
                    mPausedTask.add(0, mCurentTask);
                }
            }
        }
        DownloadManager.getInstance().updateTask(DownloadManager.MSG_PAUSE_ALL, null);
    }

    public void pauseTask(DownloadTask task) {
        if (task == null) {
            return;
        }
        task.status = DownloadTask.PAUSED;
        if (task == mCurentTask) {
            task.stopExcute();
            synchronized (mPausedTask) {
                mPausedTask.add(task);
            }
        } else {
            synchronized (mTaskQueue) {
                if (mTaskQueue.contains(task)) {
                    task.stopExcute();
                    synchronized (mPausedTask) {
                        mTaskQueue.remove(task);
                        mPausedTask.add(task);
                    }
                }
            }
        }
        DownloadManager.getInstance().updateTask(DownloadManager.MSG_PAUSE_TASK, task);
    }

    public void resumeAllTask() {
        synchronized (mPausedTask) {
            for (Runnable r : mPausedTask) {
                ((DownloadTask) r).status = DownloadTask.WAITING;
                mThreadPool.execute(r);
            }
            mPausedTask.clear();
        }
        DownloadManager.getInstance().updateTask(DownloadManager.MSG_RESUME_ALL, null);
    }

    public void resumeTask(DownloadTask task) {
        if (task == null) {
            return;
        }
        if (task == mCurentTask) {
            return;
        }
        task.status = DownloadTask.WAITING;
        synchronized (mPausedTask) {
            if (mPausedTask.contains(task)) {
                mPausedTask.remove(task);
            }
        }
        synchronized (mTaskQueue) {
            if (mTaskQueue.contains(task)) {
                return;
            }
        }
        mThreadPool.execute(task);
        DownloadManager.getInstance().updateTask(DownloadManager.MSG_RESUME_TASK, task);
    }

    public void download(DownloadTask task, boolean now) {
        if (task == null) {
            return;
        }
        if (task == mCurentTask) {
            return;
        }
        synchronized (mTaskQueue) {
            if (mTaskQueue.contains(task)) {
                return;
            }
        }
        task.status = DownloadTask.WAITING;
        if (now) {
            if (mCurentTask == null) {
                mThreadPool.execute(task);
            } else {
                synchronized (mTaskQueue) {
                    ArrayList<Runnable> tasks =
                            new ArrayList<Runnable>(mTaskQueue.size());
                    mCurentTask.stopExcute();
                    mCurentTask.status = DownloadTask.WAITING;
                    tasks.add(mCurentTask);
                    DownloadManager.getInstance().
                            updateTask(DownloadManager.MSG_PAUSE_TASK, mCurentTask);
                    tasks.addAll(mTaskQueue);
                    mTaskQueue.clear();
                    mThreadPool.execute(task);
                    for (Runnable r : tasks) {
                        mThreadPool.execute(r);
                    }
                }
            }
        } else {
            mThreadPool.execute(task);
        }
        DownloadManager.getInstance().updateTask(DownloadManager.MSG_NEW_TASK, task);
    }

    public void cancelAllTask(boolean deep) {
        synchronized (mTaskQueue) {
            mTaskQueue.clear();
        }
        if (mCurentTask != null) {
            mCurentTask.stopExcute();
            if (deep) {
                DownloadManager.getInstance().
                        deleteSound(mCurentTask.title);
            }
        }
        synchronized (mPausedTask) {
            mPausedTask.clear();
        }
        DownloadManager.getInstance().updateTask(DownloadManager.MSG_CANCEL_ALL, null);
    }

    public void cancelTask(DownloadTask task, boolean deep) {
        if (task == null) {
            return;
        }
        task.status = DownloadTask.IDLE;
        synchronized (mTaskQueue) {
            if (mCurentTask == task) {
                mCurentTask.stopExcute();
            } else {
                if (mTaskQueue.contains(task)) {
                    mTaskQueue.remove(task);
                }
                synchronized (mPausedTask) {
                    if (mPausedTask.contains(task)) {
                        mPausedTask.remove(task);
                    }
                }
            }
        }
        if (deep) {
            DownloadManager.getInstance().
                    deleteSound(task.title);
        }
        DownloadManager.getInstance().updateTask(DownloadManager.MSG_CANCEL_TASK, task);
    }

    public void setCurrentTask(DownloadTask task) {
        mCurentTask = task;
    }

    public List<DownloadTask> getAllTask() {
        List<DownloadTask> list = new ArrayList<DownloadTask>();
        synchronized (mTaskQueue) {
            if (mCurentTask != null) {
                list.add(mCurentTask);
            }
            list.addAll(Arrays.asList(mTaskQueue.toArray(new DownloadTask[0])));
        }
        return list;
    }

    void exit() {
        mThreadPool.shutdownNow();
        if (mCurentTask != null) {
            mCurentTask.stopExcute();
        }
        sExcutor = null;
    }

}

