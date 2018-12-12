package com.himer.android.downloader;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.himer.android.downloader.DownloadError.ERR_ILLEGALL_INFO;
import static com.himer.android.downloader.DownloadError.ERR_IO_CREATE_FILE;
import static com.himer.android.downloader.DownloadError.ERR_IO_DELETE_FILE;
import static com.himer.android.downloader.DownloadError.ERR_IO_MKDIR;
import static com.himer.android.downloader.DownloadError.ERR_IO_RENAME_FILE;
import static com.himer.android.downloader.DownloadError.ERR_NET;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/7.
 */
public class DownloadWorker implements Runnable {

    private static final String TEMP_SUBFIX = ".tmp";

    protected IDownloadListener mDownloadListener;
    protected IDownloadTask mDownloadTask;

    private volatile boolean mCancel;

    private File mDownloadDir;
    private File mTempFile;
    private File mDownloadFile;
    private long mStartPos;
    private long mCurrentPos;

    private String mDefaultPath;

    public DownloadWorker(IDownloadListener listener) {
        mDownloadListener = listener;
    }

    public void setDefaultPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            mDefaultPath = path;
        }
    }

    public void setDownloadTask(IDownloadTask task) {
        mDownloadTask = task;
    }

    @Override
    public void run() {

        try {

            handleTaskStart();

            if (isCancel()) {
                handleTaskCancel();
                return;
            }

            if (!initTask()) {
                return;
            }

            if (isDownloaded() && !isRedownload()) {
                handleTaskComplete();
                return;
            }

            if (!beforeDownload()) {
                return;
            }

            if (isCancel()) {
                handleTaskCancel();
                return;
            }

            if (!download()) {
                return;
            }

            if (isCancel()) {
                handleTaskCancel();
                return;
            }

            if (!afterDownload()) {
                return;
            }

            handleTaskComplete();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            destoryTask();
        }
    }

    private boolean checkDownloadTask() {
        return mDownloadTask != null &&
                !TextUtils.isEmpty(mDownloadTask.getDownloadUrl());
    }

    public boolean isCancel() {
        return mCancel;
    }

    public void cancel() {
        mCancel = true;
    }

    protected boolean initTask() {
        if (!checkDownloadTask()) {
            handleTaskFaile(ERR_ILLEGALL_INFO);
            return false;
        }

        String dir = mDownloadTask.getSavePath();
        if (TextUtils.isEmpty(dir)) {
            dir = getDefaultPath();
        }

        mDownloadDir = new File(dir);

        if (!mDownloadDir.exists() && !mDownloadDir.mkdirs()) {
            handleTaskFaile(ERR_IO_MKDIR);
            return false;
        }

        mDownloadFile = new File(mDownloadDir, mDownloadTask.getFileName());

        return true;
    }

    protected boolean beforeDownload() {

        if (mDownloadFile.exists() && !mDownloadFile.delete()) {
            handleTaskFaile(ERR_IO_DELETE_FILE);
            return false;
        }

        mTempFile = new File(mDownloadDir, mDownloadTask.getFileName() + TEMP_SUBFIX);
        if (mTempFile.exists()) {
            mStartPos = mTempFile.length();
            mCurrentPos = mStartPos;
        }
        else {
            try {
                if (!mTempFile.createNewFile()) {
                    handleTaskFaile(ERR_IO_CREATE_FILE);
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                handleTaskFaile(ERR_IO_CREATE_FILE);
                return false;
            }
        }

        return true;
    }

    protected boolean download() {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(mDownloadTask.getDownloadUrl());
        if (mStartPos > 0) {
            builder.addHeader("Range", String.format("bytes=%d-", mStartPos));
        }
        builder.method("GET", null);
        Request request = builder.build();
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isCancel()) {
            call.cancel();
            handleTaskCancel();
            return false;
        }

        if (response == null || response.body() == null) {
            handleTaskFaile(ERR_NET);
            return false;
        }

        if (!handleResponseCode(response.code())) {
            return false;
        }

        RandomAccessFile writeFile;
        try {
            writeFile = new RandomAccessFile(mTempFile, "rw");
            if (mStartPos > 0) {
                writeFile.seek(mStartPos);
            }
        } catch (IOException e) {
            e.printStackTrace();
            handleTaskFaile(ERR_IO_CREATE_FILE);
            return false;
        }

        ResponseBody body = response.body();
        long mFileLength = mStartPos + body.contentLength();
        InputStream is = body.byteStream();
        byte[] buff = new byte[8 * 1024];
        int read;
        try {
            while ((read = is.read(buff)) > 0) {
                writeFile.write(buff, 0, read);
                mCurrentPos += read;

                handleTaskProgress(mCurrentPos, mFileLength);

                if (isCancel()) {
                    call.cancel();
                    handleTaskCancel();
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            handleTaskFaile(ERR_NET);
            return false;
        }
        finally {
            if (writeFile != null) {
                try {
                    writeFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (mCurrentPos != mFileLength) {
            handleTaskFaile(ERR_NET);
            return false;
        }

        return true;
    }

    protected boolean afterDownload() {

        if (!mTempFile.renameTo(mDownloadFile)) {
            handleTaskFaile(ERR_IO_RENAME_FILE);
            return false;
        }

        return true;
    }

    protected void destoryTask() {
        mDownloadTask = null;
        mCancel = false;

        mDownloadDir = null;
        mTempFile = null;
        mDownloadFile = null;
    }

    protected String getDefaultPath() {
        return mDefaultPath;
    }

    protected boolean isDownloaded() {
        return mDownloadFile.exists();
    }

    protected boolean isRedownload() {
        return false;
    }

    private void handleTaskStart() {
        mDownloadListener.onDownloadStart(mDownloadTask);
    }

    private void handleTaskCancel() {
        mDownloadListener.onTaskCancel(mDownloadTask);
    }

    private void handleTaskFaile(DownloadError error) {
        mDownloadListener.onDownloadFail(mDownloadTask, error.getErrorCode(), error.getErrorMessage());
    }

    private boolean handleResponseCode(int responseCode) {
        if (mStartPos > 0) {
            if (206 != responseCode) {
                handleTaskFaile(ERR_NET);
                return false;
            }
            return true;
        }

        if (200 != responseCode) {
            handleTaskFaile(ERR_NET);
            return false;
        }

        return true;
    }

    private void handleTaskProgress(long current, long total) {
        float percent;
        if (current >= total) {
            percent = 1;
        }
        else {
            percent = current / (float) total;
        }
        mDownloadListener.onProgressUpdate(mDownloadTask, percent);
    }

    private void handleTaskComplete() {
        mDownloadListener.onDownloadSucess(mDownloadTask);
    }
}
