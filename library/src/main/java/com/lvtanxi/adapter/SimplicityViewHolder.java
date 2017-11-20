package com.lvtanxi.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lvtanxi.adapter.convert.DefaultViewConvert;
import com.lvtanxi.adapter.convert.IViewConvert;
import com.lvtanxi.adapter.listener.OnItemClickListener;
import com.lvtanxi.adapter.listener.OnNoDoubleClickListener;


public abstract class SimplicityViewHolder<D> extends RecyclerView.ViewHolder {

    private SparseArray<View> viewMap;

    private IViewConvert injector;

    protected int mPosition;

    private OnItemClickListener mOnItemClickListener ;




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
        mPosition=position;
        convert(injector,d,position);
    }

    public void addOnItemClickListener(OnItemClickListener itemClickListener,boolean addItemViewClick) {
        mOnItemClickListener=itemClickListener;
        if (itemClickListener != null&&addItemViewClick) {
            itemView.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (v.getId() == SimplicityViewHolder.this.itemView.getId()) {
                        mOnItemClickListener.onItemChildClick(v,mPosition,true);
                    }
                }
            });
        }
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

    public int getClickPosition(){
        return mPosition;
    }
    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }
}
