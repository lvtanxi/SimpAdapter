package com.lvtanxi.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * Date: 2016-03-01
 * Time: 8:39
 * Description:PagerAdapter基类
 */
public abstract class SimpPagerAdapter<T> extends PagerAdapter {
    protected List<T> mData;

    private SparseArray<View> mViews;

    public SimpPagerAdapter(List<T> data) {
        mData = data;
        mViews = new SparseArray<>();
    }

    public SimpPagerAdapter() {
        this(null);
    }

    public void bindData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void bindData(T... data) {
        bindData(Arrays.asList(data));
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = newView(getItem(position));
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    public abstract View newView(T t);

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViews.get(position));
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public T getItem(int position) {
        if (mData == null)
            return null;
        return mData.get(position);
    }
}