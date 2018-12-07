/**
 * DownloadFragment.java
 * com.ximalaya.downloader
 * <p>
 * Function�� TODO
 * <p>
 * ver     date      		author
 * ��������������������������������������������������������������������
 * 2014-2-18 		chadwii
 * <p>
 * Copyright (c) 2014, TNT All Rights Reserved.
 */

package com.himer.android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.himer.android.R;
import com.himer.android.common.concurrent.HMExecutor;
import com.himer.android.common.concurrent.SafeJob;
import com.himer.android.common.service.ServiceManager;
import com.himer.android.common.service.shell.IDBService;
import com.himer.android.common.util.HLog;
import com.himer.android.download.DownloadManager;
import com.himer.android.download.DownloadTask;
import com.himer.android.download.DownloadUpdateListener;
import com.himer.android.modle.SearchSound;

import java.util.ArrayList;

/**
 * ClassName:DownloadFragment
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author chadwii
 * @Date 2014-2-18 4:29:01
 * @since Ver 1.1
 */
public class DownloadFragment extends Fragment {
    private static final String TAG = DownloadFragment.class.getSimpleName();
    private Activity mContext;
    private View mContent;
    private ListView mListView;
    private DownloadAdapter mAdapter;
    private DownloadUpdateListener mDownloadListener;
    private DownloadManager mDownloadManager =
            DownloadManager.getInstance();

    private ArrayList<DownloadTask> mDownloadTask =
            new ArrayList<DownloadTask>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.download, null);
        return mContent;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initUI();
        initData();
        initDownloadListener();
    }

    private void initDownloadListener() {
        mDownloadListener = new DownloadUpdateListener() {

            @Override
            public void onTaskUpdate(DownloadTask task) {
                if (task == null) {
                    return;
                }
                Log.e("", "Xm onTaskUpdate " + task.title + ", "
                        + task.status + ", " + task.percent);

                if (!mDownloadTask.contains(task)) {
                    return;
                }

                mAdapter.updateItem(task);

                if (100 == task.percent) {
                    final Object tag = task.tag;
                    if (tag instanceof SearchSound) {
                        final SearchSound ss = (SearchSound) tag;
                        ss.setDownload_path(task.getDownloadPath());
                        ss.setSaveTime(System.currentTimeMillis());
                        HMExecutor.runNow(new SafeJob() {
                            @Override
                            public void safeRun() {
                                IDBService db = ServiceManager.getService(IDBService.class);
                                long id = db.insertOrUpdate(ss);
                                if (id < 0) {
                                    HLog.e(TAG, "insert fail id = ", id, "sound title = ", ss.getTitle());
                                }
                            }
                        });
                    }
                }
            }

//			@Override
//			public void onTaskStart(DownloadTask task)
//			{
//			}
//
//			@Override
//			public void onTaskPause(DownloadTask task)
//			{
//			}

            @Override
            public void onTaskCancel(DownloadTask task) {
                Log.e("", "Xm onTaskCancel ");
                if (!mDownloadTask.contains(task)) {
                    return;
                }
                mDownloadTask.remove(task);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNewTask(DownloadTask task) {
                Log.e("", "Xm onNewTask ");
                if (mDownloadTask.contains(task)) {
                    return;
                }
                mDownloadTask.add(task);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAllTaskPaused() {
                Log.e("", "Xm onAllTaskPaused ");
                for (DownloadTask t : mDownloadTask) {
                    mAdapter.updateItem(t);
                }
            }

            @Override
            public void onAllTaskResumed() {
                Log.e("", "Xm onAllTaskResumed ");
                for (DownloadTask t : mDownloadTask) {
                    mAdapter.updateItem(t);
                }
            }

            @Override
            public void onAllTaskCanceled() {
                Log.e("", "Xm onAllTaskCanceled ");
                mDownloadTask.clear();
                mAdapter.notifyDataSetChanged();
            }
        };
        mDownloadManager.registeDownloadUpdateListener(mDownloadListener);
    }

    private void initData() {
        Log.e("", "Xm initData");
        mDownloadTask.addAll(mDownloadManager.getAllTask());
        mAdapter.notifyDataSetChanged();
    }

    private void initUI() {
        mListView = (ListView) mContent.findViewById(R.id.task_list);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                DownloadTask task = mDownloadTask.get(position);
                if (task == null) {
                    return;
                }
                switch (task.status) {
                    case DownloadTask.IDLE:
                    case DownloadTask.PAUSED:
                    case DownloadTask.FAILED:
                        Toast.makeText(mContext, "重新下载 \"" +
                                task.title + "\"", Toast.LENGTH_SHORT).show();
                        mDownloadManager.resumeTask(task);
                        break;
                    case DownloadTask.RUNNING:
                    case DownloadTask.WAITING:
                        Toast.makeText(mContext, "暂停下载 \"" +
                                task.title + "\"", Toast.LENGTH_SHORT).show();
                        mDownloadManager.pauseTask(task);
                        break;
                    case DownloadTask.COMPLETED:
                        try {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(mDownloadManager.getFile(task), "audio/*");
                            mContext.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

        });
        mAdapter = new DownloadAdapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        mDownloadManager.unregisteDownloadUpdateListener(mDownloadListener);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

    }

    private String getDownloadStatus(int status) {
        String str = null;
        switch (status) {
            case DownloadTask.FAILED:
                str = "下载失败";
                break;
            case DownloadTask.IDLE:
            case DownloadTask.WAITING:
                str = "等待中";
                break;
            case DownloadTask.RUNNING:
                str = "下载中";
                break;
            case DownloadTask.PAUSED:
                str = "暂停中";
                break;
            case DownloadTask.STOPPED:
                str = "已停止";
                break;
            case DownloadTask.COMPLETED:
                str = "已完成";
                break;
            default:
                str = "等待中";
                break;
        }
        return str;
    }

    class DownloadAdapter extends BaseAdapter {

        public void updateItem(DownloadTask task) {
            int index = mDownloadTask.indexOf(task);
            if (index == -1) {
                return;
            }
            int firstPos = mListView.getFirstVisiblePosition();
            View v = mListView.getChildAt(index - firstPos);
            if (v == null) {
                return;
            }
            ViewHolder holder = (ViewHolder) v.getTag();
            holder.title.setText(task.title);
            holder.progressBar.setProgress(task.percent);
            holder.download.setText(getDownloadStatus(task.status));
        }

        @Override
        public int getCount() {

            return mDownloadTask.size();

        }

        @Override
        public Object getItem(int arg0) {

            return null;

        }

        @Override
        public long getItemId(int arg0) {

            return 0;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            DownloadTask task = mDownloadTask.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.search_result_item, null);
                holder.icon = (ImageView) convertView.findViewById(R.id.sound_icon);
                holder.title = (TextView) convertView.findViewById(R.id.sound_title);
                holder.progressBar = (ProgressBar) convertView.findViewById(R.id.download_progress);
                holder.download = (TextView) convertView.findViewById(R.id.sound_download);
                holder.download.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                holder.progressBar.setVisibility(View.VISIBLE);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(task.title);
            holder.download.setText(getDownloadStatus(task.status));
            holder.progressBar.setProgress(task.percent);
            return convertView;

        }

    }

    class ViewHolder {
        ImageView icon;
        TextView title;
        ProgressBar progressBar;
        TextView download;
    }
}

