package com.himer.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.himer.android.download.DownloadManager;
import com.himer.android.fragment.DownloadFragment;
import com.himer.android.fragment.SearchFragment;

public class MainTabActivity extends FragmentActivity 
{

	public static final String TAB_A = "TAB_A";
	public static final String TAB_B = "TAB_B";
	
	private Activity mContext;
	private TabHost mTabHost;
	private FragmentManager mFragmentManager;
	private Fragment mSearchFragment;
	private Fragment mDownloadFragment;
	private Fragment mCurrentFragment;
	private int mContentId = R.id.real_tab_content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tab);
		mContext = this;
		mFragmentManager = getSupportFragmentManager();
		initTab();
	}

	private void initTab()
	{
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tag) {
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				Fragment f = getFragment(tag);
				if (f != null)
				{
					if (mCurrentFragment != f)
					{
						if (mCurrentFragment != null)
						{
							ft.hide(mCurrentFragment);
						}
						if (f.isAdded())
						{
							ft.show(f);
						}
						else
						{
							ft.add(mContentId, f);
						}
						mCurrentFragment = f;
					}
				}
				ft.commitAllowingStateLoss();
			}
		});
		addTab(TAB_A, "搜索声音", R.drawable.search);
		addTab(TAB_B, "下载详情", R.drawable.download_detail);
		setCurrentTab(TAB_A);
	}
	
	public void setCurrentTab(String tag)
	{
		mTabHost.setCurrentTabByTag(tag);
	}
	
	private Fragment getFragment(String tag)
	{
		if (TAB_A.equals(tag))
		{
			if (mSearchFragment == null)
			{
				mSearchFragment = new SearchFragment();
			}
			return mSearchFragment;
		}
		if (TAB_B.equals(tag))
		{
			if (mDownloadFragment == null)
			{
				mDownloadFragment = new DownloadFragment();
			}
			return mDownloadFragment;
		}
		return null;
	}
	
	private void addTab(String tag, String title, int iconId)
	{
		TabHost.TabSpec spec = mTabHost.newTabSpec(tag);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(mContentId);
			}
		});
		spec.setIndicator(createTabView(tag, title, iconId));
		mTabHost.addTab(spec);
	}
	
	private View createTabView(String tag, String title, int iconId)
	{
		View tab = View.inflate(mContext, R.layout.tab_indicator, null);
		TextView tv = (TextView) tab.findViewById(R.id.tab_indicator);
		tv.setCompoundDrawablesWithIntrinsicBounds(0, iconId, 0, 0);
		tv.setText(title);
		return tab;
	}
	
	@Override
	public void finish()
	{
		Log.e("", "Xm act finish");
		DownloadManager.getInstance().exit();
		mDownloadFragment = null;
		mSearchFragment = null;
		mCurrentFragment = null;
		super.finish();
		
	}
	
	@Override
	protected void onDestroy() 
	{
		Log.e("", "Xm act destroy");
		super.onDestroy();
		
	}
}
