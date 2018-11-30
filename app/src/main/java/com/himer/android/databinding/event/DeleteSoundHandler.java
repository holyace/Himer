package com.himer.android.databinding.event;

import android.text.TextUtils;
import android.view.View;

import com.himer.android.common.concurrent.HMExecutor;
import com.himer.android.common.concurrent.SafeJob;
import com.himer.android.common.service.ServiceManager;
import com.himer.android.common.service.shell.IDBService;
import com.himer.android.common.util.HLog;
import com.himer.android.databinding.BindingListAdapter;
import com.himer.android.databinding.IEventHandler;
import com.himer.android.modle.SearchSound;

import java.io.File;
import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class DeleteSoundHandler implements
        IEventHandler<BindingListAdapter<SearchSound>> {

    private static final String TAG = DeleteSoundHandler.class.getSimpleName();

    @Override
    public void handleEvent(final BindingListAdapter<SearchSound> context,
                            View view, final Map<String, Object> param) {

        HMExecutor.runNow(new SafeJob() {
            @Override
            public void safeRun() {
                Object obj = param.get("sound");
                if (!(obj instanceof SearchSound)) {
                    return;
                }
                final SearchSound sound = (SearchSound) obj;
                if (TextUtils.isEmpty(sound.getDownload_path())) {
                    return;
                }
                File file = new File(sound.getDownload_path());
                if (!file.exists()) {
                    HLog.e(TAG, "delete file not exists", sound.getTitle(), sound.getDownload_path());
                    return;
                }
                if (!file.delete()) {
                    HLog.e(TAG, "delete file failed", sound.getTitle(), sound.getDownload_path());
                    return;
                }
                IDBService db = ServiceManager.getService(IDBService.class);
                db.delete(sound);

                HMExecutor.runUiThread(new SafeJob() {
                    @Override
                    public void safeRun() {
                        context.remove(sound);
                    }
                });
            }
        });
    }
}
