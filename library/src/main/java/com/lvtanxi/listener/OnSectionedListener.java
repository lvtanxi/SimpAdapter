package com.lvtanxi.listener;

/**
 * Date: 2018-01-04
 * Time: 15:45
 * Description: 获取每个Sectioned的个数
 */

public interface OnSectionedListener<T> {
    /**
     * 获取每个条目有多少个子条目
     */
    int getSectionedChildCount(T t,int sectioned);

    /**
     * 获取某个条目下的某一个子条目
     */
    Object getSectionedChildItem(T t,int sectioned,int position);

}
