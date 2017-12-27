package com.lvtanxi.adapter.convert;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvtanxi.adapter.SimplicityViewHolder;
import com.lvtanxi.adapter.listener.OnNoDoubleClickListener;


public class DefaultViewConvert implements IViewConvert {

    private SimplicityViewHolder viewHolder;

    public DefaultViewConvert(SimplicityViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    @Override
    public final <T extends View> T getView(int viewId) {
        return (T) viewHolder.getView(viewId);
    }

    @Override
    public TextView getTextView(int viewId) {
        return getView(viewId);
    }

    @Override
    public ImageView getImageView(int viewId) {
        return getView(viewId);
    }

    @Override
    public DefaultViewConvert setTag(int viewId, Object object) {
        getView(viewId).setTag(object);
        return this;
    }

    @Override
    public DefaultViewConvert setText(int viewId, int res) {
        TextView view = getView(viewId);
        view.setText(res);
        return this;
    }

    @Override
    public DefaultViewConvert setText(int viewId, CharSequence charSequence) {
        TextView view = getView(viewId);
        view.setText(charSequence);
        return this;
    }

    @Override
    public DefaultViewConvert setTypeface(int viewId, Typeface typeface, int style) {
        TextView view = getView(viewId);
        view.setTypeface(typeface, style);
        return this;
    }

    @Override
    public DefaultViewConvert setTypeface(int viewId, Typeface typeface) {
        TextView view = getView(viewId);
        view.setTypeface(typeface);
        return this;
    }

    @Override
    public DefaultViewConvert setTextColor(int viewId, int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }

    @Override
    public DefaultViewConvert setTextSize(int viewId, int sp) {
        TextView view = getView(viewId);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        return this;
    }

    @Override
    public DefaultViewConvert setAlpha(int viewId, float alpha) {
        View view = getView(viewId);
        view.setAlpha(alpha);
        return this;
    }

    @Override
    public DefaultViewConvert setImage(int viewId, int res) {
        ImageView view = getView(viewId);
        view.setImageResource(res);
        return this;
    }

    @Override
    public DefaultViewConvert setImageLevel(int viewId, int level) {
        ImageView view = getView(viewId);
        view.setImageLevel(level);
        return this;
    }

    @Override
    public DefaultViewConvert setImage(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @Override
    public DefaultViewConvert setBackground(int viewId, int res) {
        View view = getView(viewId);
        view.setBackgroundResource(res);
        return this;
    }

    @Override
    public DefaultViewConvert setBackground(int viewId, Drawable drawable) {
        View view = getView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        return this;
    }

    @Override
    public DefaultViewConvert visible(int viewId) {
        getView(viewId).setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public DefaultViewConvert visible(int viewId, boolean show) {
        getView(viewId).setVisibility(show ? View.VISIBLE : View.GONE);
        return this;
    }


    @Override
    public DefaultViewConvert invisible(int viewId) {
        getView(viewId).setVisibility(View.INVISIBLE);
        return this;
    }

    @Override
    public DefaultViewConvert gone(int viewId) {
        getView(viewId).setVisibility(View.GONE);
        return this;
    }

    @Override
    public DefaultViewConvert visibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    @Override
    public <V extends View> DefaultViewConvert with(int viewId, Action<V> action) {
        action.action((V) getView(viewId));
        return this;
    }

    @Override
    public DefaultViewConvert setOnClickListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    @Override
    public DefaultViewConvert setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }

    @Override
    public DefaultViewConvert setEnabled(int viewId, boolean enable) {
        getView(viewId).setEnabled(enable);
        return this;
    }

    @Override
    public DefaultViewConvert enable(int viewId) {
        getView(viewId).setEnabled(true);
        return this;
    }

    @Override
    public DefaultViewConvert disable(int viewId) {
        getView(viewId).setEnabled(false);
        return this;
    }

    @Override
    public DefaultViewConvert setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    @Override
    public DefaultViewConvert setSelected(int viewId, boolean selected) {
        getView(viewId).setSelected(selected);
        return this;
    }

    @Override
    public DefaultViewConvert setPressed(int viewId, boolean pressed) {
        getView(viewId).setPressed(pressed);
        return this;
    }

    @Override
    public DefaultViewConvert setAdapter(int viewId, RecyclerView.Adapter adapter) {
        RecyclerView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    @Override
    public DefaultViewConvert setAdapter(int viewId, Adapter adapter) {
        AdapterView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    @Override
    public DefaultViewConvert setLayoutManager(int viewId, RecyclerView.LayoutManager layoutManager) {
        RecyclerView view = getView(viewId);
        view.setLayoutManager(layoutManager);
        return this;
    }

    @Override
    public DefaultViewConvert addView(int viewId, View... views) {
        ViewGroup viewGroup = getView(viewId);
        for (View view : views) {
            viewGroup.addView(view);
        }
        return this;
    }

    @Override
    public DefaultViewConvert addView(int viewId, View view, ViewGroup.LayoutParams params) {
        ViewGroup viewGroup = getView(viewId);
        viewGroup.addView(view, params);
        return this;
    }

    @Override
    public DefaultViewConvert removeAllViews(int viewId) {
        ViewGroup viewGroup = getView(viewId);
        viewGroup.removeAllViews();
        return this;
    }

    @Override
    public DefaultViewConvert removeView(int viewId, View view) {
        ViewGroup viewGroup = getView(viewId);
        viewGroup.removeView(view);
        return this;
    }

    /**
     * 为id为viewId的item子控件设置点击事件监听器
     */
    @Override
    public DefaultViewConvert setOnItemChildClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    viewHolder.invokeOnItemChildClickListener(v);
                }
            });
        }
        return this;
    }


}
