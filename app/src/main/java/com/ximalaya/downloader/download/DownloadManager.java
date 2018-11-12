/**
 * DownloadManager.java
 * com.ximalaya.downloader
 *
 *
 *   ver     date      		author
 *   		 2014-2-18 		chadwii
 *
 * Copyright (c) 2014, TNT All Rights Reserved.
*/

package com.ximalaya.downloader.download;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.ximalaya.downloader.AppConstant;
import com.ximalaya.downloader.modle.SearchSound;
import com.ximalaya.downloader.modle.SoundInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:DownloadManager
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   chadwii
 * @version  
 * @since    Ver 1.1
 * @Date	 2014-2-18 4:52:33
 *
 */
public class DownloadManager 
{
	static final int MSG_NEW_TASK = 1;
	static final int MSG_PAUSE_TASK = 2;
	static final int MSG_RESUME_TASK = 3;
	static final int MSG_CANCEL_TASK = 4;
	static final int MSG_TASK_COMPLETE = 5;
	static final int MSG_TASK_FAILE = 6;
	static final int MSG_PAUSE_ALL = 7;
	static final int MSG_CANCEL_ALL = 8;
	static final int MSG_RESUME_ALL = 9;
	static final int MSG_PROGRESS_UPDATE = 10;
	static final int MSG_TASK_START = 11;
	
	private static DownloadManager sManager;
	private static byte[] sSyncLock = new byte[0];
	
	private DownloadExcutor mDownloadExcutor;
	
	private String mDownloadDir = AppConstant.DOWNLOAD_DIR;
	
//	private ArrayList<Runnable> mAllTask = 
//			new ArrayList<Runnable>();
	private ArrayList<Runnable> mPausedTask = 
			new ArrayList<Runnable>();
	
	private ArrayList<DownloadUpdateListener> mDownloadUpdateListeners =
			new ArrayList<DownloadUpdateListener>();
	
	private Handler mUpdateHandler;

	private String mBaseDir;

	public static DownloadManager getInstance()
	{
		if (sManager == null)
		{
			synchronized (sSyncLock) 
			{
				if (sManager == null) 
				{
					sManager = new DownloadManager();
				}
			}
		}
		return sManager;
	}
	
	public Uri getFile(DownloadTask task)
	{
		return Uri.fromFile(new File(getDownloadDir(), task.title + task.getSubfix()));
	}
	
	public void pauseAllTask()
	{
		mDownloadExcutor.pauseAllTask();
	}
	
	public void pauseTask(DownloadTask task)
	{
		mDownloadExcutor.pauseTask(task);
	}
	
	public void resumeAllTask()
	{
		mDownloadExcutor.resumeAllTask();
	}
	
	public void resumeTask(DownloadTask task)
	{
		mDownloadExcutor.resumeTask(task);
	}
	
	void setCurrentTask(DownloadTask task)
	{
		mDownloadExcutor.setCurrentTask(task);
	}
	
	public void setDownloadDir(String dir)
	{
		mDownloadDir = dir;
	}

	public void setAppDir(String baseDir) {
		mBaseDir = baseDir;
	}

	String getBaseDir() {
		if (TextUtils.isEmpty(mBaseDir)) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		else {
			return mBaseDir;
		}
	}

	public String getDownloadDir()
	{
		return getBaseDir() + File.separator + mDownloadDir;
	}
	
	private DownloadManager()
	{
		mDownloadExcutor = DownloadExcutor.getInstance();
		mUpdateHandler = new UpdateHandler(Looper.getMainLooper());
	}
	
	public void downloadSound(SoundInfo sound, boolean now)
	{
		DownloadTask task = new DownloadTask(sound);
		mDownloadExcutor.download(task, now);
	}
	
	public void downloadSound(SearchSound sound, boolean now)
	{
		DownloadTask task = new DownloadTask(sound);
		mDownloadExcutor.download(task, now);
	}

	void deleteSound(String title) {
		
	}
	
	public List<DownloadTask> getAllTask()
	{
		return mDownloadExcutor.getAllTask();
	}
	
	public void registeDownloadUpdateListener(DownloadUpdateListener l)
	{
		if (l == null || mDownloadUpdateListeners.contains(l))
		{
			return;
		}
		mDownloadUpdateListeners.add(l);
	}
	
	public void unregisteDownloadUpdateListener(DownloadUpdateListener l)
	{
		if (l == null || !mDownloadUpdateListeners.contains(l))
		{
			return;
		}
		mDownloadUpdateListeners.remove(l);
	}
	
	@SuppressLint("HandlerLeak")
	private class UpdateHandler extends Handler
	{
		public UpdateHandler(Looper looper)
		{
			super(looper);
		}

		@Override
		public void handleMessage(Message msg)
		{
			
			super.handleMessage(msg);
			if (msg == null)
			{
				return;
			}
			DownloadTask task = (DownloadTask) msg.obj;
			switch (msg.what)
			{
			case MSG_NEW_TASK:
				handleNewTask(task);
				break;
			case MSG_CANCEL_TASK:
				handleCancelTask(task);
				break;
			case MSG_PAUSE_TASK:
				handlePauseTask(task);
				break;
			case MSG_RESUME_TASK:
				handleResumeTask(task);
				break;
			case MSG_PAUSE_ALL:
				handlePauseAll();
				break;
			case MSG_RESUME_ALL:
				handleResumeAll();
				break;
			case MSG_CANCEL_ALL:
				handleCancelAll();
				break;
			case MSG_TASK_COMPLETE:
				hanleTaskComplete(task);
				break;
			case MSG_TASK_FAILE:
				handleTaskFaile(task);
				break;
			case MSG_PROGRESS_UPDATE:
				handleProgressUpdate(task);
				break;
			case MSG_TASK_START:
				handleTaskStart(task);
				break;
			default:
				break;
			}
		}
	}
	
	void updateTask(int what, DownloadTask task)
	{
		Message msg = mUpdateHandler.obtainMessage();
		msg.what = what;
		msg.obj = task;
		msg.sendToTarget();
	}
	
	void handleTaskStart(DownloadTask task)
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onTaskUpdate(task);
		}
	}
	
	void handleNewTask(DownloadTask task)
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onNewTask(task);
		}
	}
	
	void handlePauseTask(DownloadTask task)
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onTaskUpdate(task);
		}
	}
	
	void handleResumeTask(DownloadTask task)
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onTaskUpdate(task);
		}
	}
	
	void handleCancelTask(DownloadTask task)
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onTaskCancel(task);
		}
	}
	
	void handlePauseAll()
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onAllTaskPaused();
		}
	}
	
	void handleResumeAll()
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onAllTaskResumed();
		}
	}
	
	void handleCancelAll()
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onAllTaskCanceled();
		}
	}
	
	void hanleTaskComplete(DownloadTask task)
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onTaskUpdate(task);
		}
	}
	
	void handleTaskFaile(DownloadTask task)
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onTaskUpdate(task);
		}
	}
	
	void handleProgressUpdate(DownloadTask task)
	{
		for (DownloadUpdateListener l : mDownloadUpdateListeners)
		{
			l.onTaskUpdate(task);
		}
	}
	
	public void exit()
	{
		mDownloadExcutor.exit();
		sManager = null;
	}
}

