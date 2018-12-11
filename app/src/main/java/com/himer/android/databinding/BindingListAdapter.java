package com.himer.android.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.himer.android.BR;

import java.util.List;
import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/26.
 */
public class BindingListAdapter<T> extends BaseAdapter {

    private List<T> mData;
    private int mItemLayoutId;

    private Map<Integer, Object> mVariables;

    public BindingListAdapter(int layoutId) {
        mItemLayoutId = layoutId;

    }

    public void setVariables(Map<Integer, Object> variables) {
        mVariables = variables;
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
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    mItemLayoutId, parent, false);
            if (mVariables != null) {
                for (Map.Entry<Integer, Object> entry : mVariables.entrySet()) {
                    binding.setVariable(entry.getKey(), entry.getValue());
                }
            }
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(BR.sound, getItem(position));
        return binding.getRoot();
    }

    public void remove(T item) {
        if (mData.remove(item)) {
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return mData;
    }
}
