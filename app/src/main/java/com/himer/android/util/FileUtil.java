/**
 * FileUtil.java
 * com.ximalaya.downloader.file
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2014-2-19 		chadwii
 * <p>
 * Copyright (c) 2014, TNT All Rights Reserved.
 */

package com.himer.android.util;

import android.os.Environment;

import java.io.File;

/**
 * ClassName:FileUtil
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author chadwii
 * @version
 * @since Ver 1.1
 * @Date 2014-2-19		下午3:51:27
 *
 */
public class FileUtil {

    public static boolean isFileSystemCanUse() {
        if (Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPrename(File file) {
        if (file == null) {
            return "";
        }
        String name = file.getName();
        if (name.contains(".")) {
            return name.substring(0, name.lastIndexOf("."));
        }
        return name;
    }

    public static String getExtensionName(File file) {
        if (file == null) {
            return "";
        }
        String name = file.getName();
        if (name.contains(".")) {
            return name.substring(name.lastIndexOf("."));
        }
        return "";
    }

    public static String getExtensionName(String url) {
        if (url == null) {
            return "";
        }
        if (url.contains(".")) {
            return url.substring(url.lastIndexOf("."));
        }
        return "";
    }

    public static boolean renameFile(File src, File dest) {
        if (src == null || dest == null) {
            return false;
        }
        File parent = dest.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        if (dest.exists()) {
            dest = new File(dest.getParent(),
                    getPrename(dest) +
                            System.currentTimeMillis() +
                            getExtensionName(dest));
        }
        return src.renameTo(dest);
    }

    public static boolean makeDir(String path) {
        if (path == null || "".equals(path)) {
            return false;
        }
        File dir = new File(path);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        if (dir.exists() && dir.isFile()) {
            return false;
        }
        return true;
    }
}

