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

import com.himer.android.BR;
import com.himer.android.R;
import com.himer.android.common.concurrent.HMExecutor;
import com.himer.android.common.concurrent.SafeJob;
import com.himer.android.common.service.ServiceManager;
import com.himer.android.common.service.shell.IDBService;
import com.himer.android.common.util.HLog;
import com.himer.android.databinding.BindingAdapter;
import com.himer.android.databinding.BindingListAdapter;
import com.himer.android.download.DownloadManager;
import com.himer.android.download.DownloadTask;
import com.himer.android.download.DownloadUpdateListener;
import com.himer.android.modle.SearchSound;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class LocalFileFragment extends Fragment {
    private static final String TAG = LocalFileFragment.class.getSimpleName();

    private Activity mContext;
    private View mContent;
    private ListView mListView;
    //    private DownloadAdapter mAdapter;
    private BindingListAdapter<SearchSound> mListAdapter;

    private ArrayList<SearchSound> mDownlaoded =
            new ArrayList<>();

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
        initListener();
    }

    private void initListener() {
        DownloadManager.getInstance().registeDownloadUpdateListener(new DownloadUpdateListener() {
            @Override
            public void onNewTask(DownloadTask task) {

            }

            @Override
            public void onTaskCancel(DownloadTask task) {

            }

            @Override
            public void onTaskUpdate(DownloadTask task) {
                if (DownloadTask.COMPLETED == task.status || 100 == task.percent) {
                    updateData();
                }
            }

            @Override
            public void onAllTaskPaused() {

            }

            @Override
            public void onAllTaskResumed() {

            }

            @Override
            public void onAllTaskCanceled() {

            }
        });
    }

    private void updateData() {
        HMExecutor.runDelay(new SafeJob() {
            @Override
            public void safeRun() {
                initData();
            }
        }, 1000);
    }

    private void initData() {
        HLog.e(TAG, "Xm initData");
        HMExecutor.runNow(new SafeJob() {
            @Override
            public void safeRun() {
                IDBService db = ServiceManager.getService(IDBService.class);
                final List<SearchSound> list = db.queryAll(SearchSound.class);
                if (list == null || list.isEmpty()) {
                    HLog.e(TAG, "initData get empty result");
                    return;
                }
                getActivity().runOnUiThread(new SafeJob() {
                    @Override
                    public void safeRun() {
                        mDownlaoded.clear();
                        mDownlaoded.addAll(list);
//                        mAdapter.notifyDataSetChanged();
                        mListAdapter.setData(mDownlaoded);
                    }
                });
            }
        });
    }

    private void initUI() {
        mListView = mContent.findViewById(R.id.task_list);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //play
            }

        });
//        mAdapter = new DownloadAdapter();
//        mListView.setAdapter(mAdapter);
        mListAdapter = new BindingListAdapter<>(
                R.layout.item_sound_info);
        mListAdapter.setVariables(new HashMap<Integer, Object>() {{
            put(BR.mode, 1);
            put(BR.event, new BindingAdapter(mListAdapter));
        }});
        mListView.setAdapter(mListAdapter);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

    }

    private void deleteSound(final SearchSound sound) {
        if (sound == null) {
            return;
        }
        final IDBService db = ServiceManager.getService(IDBService.class);
        HMExecutor.runNow(new SafeJob() {
            @Override
            public void safeRun() {
                boolean ret = db.delete(sound);
                if (ret) {
                    File file = new File(sound.getDownload_path());
                    file.delete();
                }
                getActivity().runOnUiThread(new SafeJob() {
                    @Override
                    public void safeRun() {
                        mDownlaoded.remove(sound);
                        mListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

//    private class EventHandler extends BindingAdapter {
//
//        @Override
//        public void onClick(View view, String type) {
//            super.onClick(view, type);
//            Object tag = view.getTag();
//            if (!(tag instanceof SearchSound)) {
//                return;
//            }
//            SearchSound ss = (SearchSound) tag;
//            deleteSound(ss);
//        }
//
//        @Override
//        public int getMode() {
//            return 1;
//        }
//    }

    class DownloadAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return mDownlaoded.size();

        }

        @Override
        public Object getItem(int position) {

            return mDownlaoded.get(position);

        }

        @Override
        public long getItemId(int position) {

            return position;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            SearchSound task = mDownlaoded.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.search_result_item, null);
                holder.icon = convertView.findViewById(R.id.sound_icon);
                holder.title = convertView.findViewById(R.id.sound_title);
                holder.progressBar = convertView.findViewById(R.id.download_progress);
                holder.download = convertView.findViewById(R.id.sound_download);
                holder.download.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                holder.progressBar.setVisibility(View.VISIBLE);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(task.title);
            holder.download.setText("");
            holder.progressBar.setVisibility(View.GONE);
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

