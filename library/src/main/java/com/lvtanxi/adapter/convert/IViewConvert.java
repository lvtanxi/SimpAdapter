package com.lvtanxi.adapter.convert;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;


public interface IViewConvert<VI extends IViewConvert> {

    <T extends View> T findViewById(int viewId);

    VI setTag(int viewId, Object object);

    VI setText(int viewId, int res);

    VI setText(int viewId, CharSequence charSequence);

    VI setTypeface(int viewId, Typeface typeface, int style);

    VI setTypeface(int viewId, Typeface typeface);

    VI setTextColor(int viewId, int color);

    VI setTextSize(int viewId, int sp);

    VI  setAlpha(int viewId, float alpha);

    VI  setImage(int viewId, int res);

    VI setImage(int viewId, Drawable drawable);

    VI setBackground(int viewId, int res);

    VI setBackground(int viewId, Drawable drawable);

    VI visible(int viewId);

    VI visible(int viewId,boolean show);

    VI invisible(int viewId);

    VI gone(int viewId);

    VI visibility(int viewId, int visibility);

    <V extends View> VI with(int viewId, Action<V> action);

    VI setOnClickListener(int viewId, View.OnClickListener listener);

    VI setOnLongClickListener(int viewId, View.OnLongClickListener listener);

    VI setOnItemChildClickListener(int viewId);

    VI setEnabled(int viewId, boolean enable);

    VI enable(int viewId);

    VI disable(int viewId);

    VI setChecked(int viewId, boolean checked);

    VI setSelected(int viewId, boolean selected);

    VI setPressed(int viewId, boolean pressed);

    VI setAdapter(int viewId, RecyclerView.Adapter adapter);

    VI setAdapter(int viewId, Adapter adapter);

    VI setLayoutManager(int viewId, RecyclerView.LayoutManager layoutManager);

    ///COMMON VIEWGROUP
    VI addView(int viewId, View... views);

    VI addView(int viewId, View view, ViewGroup.LayoutParams params);

    VI removeAllViews(int viewId);

    VI removeView(int viewId, View view);

    interface Action<V extends View> {
        void action(V v);
    }
}
