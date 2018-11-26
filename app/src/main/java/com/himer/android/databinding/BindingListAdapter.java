package com.himer.android.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.himer.android.BR;

import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/26.
 */
public class BindingListAdapter<T> extends BaseAdapter {

    private List<T> mData;
    private int mItemLayoutId;
    private int mVariableId;
    private BindingAdapter mEventHandler;
    private int mEventId;

    public BindingListAdapter(BindingAdapter eventHandler, int evnetId, int layoutId, int variableId) {
        mItemLayoutId = layoutId;
        mVariableId = variableId;
        mEventHandler = eventHandler;
        mEventId = evnetId;
    }

    public void setData(List<T> list) {
        mData = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        if (mData == null || position < 0 || position > mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding binding;
        if (convertView == null) {
            binding =  DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    mItemLayoutId, parent, false);
            if (mEventHandler != null) {
                binding.setVariable(BR.mode, mEventHandler.getMode());
                binding.setVariable(mEventId, mEventHandler);
            }
        }
        else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(mVariableId, getItem(position));
        return binding.getRoot();
    }
}
