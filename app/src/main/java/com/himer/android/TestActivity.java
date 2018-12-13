package com.himer.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.himer.android.common.concurrent.HMExecutor;
import com.himer.android.common.concurrent.SafeJob;
import com.himer.android.downloader.DownloadState;
import com.himer.android.downloader.DownloadWorker;
import com.himer.android.downloader.IDownloadListener;
import com.himer.android.downloader.IDownloadTask;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/12.
 */
public class TestActivity extends Activity {

    TextView mLog;
    float mPercent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ScrollView root = new ScrollView(this);
        root.setLayoutParams(lp);

        mLog = new TextView(this);
        lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        root.addView(mLog, lp);

        IDownloadListener listener = new IDownloadListener() {
            @Override
            public void onDownloadStart(IDownloadTask task) {
                printLog("onDownloadStart");
            }

            @Override
            public void onDownloadPause(IDownloadTask task) {
                printLog("onDownloadPause");
            }

            @Override
            public void onProgressUpdate(IDownloadTask task, float percent) {
                if (percent - mPercent >= 0.01f) {
                    mPercent = percent;
                    printLog(String.format("onProgressUpdate %.2f", percent));
                }
            }

            @Override
            public void onDownloadSucess(IDownloadTask task) {
                printLog("onDownloadSucess");
            }

            @Override
            public void onDownloadFail(IDownloadTask task, String errorCode, String errorMessage) {
                printLog("onDownloadFail|" + errorCode + "|" + errorMessage);
            }

            @Override
            public void onTaskCancel(IDownloadTask task) {

            }

            @Override
            public boolean onRedownload(IDownloadTask task) {
                printLog("onRedownload");
                return false;
            }
        };

        final DownloadWorker worker = new DownloadWorker(listener);

        IDownloadTask task = new IDownloadTask() {
            @Override
            public String getDownloadUrl() {
                return "https://srv83.clipconverter.cc/download/1d2TgKaenquwZW9xmZiZa3Fi5KWmqXFr4pSYbG1mmmVqZWq0qc%2FMqHyf1qiZpa2d2A%3D%3D/Aliz%C3%A9e%20-%20La%20Isla%20Bonita.mp4";
            }

            @Override
            public String getTitle() {
                return "测试下载程序";
            }

            @Override
            public String getFileName() {
                return "测试下载程序.mp4";
            }

            @Override
            public String getSavePath() {
                return null;
            }

            @Override
            public DownloadState getDownloadState() {
                return null;
            }

            @Override
            public void setDownloadState(DownloadState state) {

            }
        };

        worker.setDefaultPath(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).
                getAbsolutePath());
        worker.setDownloadTask(task);

        setContentView(root);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HMExecutor.runNow(new SafeJob() {
                    @Override
                    public void safeRun() {
                        worker.run();
                    }
                });
            }
        });
    }

    private void printLog(final String log) {
        HMExecutor.runUiThread(new SafeJob() {
            @Override
            public void safeRun() {
                mLog.append("\n");
                mLog.append(log);
            }
        });
    }
}
