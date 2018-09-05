package com.lvtanxi.adapter.listener;

import android.view.ViewGroup;

import com.lvtanxi.adapter.holder.SimpViewHolder;

/**
 * Date: 2017-12-08
 * Time: 15:34
 * Description: ViewHolder的创建接口(算是代理)
 */
public interface ViewHolderCreator<T> {
    /**
     *创建SimplicityViewHolder
     * @param parent 父类控件
     */
    SimpViewHolder<T> create(ViewGroup parent);
}