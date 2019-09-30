package com.lvtanxi.adapter;


import androidx.recyclerview.widget.RecyclerView;
import com.lvtanxi.adapter.holder.SimpViewHolder;

@SuppressWarnings({"unchecked"})
abstract class AbsSimpAdapter extends RecyclerView.Adapter<SimpViewHolder> {

    @Override
    public final void onBindViewHolder(SimpViewHolder holder, int position) {
        holder.convert(getItem(position),position);
    }

    protected abstract Object getItem(int position);

}
