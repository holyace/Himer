/**
 * BaseDownloadTask.java
 * com.ximalaya.downloader.download
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2014-2-20 		chadwii
 * <p>
 * Copyright (c) 2014, TNT All Rights Reserved.
 */

package com.himer.android.download;

import android.text.TextUtils;

import com.himer.android.util.FileUtil;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ClassName:BaseDownloadTask
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author chadwii
 * @version
 * @since Ver 1.1
 * @Date 2014-2-20		下午5:03:08
 *
 * @see
 */
public abstract class BaseDownloadTask implements Runnable {
    public static final int FAILED = -1;
    public static final int IDLE = 0;
    public static final int WAITING = 1;
    public static final int RUNNING = 2;
    public static final int PAUSED = 3;
    public static final int STOPPED = 4;
    public static final int COMPLETED = 5;

    protected int status = IDLE;
    protected boolean isRunning = false;

    @Override
    final public void run() {
        isRunning = true;
        status = RUNNING;
        DownloadManager manager = DownloadManager.getInstance();
//		manager.setCurrentTask(this);
//		manager.updateTask(DownloadManager.MSG_TASK_START, this);

        String url = getDownloadUrl();
        String path = getFilePath();
        String name = getFileName();
        if (TextUtils.isEmpty(url) ||
                TextUtils.isEmpty(path) ||
                TextUtils.isEmpty(name)) {
            status = FAILED;
//			manager.updateTask(DownloadManager.MSG_TASK_FAILE, this);
            return;
        }
        HttpURLConnection conn = null;
        InputStream is = null;
        RandomAccessFile raf = null;
        File file = null;
        try {
            if (!makeDownloadDir()) {
                status = FAILED;
//				manager.updateTask(DownloadManager.MSG_TASK_FAILE, this);
                return;
            }
            file = new File(path, name);
            if (!file.exists()) {
                file.createNewFile();
            }
            raf = new RandomAccessFile(file, "rwd");
            conn = (HttpURLConnection) new URL(url).openConnection();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {

        }
    }

    private boolean makeDownloadDir() {
        return FileUtil.makeDir(getFilePath());
    }

    void stopExecute() {
        isRunning = false;
    }

    public int getStatus() {
        return status;
    }

    void setStatus(int status) {
        this.status = status;
    }

    protected abstract String getDownloadUrl();

    protected abstract String getFileName();

    protected abstract String getFilePath();
}

