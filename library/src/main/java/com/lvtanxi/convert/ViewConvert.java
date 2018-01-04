package com.lvtanxi.convert;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Date: 2017-12-29
 * Time: 15:34
 * Description: RecyclerView.ViewHolder 实体
 */
public interface ViewConvert {

    <T extends View> T getView(int viewId);

    TextView getTextView(int viewId);

    ImageView getImageView(int viewId);

    ViewConvert setTag(int viewId, Object object);

    ViewConvert setText(int viewId, int res);

    ViewConvert setText(int viewId, CharSequence charSequence);

    ViewConvert setTypeface(int viewId, Typeface typeface, int style);

    ViewConvert setTypeface(int viewId, Typeface typeface);

    ViewConvert setTextColor(int viewId, int color);

    ViewConvert setTextSize(int viewId, int sp);

    ViewConvert setAlpha(int viewId, float alpha);

    ViewConvert setImage(int viewId, int res);

    ViewConvert setImageLevel(int viewId, int level);

    ViewConvert setImage(int viewId, Drawable drawable);

    ViewConvert setBackground(int viewId, int res);

    ViewConvert setBackground(int viewId, Drawable drawable);

    ViewConvert visible(int viewId);

    ViewConvert visible(int viewId, boolean show);

    ViewConvert invisible(int viewId);

    ViewConvert gone(int viewId);

    ViewConvert visibility(int viewId, int visibility);

    <V extends View> ViewConvert with(int viewId, Action<V> action);

    ViewConvert setOnClickListener(int viewId, View.OnClickListener listener);

    ViewConvert setOnLongClickListener(int viewId, View.OnLongClickListener listener);

    ViewConvert setOnItemChildClickListener(int viewId);

    ViewConvert setEnabled(int viewId, boolean enable);

    ViewConvert enable(int viewId);

    ViewConvert disable(int viewId);

    ViewConvert setChecked(int viewId, boolean checked);

    ViewConvert setSelected(int viewId, boolean selected);

    ViewConvert setPressed(int viewId, boolean pressed);

    ViewConvert setAdapter(int viewId, RecyclerView.Adapter adapter);

    ViewConvert setAdapter(int viewId, Adapter adapter);

    ViewConvert setLayoutManager(int viewId, RecyclerView.LayoutManager layoutManager);

    ViewConvert addView(int viewId, View... views);

    ViewConvert addView(int viewId, View view, ViewGroup.LayoutParams params);

    ViewConvert removeAllViews(int viewId);

    ViewConvert removeView(int viewId, View view);

    interface Action<V extends View> {
        void action(V v);
    }
}
