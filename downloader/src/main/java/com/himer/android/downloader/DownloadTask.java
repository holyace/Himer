/**
 * DownloadTask.java
 * com.ximalaya.downloader.download
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2014-2-19 		chadwii
 * <p>
 * Copyright (c) 2014, TNT All Rights Reserved.
 */

package com.himer.android.downloader;

import android.util.Log;

import com.himer.android.modle.SearchSound;
import com.himer.android.modle.SoundInfo;
import com.himer.android.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ClassName:DownloadTask
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author chadwii
 * @Date 2014-2-19		上午10:32:34
 * @since Ver 1.1
 */
public class DownloadTask implements Runnable {
    public static final int FAILED = -1;
    public static final int IDLE = 0;
    public static final int WAITING = 1;
    public static final int RUNNING = 2;
    public static final int PAUSED = 3;
    public static final int STOPPED = 4;
    public static final int COMPLETED = 5;

    public long id;
    public String url;
    public String cover;
    public String title;
    public Object tag;
    public int percent;

    public int status = IDLE;

    private boolean isRunning = false;

    private DownloadManager mDownloadManager;

    private String mSubfix;
    private String mRealPath;

    public DownloadTask(SoundInfo sound) {
        id = sound.trackId;
        url = sound.playUrl64;
        title = sound.title;
        mSubfix = FileUtil.getExtensionName(url);
        init();
    }

    public DownloadTask(SearchSound sound) {
        id = sound.id;
        url = sound.play_path_aac_v224;
        mSubfix = FileUtil.getExtensionName(url);
        title = sound.title;
        init();
    }

    private void init() {
        status = IDLE;
        mDownloadManager = DownloadManager.getInstance();
    }

    public String getDownloadPath() {
        return mRealPath;
    }

    public String getSubfix() {
        return mSubfix;
    }

    private void handleComplete(String path) {
        status = COMPLETED;
        percent = 100;
        Log.e("", "Xm complete task " + title);
        mRealPath = path;
        mDownloadManager.updateTask(DownloadManager.MSG_TASK_COMPLETE, this);
    }

    @Override
    public void run() {
        isRunning = true;
        status = RUNNING;
        Log.e("", "Xm start tp downmloa " + title);
        mDownloadManager.setCurrentTask(this);
        mDownloadManager.updateTask(DownloadManager.MSG_TASK_START, this);
        if (!FileUtil.isFileSystemCanUse()) {
            mDownloadManager.pauseAllTask();
            return;
        }
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        InputStream is = null;

        try {
            String dirStr = mDownloadManager.getDownloadDir();
            File dir = new File(dirStr);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            long startPos = 0;
            long fileLength = 0;
            File soundFile = new File(dir, title + mSubfix);
            if (soundFile.exists()) {
                handleComplete(soundFile.getCanonicalPath());
                return;
            }
            soundFile = new File(dir, title + mSubfix + ".temp");
            if (!soundFile.exists()) {
                soundFile.createNewFile();
            }
            startPos = soundFile.length();
            raf = new RandomAccessFile(soundFile.getCanonicalPath(), "rwd");
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "*/*");
//			conn.setRequestProperty("Accept-Language","zh-CN");
//			conn.setRequestProperty("Charset","UTF-8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Range", String.format("bytes=%d-", startPos));
            conn.connect();
            String lenStr = conn.getHeaderField("Content-Length");
            is = conn.getInputStream();
            long leftLength = 0;
            long downloadLength = 0;
            if (lenStr == null ||
                    "".equals(lenStr) ||
                    (leftLength = Long.parseLong(lenStr)) <= 0) {
                status = FAILED;
                mDownloadManager.updateTask(DownloadManager.MSG_TASK_FAILE, this);
                return;
            }
            long total = leftLength + startPos;
            byte[] buff = new byte[8192];
            int n = 0;
            while (isRunning && (n = is.read(buff)) > 0) {
                raf.seek(raf.length());
                raf.write(buff, 0, n);
                leftLength -= n;
                downloadLength += n;
                int currPercent = (int) ((startPos + downloadLength) * 100 / (float) total);
                if (leftLength == 0 || currPercent == 100) {
                    File completeF = new File(dir, title + mSubfix);
                    if (completeF.exists()) {
                        completeF = new File(dir, title + System.currentTimeMillis() + mSubfix);
                    }
                    boolean ret = soundFile.renameTo(completeF);
                    if (!ret) {
                        Log.e("", "Xm rename faile " + title);
                        mDownloadManager.updateTask(DownloadManager.MSG_TASK_FAILE, this);
                    } else {
                        handleComplete(completeF.getCanonicalPath());
                    }
                } else {
                    if (currPercent - percent > 5) {
                        percent = currPercent;
                        mDownloadManager.updateTask(DownloadManager.MSG_PROGRESS_UPDATE, this);
                    }
                }
            }
        } catch (Exception e) {
            status = DownloadTask.FAILED;
            mDownloadManager.updateTask(DownloadManager.MSG_TASK_FAILE, this);
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mDownloadManager.setCurrentTask(null);
        }
    }

    public void stopExcute() {
        isRunning = false;
        Log.e("", "Xm pause download " + title);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DownloadTask other = (DownloadTask) obj;
        if (id != other.id)
            return false;
        return true;
    }


}

