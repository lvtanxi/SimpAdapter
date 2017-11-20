package com.lvtanxi.adapter.listener;

import android.view.ViewGroup;

import com.lvtanxi.adapter.SimplicityViewHolder;

public interface IViewHolderCreator<T> {
    SimplicityViewHolder<T> create(ViewGroup parent);
}