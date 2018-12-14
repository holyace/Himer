package com.himer.android.util;

import android.os.Process;
import android.text.TextUtils;
import android.util.SparseArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/14.
 */
public class AppUtil {

    public static final String MAIN_PROCESS = "com.himer.android";

    private static SparseArray<String> sProcessCache = new SparseArray<>(3);

    public static boolean isMainProcess() {
        return TextUtils.equals(MAIN_PROCESS, getProcessName(Process.myPid()));
    }

    public static String currentProcessName() {
        int pid = Process.myPid();
        String processName = sProcessCache.get(pid);
        if (!TextUtils.isEmpty(processName)) {
            return processName;
        }
        else {
            processName = getProcessName(pid);
            sProcessCache.put(pid, processName);
        }
        return processName;
    }

    public static String getProcessName(int pid) {
        try {
            File file = new File("/proc/" + pid + "/cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
