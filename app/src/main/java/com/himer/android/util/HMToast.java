package com.himer.android.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.himer.android.Global;
import com.himer.android.common.concurrent.HMExecutor;
import com.himer.android.common.concurrent.SafeJob;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class HMToast {

    public static void show(Context context, final String msg) {

        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (context == null) {
            context = Global.getApplication();
        }

        final Context ctx = context;

        HMExecutor.runUiThread(new SafeJob() {
            @Override
            public void safeRun() {

                if (!UIUtils.isAlive(ctx)) {
                    return;
                }

                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
            }
        });

    }
}
