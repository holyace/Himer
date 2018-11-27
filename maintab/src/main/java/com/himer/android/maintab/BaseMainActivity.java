package com.himer.android.maintab;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.himer.android.common.BaseActivity;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/26.
 */
public abstract class BaseMainActivity extends BaseActivity {

    private static final int REQ_CODE = 0x0001;
    private Bundle mSavedInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSavedInstance = savedInstanceState;
            checkPermission(getCheckPermissions());
        }
        else {
            delayOnCreate(savedInstanceState);
        }
    }

    private void checkPermission(String[] permissions) {
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.
                    checkSelfPermission(mActivity, permission)) {
                ActivityCompat.requestPermissions(mActivity,
                        permissions, REQ_CODE);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQ_CODE) {
            return;
        }
        for (int i = 0; i < permissions.length; i++) {
            int ret = grantResults[i];
            if (PackageManager.PERMISSION_GRANTED != ret) {
                if (!onPermissionDeny(permissions[i])) {
                    finish();
                    return;
                }
            }
        }
        delayOnCreate(mSavedInstance);
        mSavedInstance = null;
    }

    /**
     *
     * @param permission
     * @return true the app go on when permission deny, false otherwise
     */
    public boolean onPermissionDeny(String permission) {

        return false;
    }

    public abstract void delayOnCreate(Bundle savedInstance);

    public abstract String[] getCheckPermissions();
}
