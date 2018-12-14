package com.himer.android;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.himer.android.common.util.HLog;
import com.himer.android.download.DownloadManager;
import com.himer.android.fragment.DownloadFragment;
import com.himer.android.fragment.LocalFileFragment;
import com.himer.android.fragment.SearchFragment;

public class MainTabActivity extends AppCompatActivity {

    private static final String TAG = MainTabActivity.class.getSimpleName();

    private static final int REQUEST_CODE = 0x11;

    private static final String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};

    public static final String TAB_A = "TAB_A";
    public static final String TAB_B = "TAB_B";
    public static final String TAB_C = "TAB_C";

    private Activity mContext;
    private TabHost mTabHost;
    private FragmentManager mFragmentManager;
    private Fragment mSearchFragment;
    private Fragment mDownloadFragment;
    private Fragment mLocalFileFragment;
    private Fragment mCurrentFragment;
    private int mContentId = android.R.id.tabcontent;
    private View mContentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        mContext = this;

        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);

        mFragmentManager = getSupportFragmentManager();
        initTab();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void initTab() {
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

            @Override
            public void onTabChanged(String tag) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                Fragment f = getFragment(tag);
                if (f != null) {
                    if (mCurrentFragment != f) {
                        if (mCurrentFragment != null) {
                            ft.hide(mCurrentFragment);
                        }
                        if (f.isAdded()) {
                            ft.show(f);
                        } else {
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
        addTab(TAB_C, "已下载", R.drawable.file);
        setCurrentTab(TAB_A);
    }

    public void setCurrentTab(String tag) {
        mTabHost.setCurrentTabByTag(tag);
    }

    private Fragment getFragment(String tag) {
        if (TAB_A.equals(tag)) {
            if (mSearchFragment == null) {
                mSearchFragment = new SearchFragment();
            }
            return mSearchFragment;
        }
        if (TAB_B.equals(tag)) {
            if (mDownloadFragment == null) {
                mDownloadFragment = new DownloadFragment();
            }
            return mDownloadFragment;
        }
        if (TAB_C.equals(tag)) {
            if (mLocalFileFragment == null) {
                mLocalFileFragment = new LocalFileFragment();
            }
            return mLocalFileFragment;
        }
        return null;
    }

    private void addTab(String tag, String title, int iconId) {
        TabHost.TabSpec spec = mTabHost.newTabSpec(tag);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                if (mContentContainer == null) {
                    mContentContainer = findViewById(mContentId);
                }
                return mContentContainer;
            }
        });
        spec.setIndicator(createTabView(tag, title, iconId));
        mTabHost.addTab(spec);
    }

    private View createTabView(String tag, String title, int iconId) {
        View tab = View.inflate(mContext, R.layout.tab_indicator, null);
        ImageView icon = tab.findViewById(R.id.tab_icon);
        TextView tv = tab.findViewById(R.id.tab_text);
        tv.setText(title);
        icon.setImageResource(iconId);
        tab.setTag(tag);
        return tab;
    }

    @Override
    public void finish() {
        HLog.e(TAG, "Xm act finish");
        DownloadManager.getInstance().exit();
        mDownloadFragment = null;
        mSearchFragment = null;
        mCurrentFragment = null;
        super.finish();

    }

    @Override
    protected void onDestroy() {
        HLog.e(TAG, "Xm act destroy");
        super.onDestroy();

    }
}
