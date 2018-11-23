/**
 * DownloadUpdateListener.java
 * com.ximalaya.downloader.download
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014-2-20 		chadwii
 *
 * Copyright (c) 2014, chadwii All Rights Reserved.
*/

package com.himer.android.download;

/**
 * ClassName:DownloadUpdateListener
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   chadwii
 * @version  
 * @since    Ver 1.1
 * @Date	 2014-2-20		下午5:20:48
 *
 * @see 	 
 */
public interface DownloadUpdateListener
{
//	public void onTaskStart(final DownloadTask task);
	public void onNewTask(final DownloadTask task);
//	public void onTaskPause(final DownloadTask task);
	public void onTaskCancel(final DownloadTask task);
	public void onTaskUpdate(final DownloadTask task);
	public void onAllTaskPaused();
	public void onAllTaskResumed();
	public void onAllTaskCanceled();
}

