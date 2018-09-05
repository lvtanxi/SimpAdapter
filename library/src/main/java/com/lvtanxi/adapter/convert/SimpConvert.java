package com.lvtanxi.adapter.convert;

public interface SimpConvert<T> {
    void convert(ViewConvert convert, T t, int position);
}
