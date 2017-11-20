package com.lvtanxi.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lvtanxi.adapter.convert.DefaultViewConvert;
import com.lvtanxi.adapter.convert.IViewConvert;



public abstract class SimplicityViewHolder<D> extends RecyclerView.ViewHolder {

    private SparseArray<View> viewMap;

    private IViewConvert injector;

    public SimplicityViewHolder(ViewGroup parent, int itemLayoutRes) {
        this(LayoutInflater.from(parent.getContext()).inflate(itemLayoutRes, parent, false));
    }

    public SimplicityViewHolder(View itemView) {
        super(itemView);
        viewMap = new SparseArray<>();
    }

    final void convert(D d, int position) {
        if (injector == null)
            injector = new DefaultViewConvert(this);
        convert(injector,d,position);
    }

    protected abstract void convert(IViewConvert convert, D d, int position);

    public final <T extends View> T id(int id) {
        View view = viewMap.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            viewMap.put(id, view);
        }
        return (T) view;
    }

}
