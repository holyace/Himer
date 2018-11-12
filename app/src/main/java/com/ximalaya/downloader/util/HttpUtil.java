/**
 * HttpUtil.java
 * com.ximalaya.downloader.net
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014-2-19 		chadwii
 *
 * Copyright (c) 2014, TNT All Rights Reserved.
*/

package com.ximalaya.downloader.util;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.ximalaya.downloader.net.HttpCallback;

/**
 * ClassName:HttpUtil
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   chadwii
 * @version  
 * @since    Ver 1.1
 * @Date	 2014-2-19		上午10:55:14
 *
 */
public class HttpUtil 
{
	
	private static HttpUtil sUtil;
	private static byte[] sSyncLock = new byte[0];
	
	private AsyncHttpClient mHttpClient;
	
	public static HttpUtil getInstance()
	{
		if (sUtil == null)
		{
			synchronized (sSyncLock) 
			{
				if (sUtil == null)
				{
					sUtil = new HttpUtil();
				}
			}
		}
		return sUtil;
	}
	
	private HttpUtil()
	{
		setup();
	}
	
	private void setup()
	{
		mHttpClient = new AsyncHttpClient();
		setHttpHeader();
	}
	
	private void setHttpHeader()
	{
		if (mHttpClient == null)
		{
			return;
		}
		mHttpClient.setUserAgent(null);
		mHttpClient.addHeader("user_agent", getUserAgent());
		mHttpClient.addHeader("Accept", "*/*");
//		client.addHeader("sdk_version", AppConstants.SDK_VERSION);
//		client.addHeader("device", device);
//		client.addHeader("device_id", SerialInfo.getDeviceId(Ximalaya.getContext()));
//		client.addHeader("open_app_key", Ximalaya.getInstance().getAppKey());
	}
	
	public static String getUserAgent()
	{
		
		return null;
	}
	
	public void httpGet(String url, RequestParams param, HttpCallback callback)
	{
		Log.e("", "Xm request get " + url);
		mHttpClient.get(url, param, callback);
	}
	
	public void httpPost(String url, RequestParams params, HttpCallback callback)
	{
		Log.e("", "Xm request post " + url);
		mHttpClient.post(url, params, callback);
	}
}

