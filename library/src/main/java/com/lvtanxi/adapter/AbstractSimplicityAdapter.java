package com.lvtanxi.adapter;

import android.support.v7.widget.RecyclerView;

import com.lvtanxi.adapter.holder.SimplicityViewHolder;


abstract class AbstractSimplicityAdapter extends RecyclerView.Adapter<SimplicityViewHolder> {

    @Override
    public final void onBindViewHolder(SimplicityViewHolder holder, int position) {
        holder.convert(getItem(position),position);
    }

    protected abstract Object getItem(int position);

}
