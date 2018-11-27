package com.himer.android.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.himer.android.common.widget.HMImageView;
import com.himer.android.R;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/26.
 */
public class SoundItemViewHolder extends BaseItemViewHolder {

    private HMImageView mIcon;
    private TextView mTitle;
    private View mDownload;
    private HMImageView mLogo;
    private TextView mAlbum;
    private TextView mNick;

    @Override
    public View createView(Context context, ViewGroup parent) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.item_sound_info, parent, false);



        return view;
    }

    @Override
    public void bindData(Map<String, String> data) {

    }
}
