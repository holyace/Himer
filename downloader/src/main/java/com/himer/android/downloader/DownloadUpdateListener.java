/**
 * DownloadUpdateListener.java
 * com.ximalaya.downloader.download
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2014-2-20 		chadwii
 * <p>
 * Copyright (c) 2014, chadwii All Rights Reserved.
 */

package com.himer.android.downloader;

/**
 * ClassName:DownloadUpdateListener
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author chadwii
 * @Date 2014-2-20		下午5:20:48
 * @see
 * @since Ver 1.1
 */
public interface DownloadUpdateListener {
    //	public void onTaskStart(final DownloadTask task);
    public void onNewTask(final DownloadTask task);

    //	public void onTaskPause(final DownloadTask task);
    public void onTaskCancel(final DownloadTask task);

    public void onTaskUpdate(final DownloadTask task);

    public void onAllTaskPaused();

    public void onAllTaskResumed();

    public void onAllTaskCanceled();
}

