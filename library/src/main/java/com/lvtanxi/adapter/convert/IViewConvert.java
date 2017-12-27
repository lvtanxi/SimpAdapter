package com.lvtanxi.adapter.convert;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;


public interface IViewConvert {

    <T extends View> T getView(int viewId);

    TextView getTextView(int viewId);

    ImageView getImageView(int viewId);

    IViewConvert setTag(int viewId, Object object);

    IViewConvert setText(int viewId, int res);

    IViewConvert setText(int viewId, CharSequence charSequence);

    IViewConvert setTypeface(int viewId, Typeface typeface, int style);

    IViewConvert setTypeface(int viewId, Typeface typeface);

    IViewConvert setTextColor(int viewId, int color);

    IViewConvert setTextSize(int viewId, int sp);

    IViewConvert  setAlpha(int viewId, float alpha);

    IViewConvert  setImage(int viewId, int res);

    IViewConvert  setImageLevel(int viewId, int level);

    IViewConvert setImage(int viewId, Drawable drawable);

    IViewConvert setBackground(int viewId, int res);

    IViewConvert setBackground(int viewId, Drawable drawable);

    IViewConvert visible(int viewId);

    IViewConvert visible(int viewId,boolean show);

    IViewConvert invisible(int viewId);

    IViewConvert gone(int viewId);

    IViewConvert visibility(int viewId, int visibility);

    <V extends View> IViewConvert with(int viewId, Action<V> action);

    IViewConvert setOnClickListener(int viewId, View.OnClickListener listener);

    IViewConvert setOnLongClickListener(int viewId, View.OnLongClickListener listener);

    IViewConvert setOnItemChildClickListener(int viewId);

    IViewConvert setEnabled(int viewId, boolean enable);

    IViewConvert enable(int viewId);

    IViewConvert disable(int viewId);

    IViewConvert setChecked(int viewId, boolean checked);

    IViewConvert setSelected(int viewId, boolean selected);

    IViewConvert setPressed(int viewId, boolean pressed);

    IViewConvert setAdapter(int viewId, RecyclerView.Adapter adapter);

    IViewConvert setAdapter(int viewId, Adapter adapter);

    IViewConvert setLayoutManager(int viewId, RecyclerView.LayoutManager layoutManager);

    IViewConvert addView(int viewId, View... views);

    IViewConvert addView(int viewId, View view, ViewGroup.LayoutParams params);

    IViewConvert removeAllViews(int viewId);

    IViewConvert removeView(int viewId, View view);

    interface Action<V extends View> {
        void action(V v);
    }
}
