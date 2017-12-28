package com.lvtanxi.adapter;

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
public abstract class SimplicityPagerAdapter<T> extends PagerAdapter {
    protected List<T> mData;

    private SparseArray<View> mViews;

    public SimplicityPagerAdapter(List<T> data) {
        mData = data;
        mViews = new SparseArray<>();
    }

    public SimplicityPagerAdapter() {
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
        return mData==null ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
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
    public void destroyItem(ViewGroup container, int position, Object object) {
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