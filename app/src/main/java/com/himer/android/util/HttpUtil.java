/**
 * HttpUtil.java
 * com.ximalaya.downloader.net
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

import android.util.Log;

import com.himer.android.common.util.HLog;
import com.himer.android.net.HttpCallback;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

/**
 * ClassName:HttpUtil
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author chadwii
 * @Date 2014-2-19		上午10:55:14
 * @since Ver 1.1
 */
public class HttpUtil {

    private static final String TAG = HttpUtil.class.getSimpleName();

    private static HttpUtil sUtil;
    private static byte[] sSyncLock = new byte[0];

    private AsyncHttpClient mHttpClient;

    public static HttpUtil getInstance() {
        if (sUtil == null) {
            synchronized (sSyncLock) {
                if (sUtil == null) {
                    sUtil = new HttpUtil();
                }
            }
        }
        return sUtil;
    }

    private HttpUtil() {
        setup();
    }

    private void setup() {
        mHttpClient = new AsyncHttpClient();
        setHttpHeader();
    }

    private void setHttpHeader() {
        if (mHttpClient == null) {
            return;
        }
//        mHttpClient.setProxy("30.7.24.243", 8888);
        mHttpClient.setUserAgent(null);
        mHttpClient.addHeader("user_agent", getUserAgent());
        mHttpClient.addHeader("Accept", "*/*");
//		client.addHeader("sdk_version", AppConstants.SDK_VERSION);
//		client.addHeader("device", device);
//		client.addHeader("device_id", SerialInfo.getDeviceId(Ximalaya.getContext()));
//		client.addHeader("open_app_key", Ximalaya.getInstance().getAppKey());
    }

    public static String getUserAgent() {

        return null;
    }

    public void httpGet(String url, RequestParams param, HttpCallback callback) {
        HLog.e(TAG, "Xm request get " + url);
        mHttpClient.get(url, param, callback);
    }

    public void httpPost(String url, RequestParams params, HttpCallback callback) {
        HLog.e(TAG, "Xm request post " + url);
        mHttpClient.post(url, params, callback);
    }
}

