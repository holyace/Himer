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
import android.nfc.Tag;
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

import com.chad.android.common.concurrent.Executor;
import com.chad.android.common.concurrent.SafeJob;
import com.chad.android.common.service.ServiceManager;
import com.chad.android.common.service.shell.IDBService;
import com.himer.android.R;
import com.himer.android.download.DownloadManager;
import com.himer.android.download.DownloadTask;
import com.himer.android.download.DownloadUpdateListener;
import com.himer.android.modle.SearchSound;
import com.himer.android.util.HLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class LocalFileFragment extends Fragment {
    private static final String TAG = LocalFileFragment.class.getSimpleName();

    private Activity mContext;
    private View mContent;
    private ListView mListView;
    private DownloadAdapter mAdapter;

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
    }

    private void initData() {
        Log.e(TAG, "Xm initData");
        Executor.runNow(new SafeJob() {
            @Override
            public void safeRun() {
                IDBService db = ServiceManager.getService(IDBService.class);
                List<SearchSound> list = db.queryAll(SearchSound.class);
                if (list == null || list.isEmpty()) {
                    HLog.e(TAG, "initData get empty result");
                    return;
                }
                mDownlaoded.clear();
                mDownlaoded.addAll(list);
                getActivity().runOnUiThread(new SafeJob() {
                    @Override
                    public void safeRun() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initUI() {
        mListView = (ListView) mContent.findViewById(R.id.task_list);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //play
            }

        });
        mAdapter = new DownloadAdapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

    }

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

