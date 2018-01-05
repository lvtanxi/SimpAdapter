package com.lvtanxi.adapter.listener;

/**
 * Date: 2018-01-05
 * Time: 08:47
 * Description: Sectioned分组Item接口，与OnSectionedListener互斥
 */

public interface Section {
    /**
     * 获取分组子Item个数
     */
    int getSectionChildCount();

    /**
     * 获取具体某一个child对应的数据
     */
    Object getSectionChildItem(int childPosition);

}
