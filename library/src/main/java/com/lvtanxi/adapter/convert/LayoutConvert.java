package com.lvtanxi.adapter.convert;

/**
 * 处理只加载布局的形式
 */
public class LayoutConvert implements SimplicityConvert<Object> {
    private Class<?> mTagetClass;

    public LayoutConvert(Class<?> tagetClass) {
        this.mTagetClass = tagetClass;
    }

    @Override
    public void convert(ViewConvert convert, Object t, int position) {

    }

    public Class<?> getTagetClass() {
        return mTagetClass;
    }
}
