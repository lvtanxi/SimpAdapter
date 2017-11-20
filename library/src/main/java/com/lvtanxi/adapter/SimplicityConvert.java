package com.lvtanxi.adapter;


import com.lvtanxi.adapter.convert.IViewConvert;

public interface SimplicityConvert<T> {
    void convert(IViewConvert convert, T t, int position);
}
